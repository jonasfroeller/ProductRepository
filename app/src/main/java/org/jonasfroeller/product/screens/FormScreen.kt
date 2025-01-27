package org.jonasfroeller.product.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jonasfroeller.product.model.Product
import java.util.UUID

@Composable
fun FormScreen(onSubmit: (Product) -> Unit) {
    var name by remember { mutableStateOf("") }
    var urgent by remember { mutableStateOf(false) }
    var price by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product Name") }
        )

        TextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") }
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(
                checked = urgent,
                onCheckedChange = { urgent = it }
            )
            Text("Urgent")
        }

        Button(
            onClick = {
                val priceDouble = price.toDoubleOrNull() ?: 0.0
                val product = Product(
                    id = UUID.randomUUID(),
                    name = name,
                    urgent = urgent,
                    price = priceDouble
                )
                onSubmit(product)
                name = ""
                urgent = false
                price = ""
            },
            enabled = name.isNotBlank() && price.isNotBlank()
        ) {
            Text("Add Product")
        }
    }
}
