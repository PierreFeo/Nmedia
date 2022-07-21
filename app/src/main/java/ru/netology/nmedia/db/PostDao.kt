package ru.netology.nmedia.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM posts_table ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE posts_table SET content = :content WHERE id = :id")
    fun updateContentById(id: Long, content: String)

    @Query(
        """
        UPDATE posts_table SET
        likesCount = likesCount + CASE WHEN likeByMe THEN -1 ELSE 1 END,
        likeByMe = CASE WHEN likeByMe THEN 0 ELSE 1 END
        WHERE id = :id
        """)
    fun likeById(id: Long)


    @Query("DELETE FROM posts_table WHERE id = :id")
    fun removeById(id: Long)


    @Query(" UPDATE posts_table SET shareCount = shareCount + 1 WHERE id = :id ")
    fun sharePostById(id: Long)
}