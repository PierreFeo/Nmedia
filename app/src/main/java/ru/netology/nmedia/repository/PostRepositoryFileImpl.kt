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

    //posts inside region
    //region
//    private var posts = listOf(
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
//            published = "21 мая в 18:36",
//            likeByMe = false,
//            likesCount = 32,
//            shareCount = 12
//        ),
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях \uD83D\uDC47",
//            published = "18 сентября в 10:12",
//            likeByMe = false,
//            likesCount = 44,
//            shareCount = 23
//        ),
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Языков программирования много, и выбрать какой-то один бывает нелегко. Собрали подборку статей, которая поможет вам начать, если вы остановили свой выбор на JavaScript.",
//            published = "19 сентября в 10:24",
//            likeByMe = false,
//            likesCount = 9_999,
//            shareCount = 12
//        ),
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Большая афиша мероприятий осени: конференции, выставки и хакатоны для жителей Москвы, Ульяновска и Новосибирска \uD83D\uDE09",
//            published = "19 сентября в 14:12",
//            likeByMe = false,
//            likesCount = 999,
//            shareCount = 5
//        ),
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Диджитал давно стал частью нашей жизни: мы общаемся в социальных сетях и мессенджерах, заказываем еду, такси и оплачиваем счета через приложения.",
//            published = "20 сентября в 10:14",
//            likeByMe = false,
//            likesCount = 6,
//            shareCount = 8
//        ),
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "\uD83D\uDE80 24 сентября стартует новый поток бесплатного курса «Диджитал-старт: первый шаг к востребованной профессии» — за две недели вы попробуете себя в разных профессиях и определите, что подходит именно вам → http://netolo.gy/fQ",
//            published = "21 сентября в 10:12",
//            likeByMe = false,
//            likesCount = 0,
//            shareCount = 0
//        ),
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Хорошая песня, послушайте!",
//            published = "22 сентября в 10:12",
//            likeByMe = false,
//            likesCount = 44,
//            shareCount = 2,
//            videoContent = "https://www.youtube.com/watch?v=j1zBEWyBJb0&list=RDj1zBEWyBJb0&start_radio=1"
//        ),
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали \uD83D\uDE34\n",
//            published = "22 сентября в 10:14",
//            likeByMe = false,
//            likesCount = 1100,
//            shareCount = 5
//        ),
//        Post(
//            id = nextId++,
//            author = "Нетология. Университет интернет-профессий будущего",
//            content = "Видео, что надо",
//            published = "23 сентября в 10:12",
//            likeByMe = false,
//            likesCount = 32,
//            shareCount = 46,
//            videoContent = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
//        ),
//    ).reversed()

    //endregion //

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
