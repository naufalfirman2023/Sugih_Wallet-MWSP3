package com.example.sugih_wallet

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONException
import org.json.JSONObject

class Transfer : AppCompatActivity() {
    private lateinit var ednomor: EditText
    private lateinit var edsaldo: EditText
    private lateinit var btnnya: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        ednomor = findViewById(R.id.edt_to)
        edsaldo = findViewById(R.id.saldo)
        btnnya = findViewById(R.id.btn_tf)
        val id = intent.getIntExtra("id", 0)



        btnnya.setOnClickListener {
            val jsonObject = JSONObject()
            try {
                jsonObject.put("to", ednomor.text.toString())
                jsonObject.put("ballance", edsaldo.text.toString())
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            AndroidNetworking.post("https://api.kuliah.esaage.com/transfer/$id")
                .addJSONObjectBody(jsonObject)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        // do anything with response
                        try {
                            Log.d("ini respon", response.toString())
                            if (response?.getInt("code") == 200) {
                                Log.d("TAG", "to: $ednomor, ballance: $edsaldo")
                                customDialog()

                            }

                        } catch (eror: JSONException) {
                            Toast.makeText(this@Transfer, eror.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onError(error: ANError) {
                        // handle error
                    }
                })

        }
    }
    private fun customDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView((R.layout.custom_dialog))

        val btn_tutup = dialog.findViewById<Button>(R.id.btn_tutup)
        btn_tutup.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }
}
