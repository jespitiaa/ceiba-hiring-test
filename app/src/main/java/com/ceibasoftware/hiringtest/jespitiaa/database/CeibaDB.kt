package com.ceibasoftware.hiringtest.jespitiaa.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ceibasoftware.hiringtest.jespitiaa.model.Post
import com.ceibasoftware.hiringtest.jespitiaa.model.User

@Database(entities = [Post::class, User::class], version = 1, exportSchema = false)
abstract class CeibaDB : RoomDatabase() {
    abstract fun usersDao(): UserDao
    abstract fun postsDao(): PostDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CeibaDB? = null

        fun getDatabase(context: Context): CeibaDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CeibaDB::class.java,
                    "ceiba_user_posts_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}