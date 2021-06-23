package com.example.mvi_test.ui

import com.example.mvi_test.util.DataState

interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)
}