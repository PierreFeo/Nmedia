package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.db.PostDao

class PostRepositorySQLiteImpl(private val dao: PostDao) : PostRepository {
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
       posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likePostById(id: Long) {
        dao.likeById(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likeByMe = !it.likeByMe,
                likesCount = if (it.likeByMe) it.likesCount - 1 else it.likesCount + 1
            )
        }
        data.value = posts
    }


    override fun sharePostById(id: Long) {
       dao.sharePostById(id)
        posts = posts.map {
            if (it.id == id) it.copy(shareCount = it.shareCount + 1) else it
        }
        data.value = posts
    }

    override fun removePostById(id: Long) {
        dao.removeById(id)
        posts = posts.filter { it.id != id }
        data.value = posts
    }


    override fun savePost(post: Post) {
        if (post.id == 0L) createPost(post) else updatePost(post)
    }

    private fun createPost(post: Post) {
        val saved = dao.save(post)
        posts = listOf(
            saved
        ) + posts
        data.value = posts
    }

    private fun updatePost(post: Post) {
        posts = posts.map {
            if (it.id != post.id) it else dao.save(post)
        }
        data.value = posts

    }
}
