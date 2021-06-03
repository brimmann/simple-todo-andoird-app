package com.penteches.todolist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(val constraintLayout: ConstraintLayout): RecyclerView.ViewHolder(constraintLayout)

class TodoItemAdaptor(private val todItemsList: ArrayList<TodoItem>, private val activity: MainActivity):
    RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val constraintLayout = LayoutInflater.from(parent.context).inflate(
            R.layout.todo_item_layout, parent, false) as ConstraintLayout

        constraintLayout.setOnClickListener(View.OnClickListener {
            val nameTextView = constraintLayout.getChildAt(0) as TextView
            val urgencyTextView = constraintLayout.getChildAt(1) as TextView
            val nameText = nameTextView.text
            val isItemUrgent = urgencyTextView.text == "Important"
            val intent = Intent(parent.context, AddItemActivity::class.java)
            intent.putExtra("ITEM_NAME", nameText)
            intent.putExtra("ITEM_URGENCY", isItemUrgent)
            activity.startActivity(intent)
        })

        constraintLayout.setOnLongClickListener(View.OnLongClickListener {
            val position = parent.indexOfChild(it)
            val dbo = DatabaseOperations(parent.context)
            val delName = activity.todoItemsList[position].name
            dbo.delete(dbo, delName)
            activity.todoItemsList.removeAt(position)
            notifyItemRemoved(position)
            true
        })

        return ViewHolder(constraintLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val constraintLayout = holder.constraintLayout
        val nameTextView = constraintLayout.getChildAt(0) as TextView
        val urgencyTextView = constraintLayout.getChildAt(1) as TextView
        nameTextView.text = todItemsList[position].name
        urgencyTextView.text = if(todItemsList[position].isImportant) "Important" else ""
    }

    override fun getItemCount(): Int {
        return todItemsList.size
    }
}