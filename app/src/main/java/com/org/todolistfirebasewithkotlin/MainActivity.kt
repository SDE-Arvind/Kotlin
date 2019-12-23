package com.org.todolistfirebasewithkotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.beust.klaxon.Klaxon
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var todos: ArrayList<ToDoItem?> = ArrayList()

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("todo-list")

    val adapter: ToDoAdapter = ToDoAdapter(todos, this, myRef)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set click listener for add item button
        button_add_todo.setOnClickListener(this)

        // initialise recycler view
        recyclerView_todo_list.layoutManager = LinearLayoutManager(this)

        recyclerView_todo_list.adapter = adapter;

        // Read from the database only once for initialization
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value

                if (dataSnapshot.exists()) {
                    val todoHash = dataSnapshot.value as HashMap<*, *>

                    for ((k, v) in todoHash) {
                        // converted datasnapshot ot json format
                        val jaon=   JSONObject(v.toString())

                        //parse json to modal class
                        val result = Klaxon()
                            .parse<ToDoItem>(jaon.toString())
                        todos.add(result)
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    override fun onClick(view: View) {

        when (view) {
            // add item button action
            button_add_todo -> {

                val level = editText_todo_item.text.toString()
                if (!level.equals("")) {

                    val id: String = myRef.push().key.toString()

                    val todoItem = ToDoItem(id, level)

                    editText_todo_item.setText("")

                    //save data on firebase
                    val a = myRef.child(id).setValue(todoItem)

                    todos.add(todoItem)
                    adapter.notifyDataSetChanged()

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
