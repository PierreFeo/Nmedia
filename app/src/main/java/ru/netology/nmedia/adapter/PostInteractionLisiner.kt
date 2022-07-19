package ru.netology.nmedia.adapter

import ru.netology.nmedia.data.Post

interface PostInteractionLisiner {
    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onSaveClicked(content:String)
    fun onEditClicked(post: Post)
    fun onCloseClicked()
    fun onVideoClicked(post: Post)
    fun onPostClicked(post: Post)

}