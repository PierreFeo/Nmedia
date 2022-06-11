package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1L,
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
        likesCount = 999,
        shareCount = 12
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = if (post.likeByMe) post.copy(
            likeByMe = !post.likeByMe,
            likesCount = post.likesCount -1
        )
        else post.copy(likeByMe = !post.likeByMe, likesCount = post.likesCount +1)
        data.value = post
    }

    override fun share() {
        post = post.copy(shareCount = post.shareCount + 1)
        data.value = post
    }
}