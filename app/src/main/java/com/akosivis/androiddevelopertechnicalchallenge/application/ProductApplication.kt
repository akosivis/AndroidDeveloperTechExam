package com.akosivis.androiddevelopertechnicalchallenge.application

import android.app.Application
import com.akosivis.androiddevelopertechnicalchallenge.database.AppDatabase
import com.akosivis.androiddevelopertechnicalchallenge.repository.ProductRepository
import com.akosivis.androiddevelopertechnicalchallenge.repository.ProductRepositoryImpl

class ProductApplication: Application() {
    val database by lazy { AppDatabase.getInstance(this) }

    val productRepo by lazy {
        ProductRepositoryImpl(
            database.productDao()
        )
    }
}