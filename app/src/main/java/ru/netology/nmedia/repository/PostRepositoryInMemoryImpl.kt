package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var posts = List(10) { index ->
        Post(
            id = index + 1L,
            author = "Нетология",
            content = "Привет, это новая Нетология! " +
                    "Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. " +
                    "Затем появились курсы по дизайну, разработке, аналитике и управлению. " +
                    "Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. " +
                    "Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, " +
                    "целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → " +
                    "http://netolo.gy/fyb",
            published = "12 мая 2022",
            likeByMe = false,
            likesCount = 9 + index,
            shareCount = 2 + index
        )
    }
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likeByMe = !it.likeByMe,
                likesCount = if (it.likeByMe) it.likesCount - 1
                else it.likesCount + 1
            )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id == id) it.copy(shareCount = it.shareCount + 1) else it
        }
        data.value = posts
    }
}

