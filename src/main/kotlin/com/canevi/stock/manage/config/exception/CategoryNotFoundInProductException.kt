package com.canevi.stock.manage.config.exception

class CategoryNotFoundInProductException(categoryId: String)
    : ResourceNotFoundException("Category with ID $categoryId not found in product")