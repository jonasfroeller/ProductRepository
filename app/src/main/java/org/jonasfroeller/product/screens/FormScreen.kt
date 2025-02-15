package org.jonasfroeller.product.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jonasfroeller.product.model.Product

@Composable
fun FormScreen(onSubmit: (Product) -> Unit) {
    var name by remember { mutableStateOf("") }
    var urgent by remember { mutableStateOf(false) }
    var price by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        TextField(name, { name = it }, label = { Text("Name") })
        TextField(price, { price = it }, label = { Text("Price") })
        Row(verticalAlignment = Alignment.CenterVertically) { 
            Checkbox(urgent, { urgent = it })
            Text("Urgent", Modifier.clickable { urgent = !urgent })
        }
        Button(
            onClick = {
                onSubmit(Product(name = name, urgent = urgent, price = price.toDoubleOrNull() ?: 0.0))
                name = ""; urgent = false; price = ""
            },
            enabled = name.isNotBlank() && price.isNotBlank()
        ) { Text("Add") }
    }
}
