package com.example.androidexam

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
                    ProductOverview(productUiState = overviewViewModel.productUiState)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductOverview(productUiState: ProductUiState) {
    Scaffold(topBar = { TopBar(text = "Product", showArrow = false) }) { innerPadding ->
        when (productUiState) {
            is ProductUiState.Loading -> LoadingScreen()
            is ProductUiState.Success -> {
                val products = (productUiState as ProductUiState.Success).products
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    items(products) { product ->
                        ProductItem(product = product)
                    }
                }
            }
            else -> {}
        }
    }
}



@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = product.title, fontWeight = FontWeight.Bold)
            Text(text = "Price: $${product.price}")
            Text(text = "Description: ${product.description}")
            Text(text = "Category: ${product.category}")
            Image(
                painter = rememberCoilPainter(request = product.image),
                contentDescription = null, // Provide a proper description
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
            Text(text = "Rating: ${product.rating.rate} (${product.rating.count} ratings)")
        }
    }
}

fun <Painter> rememberCoilPainter(request: String): Painter {
 return rememberCoilPainter(request)
}

fun CardElevation(defaultElevation: Dp): CardElevation {
    return CardElevation(defaultElevation)
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
