package com.example.caffe.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.caffe.R
import com.example.caffe.activities.ShoppingCartActivity
import com.example.caffe.db.entities.CoffeeOrderEntity

class RecyclerViewAdapterOrder (private val context: Context): RecyclerView.Adapter<RecyclerViewAdapterOrder.OrderViewHolder>(){

    private var orderList: List<CoffeeOrderEntity> = listOf()
    private val intent: Intent = Intent(context,ShoppingCartActivity::class.java)
    private val TAG = "RecyclerViewAdapter"
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapterOrder.OrderViewHolder {
        return OrderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_order_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterOrder.OrderViewHolder, position: Int) {
        holder.coffeeNameTxt.text = orderList[position].name
        holder.coffeeVolumeTxt.text = orderList[position].volume
        holder.coffeePriceTxt.text = orderList[position].price

        holder.deleteButton.setOnClickListener{
            intent.putExtra("id", orderList[position].id.toString())
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            context.startActivity(intent)
        }
    }

    fun setOrderListItem(orderList: List<CoffeeOrderEntity>){
        this.orderList = orderList
        notifyDataSetChanged()
    }

    inner class OrderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val coffeeNameTxt: TextView = itemView.findViewById(R.id.order_coffee_name)
        val coffeeVolumeTxt: TextView = itemView.findViewById(R.id.order_coffee_volume)
        val coffeePriceTxt: TextView = itemView.findViewById(R.id.order_coffee_price)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_order_item)
    }

}