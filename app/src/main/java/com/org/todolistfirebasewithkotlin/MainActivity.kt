package com.org.todolistfirebasewithkotlin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var todos: ArrayList<ToDoItem> = ArrayList()

    val adapter: ToDoAdapter = ToDoAdapter(todos, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set click listener for add item button
        button_add_todo.setOnClickListener(this)

        // initialise recycler view
        recyclerView_todo_list.layoutManager = LinearLayoutManager(this)

        recyclerView_todo_list.adapter = adapter;

    }

    override fun onClick(view: View) {

        when (view) {
            // add item button action
            button_add_todo -> {

                val level = editText_todo_item.text.toString()
                if (!level.equals("")) {

                    todos.add(ToDoItem(level))

                    adapter.notifyDataSetChanged()

                    editText_todo_item.setText("")

                    Toast.makeText(
                        this,
                        "Item Added successfully",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }
}
