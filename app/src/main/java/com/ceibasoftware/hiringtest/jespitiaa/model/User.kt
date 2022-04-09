package com.ceibasoftware.hiringtest.jespitiaa.model

import androidx.room.PrimaryKey

data class User(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String
    //address
    //company
)