package com.upt.cti.smartwallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import com.upt.cti.smartwallet.model.Payment
import com.upt.cti.smartwallet.service.OfflineService
import java.text.SimpleDateFormat
import java.util.*

class AddPaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_payment)
        var date = intent.getStringExtra("date").toString()

        var descriptionText = findViewById<EditText>(R.id.descriptionView)
        var costText = findViewById<EditText>(R.id.costView)

        val spinner: Spinner = findViewById(R.id.spinner)
        val values = arrayOf("entertainment", "food", "taxes", "travel")
        val arrayAdapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, values)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        var selection = ""

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selection = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener{
            var name = ""
            var cost = 0.0
            name = descriptionText.text.toString()
            if(costText.text.isNotEmpty())
                cost = costText.text.toString().toDouble()
            if(date=="new item")
                addPayment(name, selection, cost)
            else
                updatePayment(date, name, selection, cost)

            val intent = Intent(this, LauncherActivity::class.java)
            startActivity(intent)
        }

        val deleteButton = findViewById<Button>(R.id.deleteButton)
        deleteButton.setOnClickListener{
            if(date != "new item")
                deletePayment(date)
            val intent = Intent(this, LauncherActivity::class.java)
            startActivity(intent)
        }

        val db = FirebaseDatabase.getInstance("https://smart-wallet-6240c-default-rtdb.europe-west1.firebasedatabase.app/").reference
        db.child("smart wallet").child(date).get().addOnSuccessListener {
            if(it.exists()){
                descriptionText.setText(it.child("name").value.toString())
                costText.setText(it.child("cost").value.toString())
                spinner.setSelection(arrayAdapter.getPosition(it.child("type").value.toString()))
                Toast.makeText(applicationContext, "Retrieved from firebase", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(this, "Cant retrieve from firebase", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePayment(date: String, name: String, type: String, cost: Double) {
        val db = FirebaseDatabase.getInstance("https://smart-wallet-6240c-default-rtdb.europe-west1.firebasedatabase.app/").reference
        val payment1 = Payment(date, name, type, cost)
        db.child("smart wallet").child(payment1.time).setValue(mapOf("name" to payment1.name, "cost" to payment1.cost, "type" to payment1.type))
        OfflineService.updateLocalBackup(applicationContext, payment1, false)
        OfflineService.updateLocalBackup(applicationContext, payment1, true)
    }

    private fun addPayment(name: String, type: String, cost: Double) {
        val db = FirebaseDatabase.getInstance("https://smart-wallet-6240c-default-rtdb.europe-west1.firebasedatabase.app/").reference
        val payment1 = Payment(getTime(), name, type, cost)
        db.child("smart wallet").child(payment1.time).setValue(mapOf("name" to payment1.name, "cost" to payment1.cost, "type" to payment1.type))
        OfflineService.updateLocalBackup(applicationContext, payment1, true)
    }

    private fun deletePayment(date: String){
        val db = FirebaseDatabase.getInstance("https://smart-wallet-6240c-default-rtdb.europe-west1.firebasedatabase.app/").reference
        db.child("smart wallet").child(date).removeValue()
        OfflineService.updateLocalBackup(applicationContext, Payment(date, "", "", 0.0), true)
    }

    private fun getTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        return sdf.format(Date())
    }
}