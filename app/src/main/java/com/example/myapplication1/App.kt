package com.example.myapplication1

import android.app.Application
import com.example.myapplication1.database.ItemDatabase

class App : Application() {
    val dataBase by lazy { ItemDatabase.getInstance(this) }
}