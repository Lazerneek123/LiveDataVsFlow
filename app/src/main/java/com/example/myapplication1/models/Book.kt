package com.example.myapplication1.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Book")
data class Book(
    @ColumnInfo(name = "name")
    var name: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bookId")
    var bookId: String = ""
}