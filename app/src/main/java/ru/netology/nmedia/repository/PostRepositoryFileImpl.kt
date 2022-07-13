package ru.netology.nmedia.repository

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.data.Post
import kotlin.properties.Delegates

class PostRepositoryFileImpl(
    private val context: Context,
) : PostRepository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)


    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            // если файл есть - читаем
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        } else {
            // если нет, записываем пустой массив
            sync()
        }
    }



    override fun getAll(): LiveData<List<Post>> = data

    override fun likePostById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likeByMe = !it.likeByMe,
                likesCount = if (it.likeByMe) it.likesCount - 1
                else it.likesCount + 1
            )
        }
        data.value = posts
        sync()

    }

    override fun sharePostById(id: Long) {
        posts = posts.map {
            if (it.id == id) it.copy(shareCount = it.shareCount + 1) else it
        }
        data.value = posts
        sync()
    }

    override fun removePostById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    override fun savePost(post: Post) {
        if (post.id == 0L) createPost(post) else updatePost(post)
    }

    private fun createPost(post: Post) {
        posts = listOf(
            post.copy(id = newId++)
        ) + posts
        data.value = posts
        sync()
    }

    private fun updatePost(post: Post) {
        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    companion object {
        const val NEXT_ID_PREFS_LEY = "nextId"
    }

    private var newId: Long by Delegates.observable(
        prefs.getLong(NEXT_ID_PREFS_LEY, 1L)
    ) { _, _, newValue ->
        prefs.edit {
            putLong(NEXT_ID_PREFS_LEY, newValue)
        }
    }
}
