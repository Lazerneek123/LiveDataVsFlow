package com.example.myapplication1.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication1.InterfaceAdapter

@Entity(tableName = "User")
data class User(
    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "status")
    var status: String? = null,

    @ColumnInfo(name = "email")
    var email: String? = null,

    @ColumnInfo(name = "online_status")
    var onlineStatus: Boolean = false,

    @ColumnInfo(name = "age")
    var age: Int = 0

) : InterfaceAdapter {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    override fun getType(): Int {
        return InterfaceAdapter.USER_TYPE
    }
}