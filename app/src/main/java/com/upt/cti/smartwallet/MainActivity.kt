package com.upt.cti.smartwallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.upt.cti.smartwallet.model.MonthlyExpenses
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = FirebaseDatabase.getInstance("https://smart-wallet-6240c-default-rtdb.europe-west1.firebasedatabase.app/").reference
        db.setValue("12345678")

        val src = findViewById<Button>(R.id.searchButton)
        src.setOnClickListener{
            Toast.makeText(this, "Searching", Toast.LENGTH_SHORT).show()

            val srcMonth = findViewById<EditText>(R.id.monthName)
            var searchMonth = srcMonth.text.toString()

            db.child(searchMonth).setValue(MonthlyExpenses(searchMonth, 1f, 2f))
        }

    }
}