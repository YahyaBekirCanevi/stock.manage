package com.canevi.stock.manage.document

import org.springframework.data.annotation.Id
import org.springframework.data.couchbase.core.mapping.Document
import org.springframework.data.couchbase.core.mapping.Field
import org.springframework.data.couchbase.repository.Collection
import org.springframework.data.couchbase.repository.Scope
import java.time.LocalDateTime
import java.util.*

@Document
@Collection("product")
@Scope("inventory")
data class Product(
    @Id
    val id: String = UUID.randomUUID().toString(),
    @Field
    val name: String,
    @Field
    val description: String,
    @Field
    val price: Double,
    @Field
    val categoryIds: MutableList<String> = mutableListOf(),
    @Field
    val imageIds: MutableList<String> = mutableListOf(),
    @Field
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Field
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    override fun toString(): String {
        return "Product(id='$id', name='$name', description='$description', price=$price, categoryIds=$categoryIds, imageIds=$imageIds, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}