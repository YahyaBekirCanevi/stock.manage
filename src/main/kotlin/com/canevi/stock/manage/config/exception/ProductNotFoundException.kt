package com.canevi.stock.manage.config.exception

class ProductNotFoundException(productId: String)
    : ResourceNotFoundException("Product with ID $productId not found")