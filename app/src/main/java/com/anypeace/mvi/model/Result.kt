package com.anypeace.mvi.model

data class Result <T>(
    var resultCd: String? = null,
    var msg: String? = null,
    var payload: T? = null,
)
