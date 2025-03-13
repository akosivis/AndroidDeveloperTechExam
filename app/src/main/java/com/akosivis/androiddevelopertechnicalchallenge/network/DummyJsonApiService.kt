package com.akosivis.androiddevelopertechnicalchallenge.network

import com.akosivis.androiddevelopertechnicalchallenge.database.Product
import com.akosivis.androiddevelopertechnicalchallenge.model.ProductListResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://dummyjson.com/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    interface DummyJsonApiService {
        @GET("products")
        suspend fun getProducts(): ProductListResponse

        @GET("products")
        suspend fun getProductsWithConstraints(@Query("skip") skip: String, @Query("limit") limit: String): ProductListResponse

        @GET("products/{id}")
        suspend fun getProductItem(@Path("id") id: String): Product?

    }

    /**
     * A public Api object that exposes the lazy-initialized Retrofit service
     */
    object DummyJsonApi {
        val retrofitService: DummyJsonApiService by lazy {
            retrofit.create(DummyJsonApiService::class.java)
        }
    }



