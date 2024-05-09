package com.example.myapplication1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.InterfaceAdapter
import com.example.myapplication1.models.Image
import com.example.myapplication1.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch

class MainActivity : ViewModel() {
    private val _usersLiveData = MutableLiveData<ArrayList<InterfaceAdapter>>()
    val usersLiveData: LiveData<ArrayList<InterfaceAdapter>> = _usersLiveData

    fun loadListLiveData() {
        val list = ArrayList<InterfaceAdapter>()

        var user = User("Roman", "Like a football", "roman@mail.com", true, 12)
        user.id = 1
        list.add(user)

        var image = Image("Roman", "10:10")
        image.id = 1
        list.add(image)

        user = User("Pavel", "Like a bicycler", "pavel@mail.com", false, 13)
        user.id = 2
        list.add(user)

        image = Image("Pavel", "11:05")
        image.id = 2
        list.add(image)

        _usersLiveData.value = list

    }

    fun addItem(item: InterfaceAdapter) {
        _usersLiveData.value!!.add(item)
    }

    fun addItemFlow(item: InterfaceAdapter) {
        viewModelScope.launch(Dispatchers.Main) {
            _usersFlow.emit(_usersFlow.replayCache.firstOrNull()?.apply { add(item) } ?: arrayListOf(item))
        }
    }

    private var _usersFlow = MutableSharedFlow<ArrayList<InterfaceAdapter>>(replay = 1)
    val usersFlow: Flow<ArrayList<InterfaceAdapter>> = _usersFlow.asSharedFlow()

    fun loadListFlow() {
        viewModelScope.launch(Dispatchers.Main) {
            val result: Flow<ArrayList<InterfaceAdapter>> = flow {
                val list = arrayListOf<InterfaceAdapter>()

                var user = User("Roman", "Like a football", "roman@mail.com", true, 12)
                user.id = 1
                list.add(user)

                var image = Image("Roman", "10:10")
                image.id = 1
                list.add(image)

                user = User("Pavel", "Like a bicycler", "pavel@mail.com", false, 13)
                user.id = 2
                list.add(user)

                image = Image("Pavel", "11:05")
                image.id = 2
                list.add(image)

                emit(list)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = arrayListOf() // Initial value should match the Flow's type
            )
            // Оновлення значення _usersFlow за допомогою методу emit
            _usersFlow.emit(result.first()) // Використовуємо first() для отримання першого елемента потоку
        }
    }
}