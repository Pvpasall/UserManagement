package com.example.kotlin_database.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.kotlin_database.User
import com.example.kotlin_database.dao.UserDao
import com.example.kotlin_database.database.UserDatabase

class UserRepository(private val userDao: UserDao) {
    val allUsers: LiveData<List<User>> = userDao.getAllUsers()
    suspend fun insert(user: User) {
        userDao.insert(user)
    }
    suspend fun delete(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun insertAll(users: List<User>) {
        userDao.insertAll(users)
    }

    companion object {
        // Factory method to create the repository
        fun getRepository(context: Context): UserRepository {
            val database = UserDatabase.getDatabase(context)
            return UserRepository(database.userDao())
        }
    }
}