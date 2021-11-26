package com.upt.cti.smartwallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import com.upt.cti.smartwallet.model.PaymentType
import java.text.SimpleDateFormat
import java.util.*

class AddPaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_payment)

        val spinner: Spinner = findViewById(R.id.spinner)
        val values = arrayOf("entertainment", "food", "taxes", "travel")
        val arrayAdapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, values)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        var selection = ""

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Toast.makeText(applicationContext, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
                selection = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener{
            var descriptionText = findViewById<EditText>(R.id.descriptionView)
            var costText = findViewById<EditText>(R.id.costView)
            var name = ""
            var cost = 0.0
            name = descriptionText.text.toString()
            if(costText.text.isNotEmpty())
                cost = costText.text.toString().toDouble()
            addPayment(name, selection, cost)

            val intent = Intent(this, LauncherActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addPayment(name: String, type: String, cost: Double) {
        val db = FirebaseDatabase.getInstance("https://smart-wallet-6240c-default-rtdb.europe-west1.firebasedatabase.app/").reference
        val payment1 = PaymentType(getTime(), name, type, cost)
        Toast.makeText(applicationContext, getTime(), Toast.LENGTH_SHORT).show()

        db.child("smart wallet").child(payment1.time).setValue(mapOf("name" to payment1.name, "cost" to payment1.cost, "type" to payment1.type))
    }

    private fun getTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        return sdf.format(Date())
    }
}