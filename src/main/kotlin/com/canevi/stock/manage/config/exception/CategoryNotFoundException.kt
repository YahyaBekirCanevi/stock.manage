package com.canevi.stock.manage.config.exception

class CategoryNotFoundException(categoryId: String)
    : ResourceNotFoundException("Category with ID $categoryId not found")