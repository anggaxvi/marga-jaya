package com.example.myapplication.data.api

import com.example.myapplication.data.model.LoginModel
import com.example.myapplication.data.model.RegisterModel
import com.example.myapplication.data.response.LoginResponse
import com.example.myapplication.data.response.ProfileResponse
import com.example.myapplication.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ServiceApi {

    @POST("auth/register")
    suspend fun register(
        @Body postModel: RegisterModel
    ): RegisterResponse


    @Headers("User-Agent: margajaya-app")
    @POST("auth/users/login")
    suspend fun login(
        @Body postModel: LoginModel
    ): LoginResponse


    @GET("auth/users/me")
    suspend fun getProfile() : ProfileResponse
}