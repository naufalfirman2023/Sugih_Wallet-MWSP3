package com.example.sugih_wallet


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class AdapterHistory (val context : Context, val datanya : ArrayList<ResponHistory>) : RecyclerView.Adapter<AdapterHistory.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = datanya[position]
        if (currentItem.type == "in") {
            holder.itemView.findViewById<CardView>(R.id.cardnya).setCardBackgroundColor(ContextCompat.getColor(context, R.color.ijo))
        }else {
            holder.itemView.findViewById<CardView>(R.id.cardnya).setCardBackgroundColor(ContextCompat.getColor(context, R.color.red))

        }

        holder.tipe.text = currentItem.type
        holder.tujuan.text = currentItem.to
        holder.dari.text = currentItem.from
        holder.besaruang.text = "Rp. "+currentItem.ballance
        holder.time.text = currentItem.time

    }

    override fun getItemCount(): Int {
        return datanya.size
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tujuan : TextView = itemView.findViewById(R.id.tujuan)
        val besaruang : TextView = itemView.findViewById(R.id.besaruang)
        val time : TextView = itemView.findViewById(R.id.timenya)
        val tipe : TextView = itemView.findViewById(R.id.typenya)
        val dari : TextView = itemView.findViewById(R.id.dari)
        val card : CardView = itemView.findViewById(R.id.cardnya)
    }

}