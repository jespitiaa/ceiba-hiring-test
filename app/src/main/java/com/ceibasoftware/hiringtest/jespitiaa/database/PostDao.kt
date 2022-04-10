package com.ceibasoftware.hiringtest.jespitiaa.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ceibasoftware.hiringtest.jespitiaa.model.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM posts_table WHERE userId = :userId")
    fun getPosts(userId:Int):List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<Post>)

    @Query("DELETE FROM posts_table")
    fun clear():Int

    @Query("DELETE FROM posts_table WHERE userId = :userId")
    fun deleteAll(userId: Int):Int

}