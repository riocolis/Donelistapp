package com.example.donelistapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.donelistapp.R
import com.example.donelistapp.helper.AppPrefences
import com.example.donelistapp.helper.Constant
import com.example.donelistapp.helper.DatabaseHelper
import com.example.donelistapp.model.User
import kotlinx.android.synthetic.main.activity_register.*

class register : AppCompatActivity() {

    lateinit var prefHelper: AppPrefences
    lateinit var databaseHelper: DatabaseHelper
    lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        prefHelper = AppPrefences(this)
        databaseHelper = DatabaseHelper(this)

        btn_Daftar.setOnClickListener(){
            if (txtNamaDaftar.text.isNotEmpty() && txtEmailDaftar.text.isNotEmpty() && txtPasswordDaftar.text.isNotEmpty()){
                user = User(name = txtNamaDaftar.text.toString(),email = txtEmailDaftar.text.toString(),password = txtPasswordDaftar.text.toString())
                databaseHelper.addUser(user)
                saveSession(txtNamaDaftar.text.toString(),txtEmailDaftar.text.toString(), txtPasswordDaftar.text.toString())
                moveIntentToHomePage()
            }else if (txtNamaDaftar.text.isEmpty()){
                txtNamaDaftar.error = "Nama Kosong"
            }else if (txtEmailDaftar.text.isEmpty()){
                txtEmailDaftar.error = "Email Kosong"
            }else if (txtPasswordDaftar.text.isEmpty()){
                txtPasswordDaftar.error = "Password Kosong"
            }
        }
    }
    private fun moveIntentToHomePage(){
        startActivity(Intent(this, homepage::class.java))
        finish()
    }

    private fun saveSession(username: String, email: String, password: String){
        prefHelper.put( Constant.PREF_USERNAME, username )
        prefHelper.put( Constant.PREF_EMAIL, email )
        prefHelper.put( Constant.PREF_PASSWORD, password )
        prefHelper.put( Constant.PREF_IS_LOGIN, true)
    }
}
