package com.example.myapplication1.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.database.ItemDatabase
import com.example.myapplication1.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityVM(application: Application) : ViewModel() {
    private var _usersLiveData = MutableLiveData<List<User>>()
    val usersLiveData: LiveData<List<User>> = _usersLiveData

    private val database = ItemDatabase.getInstance(application).itemDatabaseDao

    fun loadListLiveData() {
        viewModelScope.launch(Dispatchers.Main) {
            if (database.listUsersEmpty() == null) {
                database.insert(User("Roman", "Like a football", "+38094959056", true, 12))
            }
            _usersLiveData.value = database.getAllUsers()
        }
    }

    fun addUserLiveData(user: User) {
        viewModelScope.launch {
            try {
                database.insert(user)
            } catch (e: Exception) {

            }
        }

        _usersLiveData.value = database.getAllUsers()
    }

    private fun deleteUser(item: User) {
        viewModelScope.launch(Dispatchers.Main) {
            database.delete(item)
        }
    }
}