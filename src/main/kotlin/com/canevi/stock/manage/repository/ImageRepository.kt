package com.canevi.stock.manage.repository

import com.canevi.stock.manage.document.Image
import org.springframework.data.couchbase.repository.CouchbaseRepository

interface ImageRepository : CouchbaseRepository<Image, String> {
    fun findAllByProductId(productId: String): List<Image>
}
