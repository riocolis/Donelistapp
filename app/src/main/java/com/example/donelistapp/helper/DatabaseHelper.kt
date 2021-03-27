package com.example.donelistapp.helper

import com.example.donelistapp.model.User
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")

    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_USER_TABLE)
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
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
        val selection = "$COLUMN_USER_EMAIL = ?"
        val selectionArgs = arrayOf(email)
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


    companion object {

        private val DATABASE_VERSION = 1

        private val DATABASE_NAME = "UserManager.db"

        private val TABLE_USER = "user"

        private val COLUMN_USER_ID = "user_id"
        private val COLUMN_USER_NAME = "user_name"
        private val COLUMN_USER_EMAIL = "user_email"
        private val COLUMN_USER_PASSWORD = "user_password"
    }
}