package com.example.pos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cartAdapter: CartAdapter
    private val cartItems = mutableListOf<Pair<Product, Int>>()
    private var totalAmount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val products = listOf(
            Product("Coffee", R.drawable.coffee, 50, 10),
            Product("Burger", R.drawable.burger, 120, 8),
            Product("Pizza", R.drawable.pizza, 200, 5),
            Product("Sandwich", R.drawable.sandwich, 80, 6)
        )

        binding.productRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.productRecyclerView.adapter = ProductAdapter(products) { product ->
            val index = cartItems.indexOfFirst { it.first.name == product.name }
            if (index >= 0) {
                val updated = cartItems[index].second + 1
                cartItems[index] = product to updated
            } else {
                cartItems.add(product to 1)
            }
            updateTotal()
            cartAdapter.notifyDataSetChanged()
        }

        cartAdapter = CartAdapter(cartItems)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = cartAdapter

        binding.purchaseButton.setOnClickListener {
            if (cartItems.isNotEmpty()) {
                Toast.makeText(this, "Purchase Successful!", Toast.LENGTH_SHORT).show()
                cartItems.clear()
                totalAmount = 0
                updateTotal()
                cartAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateTotal() {
        totalAmount = cartItems.sumOf { it.first.price * it.second }
        binding.cartTotal.text = "Total: ₹$totalAmount"
    }
}
