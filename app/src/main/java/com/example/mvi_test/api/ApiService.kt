package com.example.mvi_test.api

import com.example.mvi_test.model.BlogPost
import com.example.mvi_test.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/blogs")
    fun getBlogPosts(): List<BlogPost>

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): User
}