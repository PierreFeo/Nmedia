package ru.netology.nmedia.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.Transformations.map
import ru.netology.nmedia.data.Post

class MyDbManagerImpl(
    private val db: SQLiteDatabase
) : MyDbManager {


    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostsTable.TABLE_NAME,
            PostsTable.ALL_COLUMNS_NAMES,
            null, null, null, null, "${PostsTable.Column.ID} DESC"
        ).use { cursor ->
            while (cursor.moveToNext()) {
                posts.add(cursor.toPost())
            }
        }
        return posts
    }

    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            put(PostsTable.Column.AUTHOR.columnName, post.author)
            put(PostsTable.Column.CONTENT.columnName, post.content)
            put(PostsTable.Column.PUBLISHED.columnName, post.published)
            put(PostsTable.Column.LIKE_BY_ME.columnName, post.likeByMe)
            put(PostsTable.Column.LIKES_COUNT.columnName, post.likesCount)
            put(PostsTable.Column.SHARE_COUNT.columnName, post.shareCount)
            put(PostsTable.Column.VIDEO_CONTENT.columnName, post.videoContent)
        }
        val id = if (post.id != 0L) {
            db.update(
                PostsTable.TABLE_NAME,
                values,
                "${PostsTable.Column.ID.columnName}=?",
                arrayOf(post.id.toString())
            )
            post.id
        } else {
            db.insert(PostsTable.TABLE_NAME, null, values)
        }
        return db.query(
            PostsTable.TABLE_NAME,
            PostsTable.ALL_COLUMNS_NAMES,
            "${PostsTable.Column.ID.columnName}=?",
            arrayOf(post.id.toString()),
            null, null, null
        ).use { cursor ->
            cursor.moveToNext()
            cursor.toPost()
        }

    }


    override fun likeById(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               likesCount = likesCount + CASE WHEN likeByMe THEN -1 ELSE 1 END,
               likeByMe = CASE WHEN likeByMe THEN 0 ELSE 1 END
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostsTable.TABLE_NAME,
            "${PostsTable.Column.ID.columnName} = ?",
            arrayOf(id.toString())
        )
    }

    override fun sharePostById(id: Long) {
        db.execSQL(
            """

           UPDATE posts SET
              shareCount = shareCount + 1
              WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    private fun Cursor.toPost() = Post(
        id = getLong(getColumnIndexOrThrow(PostsTable.Column.ID.columnName)),
        author = getString(getColumnIndexOrThrow(PostsTable.Column.AUTHOR.columnName)),
        content = getString(getColumnIndexOrThrow(PostsTable.Column.CONTENT.columnName)),
        published = getString(getColumnIndexOrThrow(PostsTable.Column.PUBLISHED.columnName)),
        likeByMe = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKE_BY_ME.columnName)) != 0,
        likesCount = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKES_COUNT.columnName)),
        shareCount = getInt(getColumnIndexOrThrow(PostsTable.Column.SHARE_COUNT.columnName)),
        videoContent = getString(getColumnIndexOrThrow(PostsTable.Column.VIDEO_CONTENT.columnName))
    )

}