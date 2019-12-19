package com.org.todolistfirebasewithkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.todo_list_item.view.*

/**
 * Created by arvind on 2019-12-18
 */
class ToDoAdapter( val items :List<ToDoItem?> ,val context:Context, val ref:DatabaseReference) :RecyclerView.Adapter<ViewHolder>() {

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


            val item: ToDoItem ?= items.get(position);
        if(item!=null) {
            holder.checkboxItem?.text = item.level
            holder.checkboxItem.isChecked = item.status

            holder.checkboxItem.setOnCheckedChangeListener { _, _ ->

                item.statusChanged();
                //update status on firebase
                ref.child(item.id).setValue(item);
            }
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