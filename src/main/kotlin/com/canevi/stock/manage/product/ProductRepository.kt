package com.canevi.stock.manage.product

import org.springframework.data.couchbase.repository.CouchbaseRepository

interface ProductRepository : CouchbaseRepository<Product, String> {
    fun findByName(name: String): List<Product>
}
