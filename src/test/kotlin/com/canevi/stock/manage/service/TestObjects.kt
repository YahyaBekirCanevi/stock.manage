package com.canevi.stock.manage.service

import com.canevi.stock.manage.document.Category
import com.canevi.stock.manage.document.Product
import java.math.BigDecimal
import java.util.UUID

val category = Category(name = "Food")

val product = Product(
    name = "Tomato",
    description = "description",
    price = BigDecimal.valueOf(10L),
    categoryIdList = mutableListOf(category.id),
)

val productWithEmptyCategories = Product(
    name = "Tomato",
    description = "description",
    price = BigDecimal.valueOf(10L),
    categoryIdList = mutableListOf(),
)

val productWithUnknownCategory = Product(
    name = "Tomato",
    description = "description",
    price = BigDecimal.valueOf(10L),
    categoryIdList = mutableListOf(UUID.randomUUID().toString()),
)
