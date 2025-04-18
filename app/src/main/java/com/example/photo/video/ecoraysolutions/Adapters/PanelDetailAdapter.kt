package com.example.photo.video.ecoraysolutions.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.photo.video.ecoraysolutions.Models.PanelModel
import com.example.photo.video.ecoraysolutions.R

class PanelDetailAdapter(val context: Context, private var arrayList: MutableList<PanelModel>) :
    RecyclerView.Adapter<PanelDetailHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PanelDetailHolder {
        val inflate = LayoutInflater.from(context)
        val view = inflate.inflate(R.layout.panal_itemview, parent, false)
        return PanelDetailHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: PanelDetailHolder, position: Int) {
        holder.name.text = arrayList[position].nameHeadline
        arrayList[position].imageViewPanel?.let { holder.image.setImageResource(it) }
        holder.capacity.text = arrayList[position].capacity
        holder.price.text = arrayList[position].price
    }
}

class PanelDetailHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.textpanal)
    val image: ImageView = itemView.findViewById(R.id.imagePanel)
    val capacity: TextView = itemView.findViewById(R.id.capacity)
    val price: TextView = itemView.findViewById(R.id.capacity)
}