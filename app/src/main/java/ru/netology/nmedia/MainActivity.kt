package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val post = Post(
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
            shareCount = 32
        )
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.render(post)

        binding.likes.setOnClickListener {
            post.likeByMe = !post.likeByMe
            if (post.likeByMe) {
                post.likesCount++
                binding.likes.setImageResource(likesIcon(post.likeByMe))
            } else
                post.likesCount--
            binding.likes.setImageResource(likesIcon(post.likeByMe))
            binding.likesCount.text = numbersConvector(post.likesCount)
        }

        binding.share.setOnClickListener {
            post.shareCount++
            binding.shareCount.text = numbersConvector(post.shareCount)
        }
    }

    private fun ActivityMainBinding.render(post: Post) {
        author.text = post.author
        published.text = post.published
        content.text = post.content
        likes.setImageResource(likesIcon(post.likeByMe))
        likesCount.text = numbersConvector(post.likesCount)
        shareCount.text = numbersConvector(post.shareCount)
    }

    private fun likesIcon(liked: Boolean) =
        if (liked) R.drawable.ic_baseline_favorite_24
        else R.drawable.ic_baseline_favorite_border_24

    private fun numbersConvector(value: Int): String {
        return when (value) {
            in 0..999 -> value.toString()
            in 1_000..1_099 -> "1K"
            in 1_100..9_999 -> "${(value / 1000)}.${(value % 1000 / 100)}K"
            in 10_000..999_999 -> "${(value / 1000)}K"
            in 1_000_000..1_099_999 -> "1M"
            in 1_100_000..999_999_999 -> "${(value / 1000000)}.${value % 1000000 / 100000}M"
            else -> {
                getString(R.string.over_a_billion)
            }
        }
    }
}
