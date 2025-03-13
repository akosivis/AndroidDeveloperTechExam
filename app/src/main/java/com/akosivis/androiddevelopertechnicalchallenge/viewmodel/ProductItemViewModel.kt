package com.akosivis.androiddevelopertechnicalchallenge.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.akosivis.androiddevelopertechnicalchallenge.database.Product
import com.akosivis.androiddevelopertechnicalchallenge.network.DummyJsonApi
import kotlinx.coroutines.launch
import java.io.IOException

class ProductItemViewModel: ViewModel() {
    var productItemUiState: ProductItemUiState by mutableStateOf(ProductItemUiState.Loading)
        private set

    fun fetchProductItem(id: String) {
        viewModelScope.launch {
            productItemUiState = ProductItemUiState.Loading
            productItemUiState = try {
                val productItem = DummyJsonApi.retrofitService.getProductItem(id)
                Log.d("Product", "productItem: $productItem")
                if (productItem != null) {
                    ProductItemUiState.Success(
                        item = productItem
                    )
                } else {
                    ProductItemUiState.Error("Item cannot be fetched!")
                }
            } catch (e: IOException) {
                Log.e("Product item", "io exception")
                ProductItemUiState.Error("IOException!")
            }
        }
    }
}

sealed interface ProductItemUiState {
    data class Success(val item: Product) : ProductItemUiState
    data class Error(val message: String) : ProductItemUiState
    object Loading : ProductItemUiState
}

class ProductItemViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductItemViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}