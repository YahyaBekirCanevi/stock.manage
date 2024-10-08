package com.canevi.stock.manage.web.response

data class ErrorResponse(
    val status: Int,
    val message: String,
    val timestamp: Long
)