package com.example.kotlin_database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val email: String
)