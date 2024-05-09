package com.example.myapplication1

interface InterfaceAdapter {
    fun getType(): Int

    companion object {
        const val USER_TYPE: Int = 1
        const val IMAGE_TYPE: Int = 2
    }
}