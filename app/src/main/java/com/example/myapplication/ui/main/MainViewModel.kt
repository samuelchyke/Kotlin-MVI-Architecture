package com.example.myapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.BlogPost
import com.example.myapplication.model.User
import com.example.myapplication.repository.Repository
import com.example.myapplication.ui.main.state.MainStateEvent
import com.example.myapplication.ui.main.state.MainViewState
import com.example.myapplication.util.AbsentLiveData
import com.example.myapplication.util.DataState

class MainViewModel : ViewModel() {
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent){ stateEvent ->

            stateEvent?.let {
                handleStateEvent(it)
            }

        }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {

        return when (stateEvent){

            is MainStateEvent.GetBlogPostsEvent ->{
                return Repository.getBlogPosts()

            }
            is MainStateEvent.GetUserEvent ->{
                return Repository.getUser(stateEvent.userId)
            }
            is MainStateEvent.None ->{
                AbsentLiveData.create()
            }

        }
    }

    fun setBlogListData(blogPost: List<BlogPost>){
        val update =getCurrentViewStateOrNew()
        update.blogPosts = blogPost
        _viewState.value =  update

    }

    fun setUser(user: User){
        val update =getCurrentViewStateOrNew()
        update.user = user
        _viewState.value =  update
    }

    fun getCurrentViewStateOrNew():MainViewState{
        val value = viewState.value?.let {
            it
        }?: MainViewState()
        return value
    }

    fun setStateEvent(event: MainStateEvent){
        _stateEvent.value = event
    }
}