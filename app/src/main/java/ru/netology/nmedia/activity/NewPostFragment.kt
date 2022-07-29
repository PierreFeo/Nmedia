package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.FeedFragment.Companion.textArg
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.focusAndShowKeyboard
import ru.netology.nmedia.viewmodel.PostViewModel


class NewPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)


//setText in view edit
        val setInComeIntent = arguments?.textArg
        setInComeIntent?.let { binding.edit.setText(it) }

        with(binding) {
            edit.focusAndShowKeyboard()
            ok.setOnClickListener {
                if (!binding.edit.text.isNullOrBlank()) {
                    val text = binding.edit.text.toString()
                    viewModel.onSaveClicked(text)

                }
                findNavController().navigateUp()
            }
            return binding.root
        }

    }

}
