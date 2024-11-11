package com.canevi.stock.manage.service

import com.canevi.stock.manage.document.Category
import com.canevi.stock.manage.document.Product
import java.util.*

val category = Category(name = "Food")

val product = Product(
    name = "Tomato",
    description = "description",
    price = 10.0,
    categoryIds = mutableListOf(category.id),
)

val productWithEmptyCategories = Product(
    name = "Tomato",
    description = "description",
    price = 10.0,
)

val productWithUnknownCategory = Product(
    name = "Tomato",
    description = "description",
    price = 10.0,
    categoryIds = mutableListOf(UUID.randomUUID().toString()),
)
