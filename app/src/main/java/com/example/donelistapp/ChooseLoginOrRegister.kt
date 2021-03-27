package com.example.donelistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.donelistapp.activity.homepage
import com.example.donelistapp.activity.login
import com.example.donelistapp.activity.register
import com.example.donelistapp.helper.AppPrefences
import com.example.donelistapp.helper.Constant
import kotlinx.android.synthetic.main.activity_main.*

class ChooseLoginOrRegister : AppCompatActivity() {

    lateinit var prefHelper: AppPrefences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefHelper = AppPrefences(this)
        btn_DaftarChoose.setOnClickListener(){
            intent = Intent(this, register::class.java)
            startActivity(intent)
        }
        btn_MasukChoose.setOnClickListener(){
            intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if(prefHelper.getBoolean(Constant.PREF_IS_LOGIN)){
            moveIntentToHomePage()
        }
    }
    private fun moveIntentToHomePage(){
        startActivity(Intent(this, homepage::class.java))
        finish()
    }
}
