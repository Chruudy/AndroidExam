package com.example.androidexam.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidexam.Product
import com.example.androidexam.network.FakeStoreApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface ProductUiState {
    data class Success(val products: List<Product>) : ProductUiState
    object Error : ProductUiState
    object Loading : ProductUiState
}


class OverviewViewModel : ViewModel() {

    var productUiState: ProductUiState by mutableStateOf(ProductUiState.Loading)
        private set

    var productList: List<Product> by mutableStateOf(emptyList())

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            productUiState = ProductUiState.Loading
            productUiState = try {
                val listResult = FakeStoreApi.retrofitService.getImage()
                productList = listResult
                ProductUiState.Success(listResult)
            } catch (e: IOException) {
                ProductUiState.Error
            } catch (e: HttpException) {
                ProductUiState.Error
            }
        }
    }
}