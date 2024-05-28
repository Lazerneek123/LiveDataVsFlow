package com.example.myapplication1.viewModel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.database.ItemDatabase
import com.example.myapplication1.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityVM(application: Application, private val context: Context) : ViewModel() {
    private var _usersLiveData = MutableLiveData<List<User>>()
    val usersLiveData: LiveData<List<User>> = _usersLiveData

    var _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    //var userT: User = User("Roman", "Like a football", "+38094959056", true, 12)

    private var _leterOrWord = MutableLiveData<Boolean>()
    val leterOrWord: LiveData<Boolean> = _leterOrWord

    private val database = ItemDatabase.getInstance(application).itemDatabaseDao


    fun setLeterOrWord(boolean: Boolean) {
        _leterOrWord.value = boolean
    }

    fun loadListLiveData() {
        viewModelScope.launch(Dispatchers.Main) {
            if (database.listUsersEmpty() == null) {
                database.insert(User("Roman", "Like a football", "+38094959056", true, 12))
            }
            _usersLiveData.value = database.getAllUsers()
        }
    }

    fun addUserLiveData(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database.insert(user)
                updateListLiveData()
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database.delete(user)
                updateListLiveData()
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun searchUsersByNameLetter(searchText: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _usersLiveData.value = database.searchUsersByNameLetter(searchText)
        }
    }

    fun searchUsersByNameWord(searchText: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val newResult = database.searchUsersByNameWord(searchText)
            if (newResult.isNotEmpty()) {
                _usersLiveData.value = newResult
            } else {
                _usersLiveData.value = database.getAllUsers() // Відновлення повного списку
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.Main) {
            database.update(user)
        }
    }

    private fun updateListLiveData() {
        viewModelScope.launch(Dispatchers.Main) {
            _usersLiveData.value = database.getAllUsers()
        }
    }
}