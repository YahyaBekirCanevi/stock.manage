package com.canevi.stock.manage.repository

import com.canevi.stock.manage.document.ProductCategory
import org.springframework.data.couchbase.repository.CouchbaseRepository
import org.springframework.data.couchbase.repository.Query

interface ProductCategoryRepository : CouchbaseRepository<ProductCategory, String> {

    fun findAllByProductId(productId: String): List<ProductCategory>

    @Query("#{#n1ql.selectEntity} WHERE productId = $1 AND categoryId = $2")
    fun findByProductIdAndCategoryId(productId: String, categoryId: String): ProductCategory?

}