package com.example.myapplication1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.adapter.ItemAdapter
import com.example.myapplication1.databinding.ActivityMainBinding
import com.example.myapplication1.sheet.BottomSheetUser
import com.example.myapplication1.viewModel.MainActivityVM
import com.example.myapplication1.viewModel.MainActivityVMFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainActivityVM
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainActivityVMFactory(application, this)
        viewModel = ViewModelProvider(this, factory)[MainActivityVM::class.java]

        viewModel.setLeterOrWord(true) // встановлення пошуку по буквам

        recyclerView = binding.userListRecyclerView
        itemAdapter = ItemAdapter(supportFragmentManager)

        realizationLiveData()
        initRcView()
    }

    private fun realizationLiveData() {
        onChangeListenerLiveData()
        viewModel.usersLiveData.observe(this) {
            itemAdapter.submitList(it)
            binding.countItem.text = "LiveData List: " + it.size.toString()
        }
        addLiveData()
        searchLiveData()
    }

    private fun onChangeListenerLiveData() {
        viewModel.loadListLiveData()
    }

    private fun addLiveData() {
        binding.addButton.setOnClickListener {
            val bottomSheetInformation = BottomSheetUser()
            bottomSheetInformation.show(supportFragmentManager, "TAG")
        }
    }

    private fun searchLiveData() {
        val searchEdit = binding.userNameTextInputEdit
        searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = cs.toString().trim()
                if (viewModel.leterOrWord.value == true) {
                    viewModel.searchUsersByNameLetter(searchText)
                } else {
                    viewModel.searchUsersByNameWord(searchText)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        val btnLetterOrWord = binding.letterOrWordBtn
        btnLetterOrWord.setOnClickListener {
            if (viewModel.leterOrWord.value == true) {
                viewModel.setLeterOrWord(false)
                btnLetterOrWord.text = "Word"
            } else {
                viewModel.setLeterOrWord(true)
                btnLetterOrWord.text = "Letter"
            }
        }
    }

    private fun initRcView() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.adapter = itemAdapter
    }
}