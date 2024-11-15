package com.canevi.stock.manage.document

import org.springframework.data.couchbase.core.mapping.Document
import org.springframework.data.couchbase.repository.Collection
import org.springframework.data.couchbase.repository.Scope
import java.util.*

@Document
@Collection("product_image")
@Scope("inventory")
data class Image(
    val id: String = UUID.randomUUID().toString(),
    val productId: String,
    val imageData: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Image

        if (id != other.id) return false
        if (productId != other.productId) return false
        if (!imageData.contentEquals(other.imageData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + productId.hashCode()
        result = 31 * result + imageData.contentHashCode()
        return result
    }
}