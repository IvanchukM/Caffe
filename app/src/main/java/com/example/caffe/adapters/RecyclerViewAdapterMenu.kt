package com.example.caffe.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.caffe.R
import com.example.caffe.activities.ShoppingCartActivity
import com.example.caffe.db.DAO.CoffeeOrderDao
import com.example.caffe.db.databases.CoffeeDatabase
import com.example.caffe.model.CoffeeModel
import java.lang.Exception

class RecyclerViewAdapterMenu (private val context: Context): RecyclerView.Adapter<RecyclerViewAdapterMenu.CoffeeViewHolder>(){
    private val TAG = "RecyclerViewAdapter"

    private var coffeeList: List<CoffeeModel> = listOf()
    private val intent = Intent(context, ShoppingCartActivity::class.java)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoffeeViewHolder {
        return CoffeeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_coffee_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return  coffeeList.size
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        holder.coffeeNameTxt.text = coffeeList[position].name
        holder.coffeeVolumeMediumTxt.text = coffeeList[position].volumeMedium
        holder.coffeeVolumeBigTxt.text = coffeeList[position].volumeBig
        holder.coffeePriceMediumTxt.text = coffeeList[position].priceMedium
        holder.coffeePriceBigTxt.text = coffeeList[position].priceBig

        holder.bigButton.setOnClickListener {
            intent.putExtra("name", holder.coffeeNameTxt.text.toString())
            intent.putExtra("volume", holder.coffeeVolumeBigTxt.text.toString())
            intent.putExtra("price", holder.coffeePriceBigTxt.text.toString())
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            context.startActivity(intent)
        }

        holder.mediumButton.setOnClickListener {
            intent.putExtra("name", holder.coffeeNameTxt.text.toString())
            intent.putExtra("volume", holder.coffeeVolumeMediumTxt.text.toString())
            intent.putExtra("price", holder.coffeePriceMediumTxt.text.toString())
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION

            context.startActivity(intent)
        }

    }


    fun setCoffeeListItem(coffeeList: List<CoffeeModel>){
        this.coffeeList = coffeeList
        
        try {
            notifyDataSetChanged()
        }catch (e: Exception){
            Log.d(TAG, "setCoffeeListItem: $e")
        }
    }

    inner class CoffeeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val coffeeNameTxt: TextView = itemView.findViewById(R.id.coffeeName)
        val coffeeVolumeMediumTxt: TextView = itemView.findViewById(R.id.coffeeVolumeMedium)
        val coffeeVolumeBigTxt: TextView = itemView.findViewById(R.id.coffeeVolumeBig)
        val coffeePriceMediumTxt: TextView = itemView.findViewById(R.id.coffeePriceMedium)
        val coffeePriceBigTxt: TextView = itemView.findViewById(R.id.coffeePriceBig)
        val bigButton: Button = itemView.findViewById(R.id.add_coffee_big)
        val mediumButton: Button = itemView.findViewById(R.id.add_coffee_medium)
    }

}