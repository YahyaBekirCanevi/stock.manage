package com.canevi.stock.manage.web.dto

import com.canevi.stock.manage.document.Image
import java.util.Base64

data class ImageDTO(
    val id: String,
    val productId: String,
    val imageData: String
) {
    companion object {
        fun mapToModel(dto: ImageDTO) : Image = Image(
            id = dto.id,
            productId = dto.productId,
            imageData = Base64.getDecoder().decode(dto.imageData)
        )
        fun mapToNewModel(dto: ImageDTO) : Image = Image(
            productId = dto.productId,
            imageData = Base64.getDecoder().decode(dto.imageData)
        )
        fun mapModelToDTO(model: Image) : ImageDTO = ImageDTO(
            id = model.id,
            productId = model.productId,
            imageData = Base64.getEncoder().encodeToString(model.imageData)
        )
    }
}