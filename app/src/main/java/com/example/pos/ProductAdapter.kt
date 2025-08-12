package com.example.pos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.databinding.ItemProductBinding

class ProductAdapter(
    private val products: List<Product>,
    private val onAddToCart: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.binding.productName.text = product.name
        holder.binding.productPrice.text = "₹${product.price}"
        holder.binding.productStock.text = "Stock: ${product.stock}"
        holder.binding.productImage.setImageResource(product.imageRes)

        holder.binding.addToCartButton.isEnabled = product.stock > 0
        holder.binding.addToCartButton.setOnClickListener {
            if (product.stock > 0) {
                product.stock--
                notifyItemChanged(position)
                onAddToCart(product)
            }
        }
    }
}
