package com.example.myapplication1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.databinding.ActivityMainBinding
import com.example.myapplication1.models.Image
import com.example.myapplication1.models.User
import com.example.myapplication1.viewModel.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivity
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainActivity::class.java]
        recyclerView = binding.userListRecyclerView
        itemAdapter = ItemAdapter()

        //realizationLiveData()
        realizationFlow()
        initRcView()
    }

    private fun realizationLiveData() {
        viewModel.usersLiveData.observe(this) {
            itemAdapter.setList(it)
            binding.countItem.text = "List: " + it.size.toString()
        }
        onChangeListenerLiveData()
        addLiveData()
    }

    private fun realizationFlow() {
        onChangeListenerFlow()
        addFlow()
    }

    @SuppressLint("SetTextI18n")
    private fun onChangeListenerLiveData() {
        viewModel.loadListLiveData()
    }

    private fun addLiveData() {
        binding.addBtn.setOnClickListener {
            viewModel.addItem(Image("Roman", "11000"))
            binding.countItem.text = "List: " + viewModel.usersLiveData.value!!.size.toString()
        }
    }

    private fun addFlow() {
        binding.addBtn.setOnClickListener {
            viewModel.addItemFlow(Image("Roman", "11000"))
        }
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.usersFlow.collect {
                binding.countItem.text = "List: " + it.size
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onChangeListenerFlow() {
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.loadListFlow()
            viewModel.usersFlow
                .collect { filteredList ->
                    itemAdapter.setList(filteredList)
                    binding.countItem.text = "List: ${filteredList.size}"
                }
        }
    }

    private fun initRcView() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.adapter = itemAdapter
    }
}