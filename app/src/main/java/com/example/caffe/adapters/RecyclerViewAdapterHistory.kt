package com.example.caffe.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.caffe.R
import com.example.caffe.db.entities.OrdersHistoryEntity

class RecyclerViewAdapterHistory (private val context: Context): RecyclerView.Adapter<RecyclerViewAdapterHistory.HistoryViewHolder>(){

    private var historyList: List<OrdersHistoryEntity> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapterHistory.HistoryViewHolder {
        return HistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history_layout,parent,false))
    }

    override fun getItemCount(): Int {
       return historyList.size
    }

    override fun onBindViewHolder(
        holder: RecyclerViewAdapterHistory.HistoryViewHolder,
        position: Int
    ) {
        holder.coffeeNameTxt.text = historyList[position].name
        holder.coffeeVolumeTxt.text = historyList[position].volume
        holder.coffeePriceTxt.text = historyList[position].price
    }

    fun setHistoryListItem(historyList: List<OrdersHistoryEntity>){
        this.historyList = historyList
        notifyDataSetChanged()
    }

    inner class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val coffeeNameTxt: TextView = itemView.findViewById(R.id.history_coffee_name)
        val coffeeVolumeTxt: TextView = itemView.findViewById(R.id.history_coffee_volume)
        val coffeePriceTxt: TextView = itemView.findViewById(R.id.history_coffee_price)
    }


}