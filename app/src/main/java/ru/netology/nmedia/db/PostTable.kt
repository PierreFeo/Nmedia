package ru.netology.nmedia.db

object PostsTable {
    const val TABLE_NAME = "posts"



    //CREATE TABLE
    val DDL = """
        CREATE TABLE $TABLE_NAME (
            ${Column.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Column.AUTHOR.columnName} TEXT NOT NULL,
            ${Column.CONTENT.columnName} TEXT NOT NULL,
            ${Column.PUBLISHED.columnName} TEXT NOT NULL,
            ${Column.LIKE_BY_ME.columnName} BOOLEAN NOT NULL DEFAULT 0,
            ${Column.LIKES_COUNT.columnName} INTEGER NOT NULL DEFAULT 0,
            ${Column.SHARE_COUNT.columnName} INTEGER NOT NULL DEFAULT 0,
            ${Column.VIDEO_CONTENT.columnName} TEXT
        );
        """.trimIndent()

    //list of all columns
    val ALL_COLUMNS_NAMES = Column.values().map { it.columnName }.toTypedArray()


    enum class Column(val columnName: String) {
        ID("id"),
        AUTHOR("author"),
        CONTENT("content"),
        PUBLISHED("published"),
        LIKE_BY_ME("likeByMe"),
        LIKES_COUNT("likesCount"),
        SHARE_COUNT("shareCount"),
        VIDEO_CONTENT("videoContent")
    }

}