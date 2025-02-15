package org.jonasfroeller.product

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jonasfroeller.product.screens.FormScreen
import org.jonasfroeller.product.screens.ListScreen
import org.jonasfroeller.product.ui.theme.ProductTheme
import org.jonasfroeller.product.viewModel.ProductViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: ProductViewModel = viewModel()
            
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.List, "List") },
                            selected = navController.currentDestination?.route == "list",
                            onClick = { navController.navigate("list") }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Add, "Add") },
                            selected = navController.currentDestination?.route == "form",
                            onClick = { navController.navigate("form") }
                        )
                    }
                }
            ) { padding ->
                NavHost(navController, "list", Modifier.padding(padding)) {
                    composable("list") { ListScreen(viewModel.products, viewModel::deleteProduct, viewModel::updateProduct) }
                    composable("form") { FormScreen { viewModel.createProduct(it); navController.navigate("list") } }
                }
            }
        }
    }
}
