package com.example.myapplication1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.adapter.ItemAdapter
import com.example.myapplication1.databinding.ActivityMainBinding
import com.example.myapplication1.models.User
import com.example.myapplication1.viewModel.MainActivityVM
import com.example.myapplication1.viewModel.MainActivityVMFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityVM
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainActivityVMFactory(application)
        viewModel = ViewModelProvider(this, factory)[MainActivityVM::class.java]
        recyclerView = binding.userListRecyclerView
        itemAdapter = ItemAdapter()

        realizationLiveData()
        initRcView()
    }

    private fun realizationLiveData() {
        onChangeListenerLiveData()
        viewModel.usersLiveData.observe(this) {
            itemAdapter.setList(it)
            binding.countItem.text = "List: " + it.size.toString()
        }
        addLiveData()
    }

    private fun onChangeListenerLiveData() {
        viewModel.loadListLiveData()
    }

    private fun addLiveData() {
        binding.addBtn.setOnClickListener {
            viewModel.addUserLiveData(User("Roman", "11000"))
            binding.countItem.text = "List: " + viewModel.usersLiveData.value!!.size.toString()
        }
    }

    private fun initRcView() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.adapter = itemAdapter
    }
}