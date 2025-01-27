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
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    
    var editName by remember { mutableStateOf("") }
    var editUrgent by remember { mutableStateOf(false) }
    var editPrice by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(product.name)
                        Text("Price: $${product.price}")
                        if (product.urgent) {
                            Text("URGENT")
                        }
                    }
                    
                    Row {
                        IconButton(onClick = {
                            selectedProduct = product
                            editName = product.name
                            editUrgent = product.urgent
                            editPrice = product.price.toString()
                            showEditDialog = true
                        }) {
                            Icon(Icons.Default.Edit, "Edit")
                        }
                        IconButton(onClick = { onDelete(product) }) {
                            Icon(Icons.Default.Delete, "Delete")
                        }
                    }
                }
            }
        }
    }

    if (showEditDialog && selectedProduct != null) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Edit Product") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Product Name") }
                    )
                    
                    TextField(
                        value = editPrice,
                        onValueChange = { editPrice = it },
                        label = { Text("Price") }
                    )
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = editUrgent,
                            onCheckedChange = { editUrgent = it }
                        )
                        Text("Urgent")
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val priceDouble = editPrice.toDoubleOrNull() ?: 0.0
                        selectedProduct?.let { product ->
                            onUpdate(product.copy(
                                name = editName,
                                urgent = editUrgent,
                                price = priceDouble
                            ))
                        }
                        showEditDialog = false
                    },
                    enabled = editName.isNotBlank() && editPrice.isNotBlank()
                ) {
                    Text("Update")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
