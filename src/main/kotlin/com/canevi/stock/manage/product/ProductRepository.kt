package com.canevi.stock.manage.product

import org.springframework.data.couchbase.repository.CouchbaseRepository
import org.springframework.data.couchbase.repository.Query

interface ProductRepository : CouchbaseRepository<Product, String> {

    @Query("#{#n1ql.selectEntity} WHERE name LIKE $1")
    fun findByNameLike(namePattern: String): List<Product>
}
