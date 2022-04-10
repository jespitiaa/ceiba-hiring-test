package com.ceibasoftware.hiringtest.jespitiaa.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ceibasoftware.hiringtest.jespitiaa.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users_table")
    fun getUsers():List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Query("DELETE FROM users_table")
    fun deleteAll():Int
}