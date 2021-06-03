package com.penteches.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var toDoItemRecyclerView: RecyclerView
    private lateinit var recyclerAdaptor: TodoItemAdaptor
    private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager
    var todoItemsList = ArrayList<TodoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbo = DatabaseOperations(this)
        val cursor = dbo.getAllItems(dbo)

        with(cursor){
            while(moveToNext()){
                val itemName = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME))
                val itemUrgency = getInt(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY)) == 1
                todoItemsList.add(TodoItem(itemName, itemUrgency))
            }
        }


//        todoItemsList.add(TodoItem("Buy groceries"))
//        todoItemsList.add(TodoItem("Do laundry", true))
//        todoItemsList.add(TodoItem("Play guitar"))

        toDoItemRecyclerView = findViewById(R.id.todo_item_recycler_view)
        recyclerLayoutManager = LinearLayoutManager(this)
        recyclerAdaptor = TodoItemAdaptor(todoItemsList, this)

        toDoItemRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdaptor

        }
    }

    fun navToAddItemPageAction(view: View){
        val intent: Intent = Intent(this, AddItemActivity::class.java)
        startActivity(intent)
    }
}