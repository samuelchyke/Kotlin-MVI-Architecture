package com.example.myapplication.ui.main

import com.example.myapplication.util.DataState

interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)
}