package com.upt.cti.smartwallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

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
        db.child("calendar").child(month).get().addOnSuccessListener {
            if(it.exists()){
                incomeText.setText(it.child("income").value.toString())
                expensesText.setText(it.child("expenses").value.toString())
            } else
                Toast.makeText(this, "The item doesn't exist, being added to the database", Toast.LENGTH_SHORT).show()
        }

        val button = findViewById<Button>(R.id.saveButton)
        button.setOnClickListener{
            val income = incomeText.text.toString()
            val expenses = expensesText.text.toString()

            updateData(month, income, expenses)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun updateData(month: String, income: String, expenses: String) {
        val db = FirebaseDatabase.getInstance("https://smart-wallet-6240c-default-rtdb.europe-west1.firebasedatabase.app/").reference
        val map = mapOf<String, Float>(
            "income" to income.toFloat(),
            "expenses" to expenses.toFloat()
        )
        db.child("calendar").child(month).updateChildren(map).addOnSuccessListener {

        }.addOnFailureListener{
            Log.d("Failure", "Failure")
        }
    }
}