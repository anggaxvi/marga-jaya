package com.example.myapplication.data.pref

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    private var pref : SharedPreferences = context.getSharedPreferences(PREFENCES_NAME,Context.MODE_PRIVATE)

    fun saveToken(token:String){
        val editor = pref.edit()
        editor.putString(USER_TOKEN,token)
        editor.apply()
    }


    fun getToken():String?{
        return pref.getString(USER_TOKEN,null)

    }

    fun clearToken(){
        val editor = pref.edit()
        editor.remove(USER_TOKEN)
        editor.apply()
    }


    fun saveEmail(email:String){
        val editor = pref.edit()
        editor.putString(EMAIL_USER,email)
        editor.apply()
    }

    fun getEmail():String?{
        return pref.getString(EMAIL_USER,null)
    }

    fun clearEmail(){
        val editor = pref.edit()
        editor.remove(EMAIL_USER)
        editor.apply()
    }



    companion object{
        val PREFENCES_NAME = "user_pref"
        val USER_TOKEN = "user_token"
        val EMAIL_USER = "user_email"

    }
}