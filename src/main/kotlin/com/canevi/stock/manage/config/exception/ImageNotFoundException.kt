package com.canevi.stock.manage.config.exception

class ImageNotFoundException(imageId: String)
    : ResourceNotFoundException("Image with Id $imageId not found")