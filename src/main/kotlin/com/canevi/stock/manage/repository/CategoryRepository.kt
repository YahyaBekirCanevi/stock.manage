package com.canevi.stock.manage.repository

import com.canevi.stock.manage.document.Category
import org.springframework.data.couchbase.repository.CouchbaseRepository

interface CategoryRepository : CouchbaseRepository<Category, String> {

    fun findByName(name: String): Category?

    fun findAllByIdIn(categoryIds: List<String>): List<Category>
}