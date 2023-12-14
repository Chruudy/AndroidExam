package com.example.androidexam

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    text: String,
    navController: NavHostController,
) {
    TopAppBar(
        title = {
            Text(text = text)
        },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = {
            IconButton(
                onClick = {
                    // Navigate to the "cart" destination when the cart icon is clicked
                    navController.navigate("cart")
                }
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
            }
            IconButton(onClick = { /* Handle search click */ }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
}




