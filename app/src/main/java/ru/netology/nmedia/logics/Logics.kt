package ru.netology.nmedia.logics

import android.content.res.Resources

import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.entity.PostEntity

object Logics {

    fun numbersConvector(value: Int): String {
        return when (value) {
            in 0..999 -> value.toString()
            in 1_000..1_099 -> "1K"
            in 1_100..9_999 -> "${(value / 1000)}.${(value % 1000 / 100)}K"
            in 10_000..999_999 -> "${(value / 1000)}K"
            in 1_000_000..1_099_999 -> "1M"
            in 1_100_000..999_999_999 -> "${(value / 1000000)}.${value % 1000000 / 100000}M"
            else -> {
                Resources.getSystem().getString(R.string.over_a_billion)
            }
        }
    }
//
//    fun likesIcon(liked: Boolean) =
//        if (liked) R.drawable.ic_baseline_favorite_24
//        else R.drawable.ic_baseline_favorite_border_24

    fun PostEntity.toPost() = Post(
        id = id,
        author = author,
        content = content,
        published = published,
        likeByMe = likeByMe,
        likesCount = likesCount,
        shareCount = shareCount,
        videoContent = videoContent
    )
     fun Post.toPostEntity() = PostEntity(
        id = id,
        author = author,
        content = content,
        published = published,
        likeByMe = likeByMe,
        likesCount = likesCount,
        shareCount = shareCount,
        videoContent = videoContent
    )

}
