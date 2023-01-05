package com.example.sugih_wallet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONException
import org.json.JSONObject


class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
//        EditText
        val txtnomor = findViewById<EditText>(R.id.edt_nomor)
        val txtnama = findViewById<EditText>(R.id.edt_fullname)
        val txtemail = findViewById<EditText>(R.id.edt_email)
        val txtalamat = findViewById<EditText>(R.id.edt_alamat)
        val txtpswd = findViewById<EditText>(R.id.edt_pass)
        val txtotp = findViewById<EditText>(R.id.edt_otp)


//        Button
        val btn_otp = findViewById<Button>(R.id.btn_otp)
        val btn_regist = findViewById<Button>(R.id.btn_regist)

        btn_otp.setOnClickListener{
            val jsonObject = JSONObject()
            try {
                jsonObject.put("telpon", txtnomor.text.toString().trim())
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            AndroidNetworking.post("http://api.kuliah.esaage.com/create-otp")
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .addHeaders("Content-Type", "application/json")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        // do anything with response
                        try {
                            Log.d("ini respon",response.toString())
                            if (response?.getInt("kode")==200){
                                val getMessage = response?.getString("msg")
                                Log.d("TAGgggggggggg", "telpon: $txtnomor")

                                Log.d("otp nyaaaaa", "msg")
                                Toast.makeText(this@Register,getMessage, Toast.LENGTH_SHORT).show()
                            }
                        }catch (eror : JSONException){

                        }
                    }
                    override fun onError(error: ANError) {
                        Log.e("ERROR", error.toString())
                        // handle error
                        Toast.makeText(this@Register,"eror siuuuuuu", Toast.LENGTH_SHORT).show()
                    }
                })
        }
        btn_regist.setOnClickListener {
            val jsonObject = JSONObject()
            try {
                jsonObject.put("nama", txtnama.text.toString().trim())
                jsonObject.put("email", txtemail.text.toString().trim())
                jsonObject.put("telpon", txtnomor.text.toString().trim())
                jsonObject.put("password", txtpswd.text.toString().trim())
                jsonObject.put("alamat", txtalamat.text.toString().trim())
                jsonObject.put("kode", txtotp.text.toString().trim())
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            AndroidNetworking.post("http://api.kuliah.esaage.com/register")
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .addHeaders("Content-Type", "application/json")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        // do anything with response
                        try {
                            Log.d("ini respon",response.toString())
                            if (response?.getInt("code")==200){
                                val getMessage = response?.getString("msg")
                                Log.d("TAGgggggggggg", "telpon: $txtnomor")
                                val intent = Intent(this@Register, Login::class.java)
                                startActivity(intent)

                                Log.d("otp nyaaaaa", "msg")
                                Toast.makeText(this@Register,getMessage, Toast.LENGTH_SHORT).show()
                            }

                        }catch (eror : JSONException){

                        }
                    }
                    override fun onError(error: ANError) {
                        Log.e("ERROR", error.toString())
                        // handle error
                        Toast.makeText(this@Register,"eror siuuuuuu", Toast.LENGTH_SHORT).show()
                    }
                })
        }

    }
}