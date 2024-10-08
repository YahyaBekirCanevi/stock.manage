package com.canevi.stock.manage.document

import org.springframework.data.annotation.Id
import org.springframework.data.couchbase.core.mapping.Document
import org.springframework.data.couchbase.core.mapping.Field
import org.springframework.data.couchbase.repository.Collection
import org.springframework.data.couchbase.repository.Scope

@Document
@Collection("product_detail")
@Scope("inventory")
data class ProductDetails(
    @Id
    val productId: String,  // This links back to the product document
    @Field
    val description: String? = null,
    @Field
    val size: String? = null, // Optional field
    @Field
    val attributes: Map<String, String>? = null  // Additional key-value pairs
)
