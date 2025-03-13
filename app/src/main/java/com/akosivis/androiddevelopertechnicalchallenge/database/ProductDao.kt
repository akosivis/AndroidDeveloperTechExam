package com.akosivis.androiddevelopertechnicalchallenge.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getProducts(): List<Product>

//3

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(products: Product)
}