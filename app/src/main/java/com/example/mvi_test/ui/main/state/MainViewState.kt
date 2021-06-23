package com.example.mvi_test.ui.main.state

import com.example.mvi_test.model.BlogPost
import com.example.mvi_test.model.User

data class MainViewState(

    var blogPosts: List<BlogPost>? = null,
    var user: User? = null

)