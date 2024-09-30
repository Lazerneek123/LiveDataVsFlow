package com.example.myapplication1.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "Book2",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userOwnerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Book2(
    @ColumnInfo(name = "name")
    val name: String? = null
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: String = ""
}