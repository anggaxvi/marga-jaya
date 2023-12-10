package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class PaymentResponse(

	@field:SerializedName("data")
	val data: Redirect? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

data class Redirect(

	@field:SerializedName("redirectUrl")
	val redirectUrl: String? = null
)
