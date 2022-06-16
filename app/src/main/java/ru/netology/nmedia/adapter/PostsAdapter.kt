package ru.netology.nmedia.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.logics.Logics

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit

internal class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
) : ListAdapter<Post, PostViewHolder>(DiffCallBack) {

//    var list = emptyList<Post>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, onLikeListener, onShareListener)
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
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) = with(binding) {
        author.text = post.author
        published.text = post.published
        content.text = post.content
        likes.setImageResource(Logics.likesIcon(post.likeByMe))
        likes.setOnClickListener { onLikeListener(post) }
        share.setOnClickListener { onShareListener(post) }
        likesCount.text = Logics.numbersConvector(post.likesCount)
        shareCount.text = Logics.numbersConvector(post.shareCount)
    }
}




