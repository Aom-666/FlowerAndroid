package com.rmutto.flower

import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val resultTextview = findViewById<TextView>(R.id.resultTextview)

        val sepal_length = findViewById<EditText>(R.id.sepal_length)
        val sepal_width = findViewById<EditText>(R.id.sepal_width)
        val petal_length = findViewById<EditText>(R.id.petal_length)
        val petal_width = findViewById<EditText>(R.id.petal_width)

        val btnPredict = findViewById<Button>(R.id.btnPredict)

        btnPredict.setOnClickListener {
            val url = getString(R.string.url_api)

            val okHttpClient = OkHttpClient()
            val fromBody : RequestBody = FormBody.Builder()
                .add("sepal_length", sepal_length.text.toString())
                .add("sepal_width", sepal_width.text.toString())
                .add("petal_length", petal_length.text.toString())
                .add("petal_width", petal_width.text.toString())
                .build()
            val request : Request = Request.Builder()
                .url(url)
                .post(fromBody)
                .build()
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val obj = JSONObject(response.body!!.string())
                val species = obj["species"].toString()
                resultTextview.text = species

            }else{
                Toast.makeText(this, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }
}