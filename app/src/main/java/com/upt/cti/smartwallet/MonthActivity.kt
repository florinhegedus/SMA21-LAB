package com.upt.cti.smartwallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.upt.cti.smartwallet.model.MonthService
import org.w3c.dom.Text
import java.lang.StringBuilder
import java.time.Month

class MonthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month)

        var month = intent.getStringExtra("month").toString()

        var incomeText = findViewById<EditText>(R.id.incomeText)
        var expensesText = findViewById<EditText>(R.id.expensesText)
        var monthText = findViewById<TextView>(R.id.monthText)

        monthText.text = month

        val db = FirebaseDatabase.getInstance("https://smart-wallet-6240c-default-rtdb.europe-west1.firebasedatabase.app/").reference
        db.child(month).get().addOnSuccessListener {
            if(it.exists()){
                incomeText.setText(it.child("income").value.toString())
            } else
                Toast.makeText(this, "Retrieving failed", Toast.LENGTH_SHORT).show()
        }

        db.child(month).get().addOnSuccessListener {
            if(it.exists()){
                expensesText.setText(it.child("expenses").value.toString())
            } else
                Toast.makeText(this, "Retrieving failed", Toast.LENGTH_SHORT).show()
        }

        val button = findViewById<Button>(R.id.saveButton)
        button.setOnClickListener{

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}