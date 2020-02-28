package com.example.truecallerapp.model

import okhttp3.ResponseBody

data class ResponseBodyWrapper(
    val response1: ResponseBody? = null,
    val response2: ResponseBody? = null,
    var response3: ResponseBody? = null
)