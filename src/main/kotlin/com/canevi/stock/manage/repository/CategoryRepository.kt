package com.canevi.stock.manage.repository

import com.canevi.stock.manage.document.Category
import org.springframework.data.couchbase.repository.CouchbaseRepository
import org.springframework.data.couchbase.repository.Query

interface CategoryRepository : CouchbaseRepository<Category, String> {

    @Query("#{#n1ql.selectEntity} WHERE LOWER(name) LIKE LOWER($1)")
    fun findByNameLike(name: String): List<Category>

    fun findAllByIdIn(categoryIds: List<String>): List<Category>
}