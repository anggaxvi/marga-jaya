package com.example.myapplication.data.api

import com.example.myapplication.data.model.LoginModel
import com.example.myapplication.data.model.RegisterModel
import com.example.myapplication.data.response.GetLapByIdResponse
import com.example.myapplication.data.response.GetLapResponse
import com.example.myapplication.data.response.LoginResponse
import com.example.myapplication.data.response.ProfileResponse
import com.example.myapplication.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("lapangan")
    suspend fun getLapangan(
        @Query("tanggal") tanggal : String
    ):GetLapResponse

    @GET("lapangan/{id}")
    suspend fun getLapById(
        @Path("id") id : String,
        @Query("tanggal") tanggal: String
    ):GetLapByIdResponse
}