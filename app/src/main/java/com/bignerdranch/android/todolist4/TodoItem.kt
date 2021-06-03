package com.penteches.todolist

import java.util.*

class TodoItem(var name: String) {
    var isImportant: Boolean = false
    var date = Calendar.getInstance()

    constructor(name: String, isImportant: Boolean): this(name){
        this.isImportant = isImportant
    }

    fun getDateAsString(): String{
        return "${date.get(Calendar.YEAR).toString()}:${date.get(Calendar.MONTH).toString()}:${date.get(Calendar.DAY_OF_MONTH).toString()}"
    }
}