package org.jonasfroeller.product.client

import org.jonasfroeller.product.model.Product
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface ProductService {
	@GET("products")
	suspend fun getProducts(): List<Product>

	@POST("products")
	suspend fun createProduct(@Body product: Product)

	@PUT("products/{id}")
	suspend fun updateProduct(@Path("id") id: UUID, @Body product: Product)

	@DELETE("products/{id}")
	suspend fun deleteProduct(@Path("id") id: UUID)
}
