package com.example.donelistapp.helper

import com.example.donelistapp.model.User
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.donelistapp.model.Todo


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")

    private val CREATE_TODO_TABLE = ("CREATE TABLE " + TABLE_TODO + "("
            + COLUMN_TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TODO_DESCRIPTION + " TEXT,"
            + COLUMN_TODO_EMAIL + " TEXT" + ")")

    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"
    private val DROP_TODO_TABLE = "DROP TABLE IF EXISTS $TABLE_TODO"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
        db.execSQL(CREATE_TODO_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_USER_TABLE)
        db.execSQL(DROP_TODO_TABLE)
        onCreate(db)
    }

    fun addUser(user: User) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)

        // Inserting Row
        db.insert(TABLE_USER, null, values)
        db.close()
    }
    fun checkUser(email: String): Boolean {

        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
        val selection = "$COLUMN_USER_EMAIL = ?"
        val selectionArgs = arrayOf(email)
        val cursor = db.query(TABLE_USER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null)
        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false
    }
    fun getUser(email: String): User? {
//        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
//        val selection = "$COLUMN_USER_EMAIL = ?"
//        val selectionArgs = arrayOf(email)
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USER WHERE $COLUMN_USER_EMAIL = ? ", arrayOf(email))
//        val cursor = db.query(TABLE_USER,
//            columns,
//            selection,
//            selectionArgs,
//            null,
//            null,
//            null)
        if(cursor.moveToFirst()){
            val result = User()
            result.name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME))
            result.email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL))
            result.password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))
            return  result
        }
        cursor.close()
        db.close()
        return null
    }

    fun allTodos() : ArrayList<Todo> {
        val columns = arrayOf(COLUMN_TODO_DESCRIPTION, COLUMN_TODO_EMAIL)
        val sortOrder = "$COLUMN_TODO_ID DESC"
        val todoList = ArrayList<Todo>()
        val db = this.readableDatabase

        val cursor = db.query(
            TABLE_TODO,
            columns,
            null,
            null,
            null,
            null,
            sortOrder)
        if(cursor.moveToFirst()){
            do {
                val todo = Todo()
                todo.description = cursor.getString(cursor.getColumnIndex(COLUMN_TODO_DESCRIPTION))
                todo.email = cursor.getString(cursor.getColumnIndex(COLUMN_TODO_EMAIL))
                todoList.add(todo)
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return  todoList
    }

    fun addTodo(description: String, email: String) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_TODO_DESCRIPTION, description)
        values.put(COLUMN_TODO_EMAIL,email)

        // Inserting Row
        db.insert(TABLE_TODO, null, values)
        db.close()
    }


    companion object {

        private val DATABASE_VERSION = 1

        private val DATABASE_NAME = "UserManager.db"

        private val TABLE_USER = "user"

        private val COLUMN_USER_ID = "user_id"
        private val COLUMN_USER_NAME = "user_name"
        private val COLUMN_USER_EMAIL = "user_email"
        private val COLUMN_USER_PASSWORD = "user_password"

        private val TABLE_TODO = "todo"

        private val COLUMN_TODO_ID = "todo_id"
        private val COLUMN_TODO_DESCRIPTION = "todo_description"
        private val COLUMN_TODO_EMAIL = "todo_email"
    }
}