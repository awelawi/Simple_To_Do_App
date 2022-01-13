package com.awelawi.todoapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//A bridge that tells the recyclerview how 2 display data given
class TaskItemAdapter(val listOfItems: List<String>, val longClickListener: onLongClickListener) :
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {
    //Usually involves inflating a layout from xml and returning the holder
    interface onLongClickListener {
        fun onItemLongClicked(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView =
            inflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val item = listOfItems.get(position)
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    //Provide a direct ref to each of the views                                                                                             6within a data item
    //Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Store reference to elements in layout view
        val textView: TextView
        init {
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            //                Log.i("caren", "this item $adapterPosition" )
            }
        }
    }
}