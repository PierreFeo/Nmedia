package ru.netology.nmedia.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.logics.Logics
import ru.netology.nmedia.util.focusAndShowKeyboard
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard


internal class PostsAdapter(
    private val interactionLisiner: PostInteractionLisiner
) : ListAdapter<Post, PostViewHolder>(DiffCallBack) {

//    var list = emptyList<Post>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, interactionLisiner)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    //  override fun getItemCount()

    private object DiffCallBack : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }


        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val lisiner: PostInteractionLisiner,
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(post: Post) = with(binding) {
        videoViewGroup.visibility = View.VISIBLE
        author.text = post.author
        published.text = post.published
        content.text = post.content
        avatar.setImageResource(R.drawable.avatar) //TODO will delete
        likes.isChecked = post.likeByMe

        if (post.videoContent.isNullOrBlank()) {
            videoViewGroup.visibility = View.GONE
        }
        likes.text = Logics.numbersConvector(post.likesCount)
        share.text = Logics.numbersConvector(post.shareCount)
        urlVideoView.text = post.videoContent
        videoPreviewView.setImageResource(R.mipmap.youtobe_banner_foreground)
        likes.setOnClickListener { lisiner.onLikeClicked(post) }
        share.setOnClickListener { lisiner.onShareClicked(post) }
        playMediaButtonView.setOnClickListener { lisiner.onVideoClicked(post) }
        videoPreviewView.setOnClickListener { lisiner.onVideoClicked(post) }



        menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.remove_button -> {
                            lisiner.onRemoveClicked(post)
                            true
                        }
                        R.id.edit_button -> {
                            lisiner.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }

    }
}







