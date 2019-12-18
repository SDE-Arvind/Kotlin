package com.org.todolistfirebasewithkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_list_item.view.*

/**
 * Created by arvind on 2019-12-18
 */
class ToDoAdapter( val items :List<ToDoItem> ,val context:Context) :RecyclerView.Adapter<ViewHolder>() {

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkboxItem?.text = items.get(position).level
        holder.checkboxItem.isChecked = items.get(position).status

        holder.checkboxItem.setOnCheckedChangeListener { _, _ ->
            items.get(position).statusChanged();
        }
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_list_item, parent, false))
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val checkboxItem = view.checkBox_item
}