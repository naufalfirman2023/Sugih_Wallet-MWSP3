package com.example.sugih_wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONException
import org.json.JSONObject

class Login : AppCompatActivity() {
    private lateinit var buttonPindah: Button
    private lateinit var textPass: EditText
    private lateinit var textRegist: TextView
    private lateinit var textEmail: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        buttonPindah = findViewById(R.id.btn_login)
        textPass = findViewById(R.id.edt_pass)
        textEmail = findViewById(R.id.edt_email)


        buttonPindah.setOnClickListener {
            val jsonObject = JSONObject()
            try {
                jsonObject.put("email", textEmail.text.toString().trim())
                jsonObject.put("password", textPass.text.toString().trim())
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            AndroidNetworking.post("https://api.kuliah.esaage.com/login")
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .addHeaders("Content-Type", "application/json")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        // do anything with response
                        try {
                            Log.d("ini respon", response.toString())
                            if (response?.getInt("code")== 200) {
                                val id = response.getInt("user_id")
                                val intent = Intent(this@Login, Home::class.java)
                                intent.putExtra("id", id)
                                startActivity(intent)
                            }

                        } catch (eror: JSONException) {
                            Toast.makeText(this@Login, eror.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    override fun onError(error: ANError) {
                        // handle error
                    }
                })
        }




    }
}