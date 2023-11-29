package com.example.myapplication.data.model

import retrofit2.http.Body

data class RegisterModel(
    val name: String,
    val email: String,
    val password: String,
    val no_telp: String,
    val confirm_password: String
)
