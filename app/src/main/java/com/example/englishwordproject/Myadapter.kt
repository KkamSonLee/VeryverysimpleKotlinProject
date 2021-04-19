package com.example.englishwordproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class Myadapter(var items:ArrayList<Mydata>): RecyclerView.Adapter<Myadapter.ViewHolder>() {
    var itemOnClickListener:OnItemClickListener?=null

    interface OnItemClickListener{
        fun OnItemClick(holder:ViewHolder, view:View, data:Mydata, position:Int)
    }

    fun remove(pos:Int){
        items.removeAt(pos)
        notifyDataSetChanged()
    }
    fun move(oldPos:Int, newPos:Int){
        val item = items[oldPos]
        items.removeAt(oldPos)
        items.add(newPos, item)
        notifyItemMoved(oldPos, newPos)
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val itemname:TextView = itemView.findViewById(R.id.name)
        val price:TextView = itemView.findViewById(R.id.price)
        init {
            itemView.setOnClickListener {
                itemOnClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemname.text = items[position].name
        holder.price.text = items[position].price
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
