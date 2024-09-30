package com.example.myapplication1.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.App
import com.example.myapplication1.database.ItemDatabase
import com.example.myapplication1.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityVM(application: Application) : AndroidViewModel(application) {
    private var _usersLiveData = MutableLiveData<List<User>>()
    val usersLiveData: LiveData<List<User>> = _usersLiveData

    //var _user = MutableLiveData<User>()
    //val user: LiveData<User> = _user
    var user: User = User("Roman", "Like a football", "+38094959056", true, 12)

    private var _leterOrWord = MutableLiveData<Boolean>()
    val leterOrWord: LiveData<Boolean> = _leterOrWord

    private val database = (application as App).dataBase.itemDatabaseDao


    fun setLeterOrWord(boolean: Boolean) {
        _leterOrWord.value = boolean
    }

    fun loadListLiveData() {
        if (database.listUsersEmpty() == null) {
            viewModelScope.launch(Dispatchers.Main) {
                database.insert(User("Roman", "Like a football", "+38094959056", true, 12))
            }
        }
        updateListLiveData()
    }

    fun addUserLiveData(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database.insert(user)
                updateListLiveData()
            } catch (e: Exception) {
                //Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                Log.d("errorDatabase", e.message!!)
            }
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database.delete(user)
                updateListLiveData()
            } catch (e: Exception) {
                Log.d("errorDatabase", e.message!!)
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
            try {
                database.update(user)
                //updateListLiveData()
                _usersLiveData.value = database.getAllUsers()
                /*val u = User("Roman", "Like a football", "+38094959056", true, 12)
                u.id = 1
                val uu = ArrayList<User>()
                uu.add(u)
                _usersLiveData.value = uu*/
            } catch (e: Exception) {
                Log.d("errorDatabase", e.message!!)
            }
        }
    }

    private fun updateListLiveData() {
        viewModelScope.launch(Dispatchers.Main) {
            _usersLiveData.value = database.getAllUsers()
        }
    }
}