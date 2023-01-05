package com.example.sugih_wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONException
import org.json.JSONObject

class Home : AppCompatActivity() {
    private lateinit var textSaldo: TextView
    private lateinit var textNama: TextView
    private lateinit var transferbtn: CardView
    private lateinit var storybtn: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        transferbtn = findViewById(R.id.kirim)
        textSaldo = findViewById(R.id.saldo)
        textNama = findViewById(R.id.txtName)
        storybtn = findViewById(R.id.riwayat)

        val id = intent.getIntExtra("id", 0)
        val idbaru = id.toString()

        transferbtn.setOnClickListener{
            val intent = Intent(this@Home, Transfer::class.java)
            intent.putExtra("id", idbaru.toInt())
            startActivity(intent)
        }

        storybtn.setOnClickListener{
            val intent = Intent(this@Home, History::class.java)
            intent.putExtra("id", idbaru.toInt())
            startActivity(intent)
        }

        AndroidNetworking.get("https://api.kuliah.esaage.com/home/$id")
            .setTag("test")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    // do anything with response
                    try {
                        val saldo = response.getInt("saldo")
                        val nama = response.getString("nama")
                        textSaldo.text = "Rp. "+ saldo.toString()
                        textNama.text = "Hai, "+nama
                        Log.d("TAG", "saldo: $saldo, nama: $nama")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onError(error: ANError) {
                    // handle error
                }
            })




    }
}