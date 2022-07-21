package ru.netology.nmedia.data

import androidx.annotation.Nullable

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likeByMe: Boolean,
    var likesCount: Int,
    var shareCount: Int,
    val videoContent: String? = null

)