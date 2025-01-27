package org.jonasfroeller.product.client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
	private val retrofit: Retrofit = Retrofit.Builder()
		.baseUrl("http://10.0.2.2:8888")
		.addConverterFactory(GsonConverterFactory.create())
		.build()

	val service: ProductService = retrofit.create(ProductService::class.java)
}
