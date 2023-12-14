import androidx.lifecycle.ViewModel
import com.example.androidexam.CartItem
import com.example.androidexam.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun addToCart(product: Product, quantity: Int) {
        val currentItems = _cartItems.value.toMutableList()
        currentItems.add(CartItem(product, quantity))
        _cartItems.value = currentItems
    }

    fun removeItemFromCart(itemToRemove: CartItem) {
        val currentItems = _cartItems.value.toMutableList()
        currentItems.remove(itemToRemove)
        _cartItems.value = currentItems
    }

fun getCartItems(): List<CartItem> {
        // Return the list of cart items
        return _cartItems.value.orEmpty()
    }

    fun clearCart() {
        // Clear the cartItems list
        _cartItems.value = emptyList()
    }
}
