package com.penteches.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import java.util.function.ToDoubleBiFunction

class AddItemActivity : AppCompatActivity() {
    private lateinit var itemNameEditText: EditText
    private lateinit var isUrgentCheckBox: CheckBox
    private lateinit var titleTextView: TextView
    private var isNewItem = true
    private lateinit var oldItem: TodoItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        itemNameEditText = findViewById(R.id.item_name_edit_text)
        isUrgentCheckBox = findViewById(R.id.is_urgent_checkbox)
        titleTextView = findViewById(R.id.add_item_title_text_view)


        val itemName = intent.getStringExtra("ITEM_NAME")
        val itemUrgency = intent.getBooleanExtra("ITEM_URGENCY", false)
        if(itemName != null){
            itemNameEditText.setText(itemName)
            titleTextView.setText(R.string.edit_todo_item)

            oldItem = TodoItem(itemName)
            isNewItem = false
        }
        if(itemUrgency){
            isUrgentCheckBox.isChecked = true
            oldItem.isImportant = itemUrgency
        }
    }

    fun saveItemAction(view: View){
        val itemName = itemNameEditText.text.toString()
        val isItemUrgent = isUrgentCheckBox.isChecked
        val newTodoItem = TodoItem(itemName, isItemUrgent)

        val dbo = DatabaseOperations(this)
        if(isNewItem)
            dbo.add(dbo, newTodoItem)
        else
            dbo.updateItem(dbo, oldItem, newTodoItem)
    }

    fun addItemPageCancelButtonAction(view: View){
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}