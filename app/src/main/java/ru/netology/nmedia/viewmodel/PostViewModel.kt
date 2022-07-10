package ru.netology.nmedia.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostInteractionLisiner
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

private val empty = Post(
    id = 0L,
    content = "",
    author = "me",
    likeByMe = false,
    published = "now",
    likesCount = 0,
    shareCount = 0
)

class PostViewModel : ViewModel(), PostInteractionLisiner {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val currentPost = MutableLiveData<Post?>(null)
    val data = repository.getAll()


    override fun onLikeClicked(post: Post) = repository.likePostById(post.id)
    override fun onShareClicked(post: Post) = repository.sharePostById(post.id)
    override fun onRemoveClicked(post: Post) = repository.removePostById(post.id)
    override fun onSaveClicked(content: String) {
        if (content.isBlank()) return
        val newPost = currentPost.value?.copy(content = content) ?: empty.copy(content = content)
        repository.savePost(newPost)
        currentPost.value = null

    }

    override fun onEditClicked(post: Post) {
        currentPost.value = post
    }

    override fun onCloseClicked() {
        currentPost.value = null
    }
}