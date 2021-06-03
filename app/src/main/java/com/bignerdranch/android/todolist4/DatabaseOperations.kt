package com.penteches.todolist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import java.io.DataInput

class DatabaseOperations(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        const val DATABASE_NAME = "TodoItems.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(p0: SQLiteDatabase) {
        p0.execSQL(DatabaseInfo.SQL_CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.execSQL(DatabaseInfo.SQL_DELETE_TABLE_QUERY)
        onCreate(p0)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun add(dbo: DatabaseOperations, todoItem: TodoItem){
        val db = dbo.writableDatabase
        val itemName = todoItem.name
        val itemUrgency = if(todoItem.isImportant) 1 else 0
        val itemDate = todoItem.getDateAsString()

        val contentValues = ContentValues().apply {
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME, itemName)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY, itemUrgency)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_DATE, itemDate)
        }

        db.insert(DatabaseInfo.TableInfo.TABLE_NAME, null, contentValues)
    }

    fun getAllItems(dbo: DatabaseOperations): Cursor{
        val db = dbo.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseInfo.TableInfo.COLUMN_ITEM_NAME,
            DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY,
            DatabaseInfo.TableInfo.COLUMN_ITEM_DATE)
        val selection = ""
        val selectionArgs = null
        val sortOrder = null
        return db.query(
            DatabaseInfo.TableInfo.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
    }

    fun updateItem(dbo: DatabaseOperations, oldItem: TodoItem, newItem: TodoItem){
        val db = dbo.writableDatabase
        val contentValues = ContentValues().apply {
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME, newItem.name)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY, if(newItem.isImportant) 1 else 0)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_DATE, newItem.getDateAsString())
        }

        val count = db.update(DatabaseInfo.TableInfo.TABLE_NAME, contentValues, "${DatabaseInfo.TableInfo.COLUMN_ITEM_NAME} LIKE ?", arrayOf(oldItem.name))
    }

    fun delete(dbo: DatabaseOperations, todoItemName: String){
        val db = dbo.writableDatabase
        val deletedRow = db.delete(DatabaseInfo.TableInfo.TABLE_NAME, "${DatabaseInfo.TableInfo.COLUMN_ITEM_NAME} LIKE ?", arrayOf(todoItemName))
    }
}