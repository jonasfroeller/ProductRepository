package org.jonasfroeller.product.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.jonasfroeller.product.client.RetrofitClient
import org.jonasfroeller.product.model.Product

class ProductViewModel: ViewModel() {
    // Single Source of Truth
    private val _products = mutableStateListOf<Product>()
    val products get() = _products.toList()

    init {
        viewModelScope.launch {
            try {
                val products = RetrofitClient.service.getProducts()
                Log.i("ProductViewModel", "Fetched data: $products")
                _products.clear()
                _products.addAll(products)
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error fetching products", e)
            }
        }
    }

    fun createProduct(product: Product) {
        viewModelScope.launch {
            try {
                RetrofitClient.service.createProduct(product); _products.add(product)
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error creating product", e)
            }
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            try {
                RetrofitClient.service.updateProduct(product.id, product)
                val index = _products.indexOfFirst { it.id == product.id }
                if (index != -1) {
                    _products[index] = product
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error updating product", e)
            }
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            try {
                RetrofitClient.service.deleteProduct(product.id)
                _products.remove(product)
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error deleting product", e)
            }
        }
    }
}
