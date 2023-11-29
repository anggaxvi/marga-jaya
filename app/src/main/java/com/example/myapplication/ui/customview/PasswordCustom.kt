package com.example.myapplication.ui.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.myapplication.R

class PasswordCustom : AppCompatEditText {

    constructor(context: Context) : super(context){

        init()

    }

    constructor(context: Context, attrs : AttributeSet) :super(context, attrs){

        init()

    }

    constructor(context: Context, attrs : AttributeSet, defStyleAttr : Int) : super(context, attrs, defStyleAttr){

        init()

    }

    private fun init() {
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               when {
                   s.toString().isEmpty() -> {
                       setError("password tidak boleh kosong",null)
                   }

                   s.toString().length < 8 -> {
                       setError("password harus lebih dari 8 karakter",null)
                   }

                   else -> error = null
               }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }
}