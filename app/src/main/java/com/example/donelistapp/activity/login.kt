package com.example.donelistapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.donelistapp.R
import com.example.donelistapp.helper.AppPrefences
import com.example.donelistapp.helper.Constant
import com.example.donelistapp.helper.DatabaseHelper
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {

    lateinit var prefHelper: AppPrefences
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefHelper = AppPrefences(this)
        databaseHelper = DatabaseHelper(this)

        btn_Masuk.setOnClickListener(){
            if(txtEmailMasuk.text.isNotEmpty() && txtPasswordMasuk.text.isNotEmpty()){
                if(databaseHelper.checkUser(txtEmailMasuk.text.toString())){
                    saveSession(txtEmailMasuk.text.toString(),txtPasswordMasuk.text.toString())
                    txtEmailMasuk.text=null
                    txtPasswordMasuk.text=null
                    moveIntentToHomePage()
                }
            }else if (txtEmailMasuk.text.isEmpty()){
                txtEmailMasuk.error = "Email Kosong"
            }else if (txtPasswordMasuk.text.isEmpty()){
                txtPasswordMasuk.error = "Password Kosong"
            }
        }
    }

    private fun moveIntentToHomePage(){
        startActivity(Intent(this, homepage::class.java))
        finish()
    }

    private fun saveSession(email: String, password: String){
        prefHelper.put( Constant.PREF_EMAIL, email)
        prefHelper.put( Constant.PREF_PASSWORD, password )
        prefHelper.put( Constant.PREF_IS_LOGIN, true)
    }

}
