import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.FeedFragment.Companion.longArg
import ru.netology.nmedia.FeedFragment.Companion.textArg
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class FragmentPost : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val viewHolder = PostViewHolder(binding.onlyPost, viewModel)

        val incomeIdPost = arguments?.longArg

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val currentPost = posts.firstOrNull { it.id == incomeIdPost }
            currentPost?.let { viewHolder.bind(it) } ?: findNavController().navigateUp().apply {
                Snackbar.make(
                    binding.root,
                    R.string.message_deleted,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.sharePostEvent.observe(viewLifecycleOwner) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, it.content)
            }
            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.editPostEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_fragmentPost_to_newPostFragment,
                Bundle().apply { textArg = it.content })
        }

        return binding.root
    }

}