package com.ceibasoftware.hiringtest.jespitiaa.model

import androidx.room.PrimaryKey

data class Post(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
)
