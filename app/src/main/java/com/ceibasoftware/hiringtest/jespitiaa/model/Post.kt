package com.ceibasoftware.hiringtest.jespitiaa.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts_table")
data class Post(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
)
