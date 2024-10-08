package com.canevi.stock.manage.document

import org.springframework.data.annotation.Id
import org.springframework.data.couchbase.core.mapping.Document
import org.springframework.data.couchbase.core.mapping.Field
import org.springframework.data.couchbase.repository.Collection
import org.springframework.data.couchbase.repository.Scope
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
    val quantity: Int
)