package com.example.androidexam

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidexam.overview.ProductUiState
import com.example.androidexam.ui.theme.AndroidExamTheme
import com.example.androidexam.overview.OverviewViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidExamTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val overviewViewModel: OverviewViewModel = viewModel()
                    NavigationController(productUiState = overviewViewModel.productUiState)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationController(productUiState: ProductUiState, navController: NavHostController = rememberNavController(), startDestination: String = "product") {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("product") {
            ProductList(productUiState = productUiState, onNavigate = { navController.navigate("product") }, navController = navController)
        }
        composable("product/{productId}", arguments = listOf(navArgument("productId") {
            type = NavType.IntType
        })) { backStackEntry ->
            ProductOverview(
                product = (productUiState as ProductUiState.Success).products[backStackEntry.arguments?.getInt("productId") ?: 0],
                onNavigate = { navController.navigate("product/{productId}")},
                navController = navController
            )
        }
    }
}


@Composable
fun ProductList(productUiState: ProductUiState, onNavigate: () -> Unit, navController: NavHostController) {
    Scaffold(topBar = { TopBar(text = "Products", navController) }) { innerPadding ->
        when (productUiState) {
            is ProductUiState.Loading -> LoadingScreen()
            is ProductUiState.Success -> {
                val products = (productUiState as ProductUiState.Success).products
                Log.d(TAG, "ProductList: ${products.size}")
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    items(products) { product ->
                        ProductItem(product = product, navController = navController)
                    }
                }
            }
            else -> {}
        }
    }
}


@Composable
fun ProductItem(product: Product, navController: NavHostController) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                        navController.navigate("product/${product.id-1}")
                        Log.d(TAG, "ProductItem: ${product.id} ")
                    },
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image on the left
            AsyncImage(
                modifier = Modifier
                    .size(120.dp), // Set the size of the image
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.image)
                    .crossfade(true)
                    .build(),
                contentDescription = "Product image"
            )

            Spacer(modifier = Modifier.width(16.dp)) // Add spacing between image and text

            // Title, Category, and Price to the right of the image
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = product.title, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp)) // Add spacing between title and category
                Text(text = product.category)
                Spacer(modifier = Modifier.height(8.dp)) // Add more spacing
                Text(
                    text = buildAnnotatedString {
                        append("Price: ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("$${product.price}")
                        }
                    },
                )
                Text(text = "Rating: ${product.rating.rate} (${product.rating.count} ratings)")
            }
        }
    }
}


@Composable
fun LoadingScreen() {
    Text("loading...")
}

@Composable
fun ResultScreen(title: String) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Text(text = title)
    }
}
