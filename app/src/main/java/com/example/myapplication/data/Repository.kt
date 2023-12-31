package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.myapplication.data.api.ServiceApi
import com.example.myapplication.data.model.LoginModel
import com.example.myapplication.data.model.PaymetModel
import com.example.myapplication.data.model.RegisterModel
import com.example.myapplication.data.response.GetLapByIdResponse
import com.example.myapplication.data.response.GetLapResponse
import com.example.myapplication.data.response.HistoryResponse
import com.example.myapplication.data.response.LoginResponse
import com.example.myapplication.data.response.PaymentResponse
import com.example.myapplication.data.response.ProfileResponse
import com.example.myapplication.data.response.RegisterResponse
import kotlin.Exception

class Repository(private val serviceApi: ServiceApi) {
    fun userRegister(name : String,email : String,password : String,no_telp : String,confirm_password : String):LiveData<FetchResult<RegisterResponse>> = liveData{
        emit(FetchResult.Loading)
        try {
            val postModel = RegisterModel(
                name = name,
                email = email,
                password = password,
                no_telp = no_telp,
                confirm_password = confirm_password

            )
            val bodyResponse = serviceApi.register(postModel = postModel)
            emit(FetchResult.Success(bodyResponse))

        }catch (diskIO:Exception){
            emit(FetchResult.Error(diskIO.message.toString()))
        }
    }


    fun userLogin(email: String,password: String):LiveData<FetchResult<LoginResponse>> = liveData {
        emit(FetchResult.Loading)
        try {
            val postModel = LoginModel(
                email = email,
                password = password
            )
            val bodyResponse = serviceApi.login(postModel = postModel)
            emit(FetchResult.Success(bodyResponse))

        }catch (diskIO:Exception){
            emit(FetchResult.Error(diskIO.message.toString()))
        }
    }



    fun userProfile():LiveData<FetchResult<ProfileResponse>> = liveData {
        emit(FetchResult.Loading)
        try {
            val bodyResponse = serviceApi.getProfile()
            emit(FetchResult.Success(bodyResponse))

        }catch (diskIO:Exception){
            emit(FetchResult.Error(diskIO.message.toString()))
        }
    }


    fun getLapangan(tanggal:String):LiveData<FetchResult<GetLapResponse>> = liveData {
        emit(FetchResult.Loading)
        try {
            val bodyResponse = serviceApi.getLapangan(tanggal)
            emit(FetchResult.Success(bodyResponse))

        }catch (diskIO:Exception){
            emit(FetchResult.Error(diskIO.message.toString()))
        }
    }

    fun getLapById(id:String,tanggal: String):LiveData<FetchResult<GetLapByIdResponse>> = liveData {
        emit(FetchResult.Loading)
        try {
            val bodyResponse = serviceApi.getLapById(id,tanggal)
            emit(FetchResult.Success(bodyResponse))

        }catch (diskIO:Exception){
            emit(FetchResult.Error(diskIO.message.toString()))
        }
    }

    fun payment(id_lapangan:String,tanggal: String):LiveData<FetchResult<PaymentResponse>> = liveData {
        emit(FetchResult.Loading)
        try {
            val postModel = PaymetModel(
                id_lapangan = id_lapangan,
                tanggal = tanggal
            )
            val bodyResponse = serviceApi.payment(postModel)
            emit(FetchResult.Success(bodyResponse))

        }catch (diskIO:Exception){
            emit(FetchResult.Error(diskIO.message.toString()))
        }
    }


    fun getAllHistory():LiveData<FetchResult<HistoryResponse>> = liveData {
        emit(FetchResult.Loading)
        try {

            val bodyResponse = serviceApi.getAllHistory()
            emit(FetchResult.Success(bodyResponse))

        }catch (diskIO:Exception){
            emit(FetchResult.Error(diskIO.message.toString()))
        }
    }
}