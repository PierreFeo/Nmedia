package ru.netology.nmedia.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostInteractionLisiner
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryFileImpl
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl
import ru.netology.nmedia.repository.PostRepositorySQLiteImpl
import ru.netology.nmedia.util.SingleLiveEvent

private val empty = Post(
    id = 0L,
    content = "",
    author = "me",
    likeByMe = false,
    published = "now",
    likesCount = 0,
    shareCount = 0
)

class PostViewModel(application: Application) : AndroidViewModel(application),
    PostInteractionLisiner {

    private val repository: PostRepository =
        PostRepositorySQLiteImpl(dbManager = AppDb.getInstance(context = application).dbManager)
    val sharePostEvent = SingleLiveEvent<Post>()
    val currentPost = MutableLiveData<Post?>(null)
    val data = repository.getAll()
    val videoClickedEvent = SingleLiveEvent<Post>()
    val editPostEvent = SingleLiveEvent<Post>()
    val testPostEvent = SingleLiveEvent<Post>()

    override fun onLikeClicked(post: Post) = repository.likePostById(post.id)

    override fun onShareClicked(post: Post) {
        sharePostEvent.value = post
        repository.sharePostById(post.id)
    }

    override fun onRemoveClicked(post: Post) = repository.removePostById(post.id)

    override fun onSaveClicked(content: String) {
        if (content.isBlank()) return
        val newPost = currentPost.value?.copy(content = content) ?: empty.copy(content = content)
        repository.savePost(newPost)
        currentPost.value = null

    }

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        editPostEvent.value = post
    }

    override fun onCloseClicked() {
        currentPost.value = null
    }

    override fun onVideoClicked(post: Post) {
        videoClickedEvent.value = post
    }

    override fun onPostClicked(post: Post) {
        testPostEvent.value = post
    }

}