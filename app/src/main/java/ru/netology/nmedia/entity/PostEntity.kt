package ru.netology.nmedia.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts_table")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "published")
    val published: String,

    @ColumnInfo(name = "likeByMe")
    val likeByMe: Boolean,

    @ColumnInfo(name = "likesCount")
    var likesCount: Int,

    @ColumnInfo(name = "shareCount")
    var shareCount: Int,

    @ColumnInfo(name = "videoContent")
    val videoContent: String? = null
)