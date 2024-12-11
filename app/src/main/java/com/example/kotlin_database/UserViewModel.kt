package com.example.kotlin_database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_database.repository.UserRepository
import kotlinx.coroutines.launch

// The repository abstracts the details of where the data comes from. Here its The UserDao
class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val allUsers: LiveData<List<User>> = repository.allUsers

    fun insert(user: User) {
        viewModelScope.launch {
            repository.insert(user)
        }
    }

    fun insertAll(users: List<User>) {
        viewModelScope.launch {
            repository.insertAll(users)
        }
    }

    fun delete(user: User) {
        viewModelScope.launch {
            repository.delete(user)
        }
    }
}