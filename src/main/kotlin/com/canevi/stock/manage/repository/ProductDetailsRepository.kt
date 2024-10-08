package com.canevi.stock.manage.repository

import com.canevi.stock.manage.document.ProductDetails
import org.springframework.data.couchbase.repository.CouchbaseRepository

interface ProductDetailsRepository : CouchbaseRepository<ProductDetails, String>