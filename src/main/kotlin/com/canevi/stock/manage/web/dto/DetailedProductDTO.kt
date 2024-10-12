package com.canevi.stock.manage.web.dto

data class DetailedProductDTO(
    val id: String,
    val name: String,
    val quantity: Int,
    val description: String?,
    val size: String?,
    val attributes: Map<String, String>?,
    val categories: List<String>  // List of category names or IDs
)