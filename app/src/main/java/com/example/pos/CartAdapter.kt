package com.example.pos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.databinding.ItemCartBinding

class CartAdapter(
    private val cartItems: List<Pair<Product, Int>>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount() = cartItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val (product, qty) = cartItems[position]
        holder.binding.cartItemName.text = product.name
        holder.binding.cartItemQty.text = "x$qty"
        holder.binding.cartItemPrice.text = "₹${product.price * qty}"
    }
}
