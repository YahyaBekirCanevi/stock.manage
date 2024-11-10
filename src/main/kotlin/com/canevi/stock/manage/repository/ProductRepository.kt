package com.canevi.stock.manage.repository

import com.canevi.stock.manage.document.Product
import org.springframework.data.couchbase.repository.CouchbaseRepository
import org.springframework.data.couchbase.repository.Query

interface ProductRepository : CouchbaseRepository<Product, String> {

    @Query("#{#n1ql.selectEntity} WHERE LOWER(name) LIKE LOWER($1)")
    fun findByNameLike(namePattern: String): List<Product>
}
