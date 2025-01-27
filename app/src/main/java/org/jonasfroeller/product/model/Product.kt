package org.jonasfroeller.product.model

import java.util.UUID

data class Product(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val urgent: Boolean,
    val price: Double
)
