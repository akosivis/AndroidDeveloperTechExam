package com.akosivis.androiddevelopertechnicalchallenge.viewmodel

import android.net.http.HttpException
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.akosivis.androiddevelopertechnicalchallenge.database.Product
import com.akosivis.androiddevelopertechnicalchallenge.network.DummyJsonApi
import com.akosivis.androiddevelopertechnicalchallenge.repository.ProductRepository
import com.akosivis.androiddevelopertechnicalchallenge.repository.ProductRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class ProductListViewModel(val repo: ProductRepositoryImpl): ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var productListUiState: ProductListUiState by mutableStateOf(ProductListUiState.Success(list = ArrayList()))
        private set
    var skipVal by mutableStateOf("")
    var limitVal by mutableStateOf("")
    private var remainingItemsToLoad: Int = 0
    private var lastSkipVal: Int = 0
    var isListLoading by mutableStateOf(false)

    init {
        // fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch (Dispatchers.IO) {
            productListUiState = ProductListUiState.Loading
            productListUiState = try {
                if (skipVal.toInt() >= 0 || limitVal.toInt() >= 0) {
                    val listResult = DummyJsonApi.retrofitService.getProductsWithConstraints(
                        skip = skipVal,
                        limit = if (limitVal.toInt() >= 10) {
                            "10"
                        } else {
                            limitVal
                        }).products
                    remainingItemsToLoad -= listResult.size
                    lastSkipVal += listResult.size + 1
                    for (item in listResult) {
                        repo.addProduct(item)
                    }
                    Log.d("Products", "fetchProducts: $listResult")
                    ProductListUiState.Success(
                        list = listResult
                    )
                } else {
                    ProductListUiState.Success(
                        list = ArrayList()
                    )
                }


            } catch (e: IOException) {
                Log.e("Products", "io exception")
                ProductListUiState.Error("IOException!")
            }
        }
    }

    fun setSkip(input: String) {
        if (input.trim().isNotEmpty()) {
            skipVal = input.trim()
        } else {
            skipVal = "0"
        }
        lastSkipVal = skipVal.toInt()
    }

    fun setLimit(input: String) {
        if (input.trim().isNotEmpty()) {
            limitVal = input.trim()
        } else {
            limitVal = "0"
        }
        remainingItemsToLoad = limitVal.toInt()
    }

    fun loadMoreItems() {
        if (remainingItemsToLoad > 0) {
            Log.d("loadMoreItems()", "LOAD REMAINING $remainingItemsToLoad")
            viewModelScope.launch (Dispatchers.IO) {
                val listResult = DummyJsonApi.retrofitService.getProductsWithConstraints(
                    skip = lastSkipVal.toString(),
                    limit = if (remainingItemsToLoad >= 10) {
                        "10"
                    } else {
                        remainingItemsToLoad.toString()
                    }).products
                remainingItemsToLoad -= listResult.size
                lastSkipVal += listResult.size + 1
                for (item in listResult) {
                    repo.addProduct(item)
                }
                Log.d("Products", "loadMoreItems: $listResult")
                (productListUiState as? ProductListUiState.Success)?.list?.addAll(listResult)
                isListLoading = false
            }
        } else {
            isListLoading = false
        }
    }
}

sealed interface ProductListUiState {
    data class Success(val list: ArrayList<Product>) : ProductListUiState
    data class Error(val message: String) : ProductListUiState
    object Loading : ProductListUiState
}

class ProductListViewModelFactory(val repo: ProductRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}