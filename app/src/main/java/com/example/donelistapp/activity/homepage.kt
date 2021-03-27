package com.example.donelistapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.donelistapp.R
import com.example.donelistapp.helper.AppPrefences
import com.example.donelistapp.helper.Constant
import com.example.donelistapp.helper.DatabaseHelper
import kotlinx.android.synthetic.main.activity_homepage.*

class homepage : AppCompatActivity() {

    lateinit var databaseHelper: DatabaseHelper
    lateinit var prefHelper: AppPrefences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        fabAktivitas.setOnClickListener(){
            startActivity(Intent(this, aktivitas::class.java))
        }
    }

    override fun onBackPressed() {
        val exitIntent = Intent(Intent.ACTION_MAIN)
        exitIntent.addCategory(Intent.CATEGORY_HOME)
        exitIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(exitIntent)
    }

    override fun onStart() {
        databaseHelper = DatabaseHelper(this)
        prefHelper = AppPrefences(this)
        val emailText = prefHelper.getString(Constant.PREF_EMAIL).toString()
        val user = databaseHelper.getUser(emailText)
        txtNameHomepage.text = user!!.name
        super.onStart()
    }
}
