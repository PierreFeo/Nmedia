package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.data.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likePostById(id: Long)
    fun sharePostById(id: Long)
    fun removePostById(id: Long)
    fun savePost(post: Post)
}