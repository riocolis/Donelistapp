package com.example.donelistapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.donelistapp.R
import com.example.donelistapp.helper.AppPrefences
import com.example.donelistapp.helper.Constant
import com.example.donelistapp.helper.DatabaseHelper
import kotlinx.android.synthetic.main.activity_aktivitas.*

class aktivitas : AppCompatActivity() {

    lateinit var databaseHelper: DatabaseHelper
    lateinit var prefHelper : AppPrefences
    var textEmail : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        databaseHelper = DatabaseHelper(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aktivitas)
        btn_tambah.setOnClickListener(){
            if(editTextActivity.text.isNotEmpty()){
                databaseHelper.addTodo(editTextActivity.text.toString(),textEmail)
                moveIntentToHomePage()
            }else{
                editTextActivity.error= "Tidak boleh Kosong"
            }
        }

    }

    override fun onStart() {
        super.onStart()
        val name = intent.getStringExtra("nameHome")
        txtActivityName.text = name + "?"
        prefHelper = AppPrefences(this)
        textEmail = prefHelper.getString(Constant.PREF_EMAIL).toString()
    }

    private fun moveIntentToHomePage(){
        startActivity(Intent(this, homepage::class.java))
        finish()
    }
}
