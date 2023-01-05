package com.example.sugih_wallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONException
import org.json.JSONObject

class History : AppCompatActivity() {
    //variable untuk recycler view
    private lateinit var recyclerView: RecyclerView
//    private var id = intent.getIntExtra("id", 0)
    // variable untuk data userlist
    private lateinit var datahistorylist: ArrayList<ResponHistory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        //inisialisasi recycler view
        recyclerView = findViewById(R.id.rvPOST)
        //inisialisasi data user
        datahistorylist = ArrayList<ResponHistory>()
        //panggil function getdatahistory()
        getdatahistory()


    }

    private fun getdatahistory() {
        var id = intent.getIntExtra("id", 0)
        AndroidNetworking.get("https://api.kuliah.esaage.com/history/$id")
            .setTag("test")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    // do anything with response
                    try {
                        Log.d("ini respon", response.toString())
                        if (response?.getInt("code") == 200) {
                            val getJSONArray = response.getJSONArray("data")
                            for (i in 0 until getJSONArray.length()) {
                                val getItem = getJSONArray.getJSONObject(i)
                                val type = getItem.optString("type")
                                val to = getItem.optString("to")
                                val ballance = getItem.optString("ballance")
                                val time = getItem.optString("time")
                                val from = getItem.optString("from")

                                if (type != null && to != null && ballance != null && time != null && from != null) {
                                    datahistorylist.add(
                                        ResponHistory(
                                            type,
                                            to,
                                            ballance,
                                            time,
                                            from
                                        )
                                    )
                                }
                            }
                            //panggil function populateddata() disini
                            populateData()

                        }

                    } catch (eror: JSONException) {
                        Toast.makeText(this@History, eror.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onError(error: ANError) {
                    // handle error
                }
            })
    }

    private fun populateData() {
        val linearManager = LinearLayoutManager(this)
        linearManager.reverseLayout = true
        linearManager.stackFromEnd = true
        recyclerView.layoutManager = linearManager
        val adp = AdapterHistory(this, datahistorylist)
        recyclerView.adapter = adp
    }
}