package ru.netology.nmedia.repository

import androidx.lifecycle.map
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.logics.Logics.toPost
import ru.netology.nmedia.logics.Logics.toPostEntity

class PostRepositoryRoomImplImpl(
    private val dao: PostDao,
) : PostRepository {

    override fun getAll() = dao.getAll()
        .map { list: List<PostEntity> -> list.map { PostEntity -> PostEntity.toPost() } }

    override fun savePost(post: Post) {
        if (post.id == 0L) dao.insert(post.toPostEntity()) else dao.updateContentById(post.id, post.content)
    }

    override fun likePostById(id: Long) {
        dao.likeById(id)
    }

    override fun sharePostById(id: Long) {
     dao.sharePostById(id)
    }

    override fun removePostById(id: Long) {
        dao.removeById(id)
    }




}
