package org.jonasfroeller.product.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jonasfroeller.product.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    products: List<Product>,
    onDelete: (Product) -> Unit,
    onUpdate: (Product) -> Unit
) {
    var editProduct by remember { mutableStateOf<Product?>(null) }
    
    LazyColumn(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(products) { product ->
            Card {
                Row(Modifier.padding(8.dp)) {
                    Column(Modifier.weight(1f)) {
                        Text(product.name)
                        Text("$${product.price}")
                        if (product.urgent) Text("URGENT")
                    }
                    IconButton(onClick = { editProduct = product }) { 
                        Icon(Icons.Default.Edit, null) 
                    }
                    IconButton(onClick = { onDelete(product) }) { 
                        Icon(Icons.Default.Delete, null) 
                    }
                }
            }
        }
    }

    editProduct?.let { product ->
        var name by remember { mutableStateOf(product.name) }
        var urgent by remember { mutableStateOf(product.urgent) }
        var price by remember { mutableStateOf(product.price.toString()) }
        
        AlertDialog(
            onDismissRequest = { editProduct = null },
            title = { Text("Edit") },
            text = {
                Column {
                    TextField(name, { name = it })
                    TextField(price, { price = it })
                    Row(verticalAlignment = Alignment.CenterVertically) { 
                        Checkbox(urgent, { urgent = it })
                        Text("Urgent", Modifier.clickable { urgent = !urgent })
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    onUpdate(product.copy(
                        name = name,
                        urgent = urgent,
                        price = price.toDoubleOrNull() ?: 0.0
                    ))
                    editProduct = null
                }) { Text("Update") }
            }
        )
    }
}
