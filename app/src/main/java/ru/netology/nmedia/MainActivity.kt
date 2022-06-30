package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.logics.Logics
import ru.netology.nmedia.util.focusAndShowKeyboard
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter(viewModel)
        binding.recyclerViewPosts.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.saveButton.setOnClickListener {
            with(binding.editContent) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.EmptyContent,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                viewModel.onSaveClicked(binding.editContent.text.toString())
                setText("")
                clearFocus()
                hideKeyboard()
                binding.editConstraintLayout.visibility = View.GONE
            }
        }

        viewModel.currentPost.observe(this) { post ->
            if (post == null) {
                return@observe
            }
            with(binding.editContent) {
                requestFocus()
                setText(post.content)
                setSelection(length())
                focusAndShowKeyboard()


                with(binding) {
                    editConstraintLayout.visibility = View.VISIBLE
                    textPost.text = post.content

                    closeButton.setOnClickListener {
                        viewModel.onCloseClicked()
                        editConstraintLayout.visibility = View.GONE
                        editContent.setText("")
                        hideKeyboard()
                        clearFocus()
                    }
                }

            }
        }
    }
}


