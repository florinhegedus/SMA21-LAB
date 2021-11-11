package com.upt.cti.smartwallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import com.upt.cti.smartwallet.model.MonthService
import com.upt.cti.smartwallet.model.MonthlyExpenses
import org.w3c.dom.Text
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MonthService.init()

        val src = findViewById<Button>(R.id.searchButton)
        src.setOnClickListener{
            Toast.makeText(this, "Searching", Toast.LENGTH_SHORT).show()

            val srcMonth = findViewById<EditText>(R.id.monthName)
            var searchMonth = srcMonth.text.toString()

            val intent = Intent(this, MonthActivity::class.java)
            intent.putExtra("month", searchMonth)
            startActivity(intent)
        }

    }
}