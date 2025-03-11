package com.example.photo.video.ecoraysolutions.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.photo.video.ecoraysolutions.Models.SignUpModel
import com.example.photo.video.ecoraysolutions.R

class AdminAdapter(
    val context: Context,
    private var arrayList: ArrayList<SignUpModel>,
    var click: (Int, String) -> Unit
) : RecyclerView.Adapter<AdminHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminHolder {
        val inflate = LayoutInflater.from(context)
        val view = inflate.inflate(R.layout.admindetail, parent, false)
        return AdminHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: AdminHolder, position: Int) {
        holder.name.text = arrayList[position].name
        holder.address.text = arrayList[position].email
        holder.city.text = arrayList[position].city
        holder.gender.text = arrayList[position].type

        holder.approveBtn.setOnClickListener {
            click.invoke(position, "approve")
        }
    }
}

class AdminHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name = itemView.findViewById<TextView>(R.id.name)
    val address = itemView.findViewById<TextView>(R.id.emailshow)
    val city = itemView.findViewById<TextView>(R.id.city)
    val gender = itemView.findViewById<TextView>(R.id.gender)
    val approveBtn = itemView.findViewById<TextView>(R.id.approve)
}