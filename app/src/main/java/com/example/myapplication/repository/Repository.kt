package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.myapplication.api.MyRetrofitBuilder
import com.example.myapplication.model.BlogPost
import com.example.myapplication.model.User
import com.example.myapplication.ui.main.state.MainViewState
import com.example.myapplication.util.*

object Repository {
    fun getBlogPosts(): LiveData<DataState<MainViewState>>{
        return Transformations
            .switchMap(MyRetrofitBuilder.apiService.getBlogPosts()){ apiResponse ->
                object: LiveData<DataState<MainViewState>>(){
                    override fun onActive() {
                        super.onActive()
                        when(apiResponse){
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = MainViewState(
                                        blogPosts = apiResponse.body
                                    )
                                )
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    message = apiResponse.errorMessage
                                )
                            }

                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    message = "HTTP 204. Returned NOTHING."
                                )
                            }
                        }
                    }
                }
            }
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>>{
        return Transformations
            .switchMap(MyRetrofitBuilder.apiService.getUser(userId)){ apiResponse ->
                object: LiveData<DataState<MainViewState>>(){
                    override fun onActive() {
                        super.onActive()
                        when(apiResponse){
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = MainViewState(
                                        user = apiResponse.body
                                    )
                                )
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    message = apiResponse.errorMessage
                                )
                            }

                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    message = "HTTP 204. Returned NOTHING."
                                )
                            }
                        }
                    }
                }
            }
    }
}
