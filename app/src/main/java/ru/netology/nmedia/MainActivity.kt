package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.activity.NewPostActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
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

        val activityLauncher =
            registerForActivityResult(NewPostActivity.PostResultContract) { result: String? ->
                result ?: return@registerForActivityResult
                viewModel.onSaveClicked(result)
            }

        viewModel.postContentToScreenEvent.observe(this) {
            activityLauncher.launch(it.content)
        }

        viewModel.shareEvent.observe(this) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, it.content)
            }
            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }


        viewModel.videoClickedEvent.observe(this) {
            it.videoContent ?: return@observe
            val newIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(it.videoContent)
            )
            startActivity(newIntent)
        }


        binding.fab.setOnClickListener {
            activityLauncher.launch("")
        }
    }


}

