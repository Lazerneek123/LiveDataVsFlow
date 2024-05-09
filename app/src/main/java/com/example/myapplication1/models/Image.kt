package com.example.myapplication1.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.myapplication1.InterfaceAdapter

data class Image(
    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "time")
    val time: String? = null

) : InterfaceAdapter {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    override fun getType(): Int {
        return InterfaceAdapter.IMAGE_TYPE
    }
}