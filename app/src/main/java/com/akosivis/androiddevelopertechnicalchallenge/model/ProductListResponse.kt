package com.akosivis.androiddevelopertechnicalchallenge.model

import com.akosivis.androiddevelopertechnicalchallenge.database.Product
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProductListResponse (
    @SerializedName("products")
    var products: ArrayList<Product> = arrayListOf(),
    @SerializedName("total")
    var total: Int? = null,
    @SerializedName("skip")
    var skip: Int? = null,
    @SerializedName("limit")
    var limit: Int? = null
)