package com.akosivis.androiddevelopertechnicalchallenge.repository

import com.akosivis.androiddevelopertechnicalchallenge.database.Product
import com.akosivis.androiddevelopertechnicalchallenge.database.ProductDao
import com.akosivis.androiddevelopertechnicalchallenge.model.ProductListResponse

interface ProductRepository {
    fun getProducts(): List<Product>

    fun addProduct(product: Product)
}

class ProductRepositoryImpl(private val dao: ProductDao): ProductRepository {
    override fun getProducts(): List<Product> {
        return dao.getProducts()
    }

    override fun addProduct(product: Product) {
        dao.insertProduct(product)
    }
}