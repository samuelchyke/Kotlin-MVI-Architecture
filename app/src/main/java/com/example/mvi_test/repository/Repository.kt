package com.example.mvi_test.repository

import androidx.lifecycle.LiveData
import com.example.mvi_test.util.ApiSuccessResponse
import com.example.mvi_test.util.DataState
import com.example.mvi_test.util.GenericApiResponse
import com.example.myapplication.api.MyRetrofitBuilder
import com.example.myapplication.model.BlogPost
import com.example.myapplication.model.User
import com.example.myapplication.repository.NetworkBoundResource
import com.example.myapplication.ui.main.state.MainViewState

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<List<BlogPost>, MainViewState>(){

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                    data = MainViewState(
                        blogPosts = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return MyRetrofitBuilder.apiService.getBlogPosts()
            }

        }.asLiveData()
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<User, MainViewState>(){

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                    data = MainViewState(
                        user = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return MyRetrofitBuilder.apiService.getUser(userId)
            }

        }.asLiveData()
    }
}
