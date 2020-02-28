package com.example.truecallerapp.model

data class ResponseWrapper(
    var t10thChar: Char? = null,
    var e10thChar: String? = null,
    var wordCountMap: Map<String, Int>? = null
)