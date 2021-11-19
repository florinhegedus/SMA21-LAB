package com.upt.cti.smartwallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.*
import com.upt.cti.smartwallet.model.MonthService
import com.upt.cti.smartwallet.model.MonthlyExpenses
import org.w3c.dom.Text
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //MonthService.init()

        val src = findViewById<Button>(R.id.searchButton)
        src.setOnClickListener{
            Toast.makeText(this, "Searching", Toast.LENGTH_SHORT).show()

            val srcMonth = findViewById<EditText>(R.id.monthName)
            var searchMonth = srcMonth.text.toString()

            val intent = Intent(this, MonthActivity::class.java)
            intent.putExtra("month", searchMonth)
            startActivity(intent)
        }


        val spinner: Spinner = findViewById(R.id.spinner)
        val arraySpinner = getMonthsFromDB()
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setAdapter(arrayAdapter)

    }

    fun getMonthsFromDB(): ArrayList<String>{
        val db = FirebaseDatabase.getInstance("https://smart-wallet-6240c-default-rtdb.europe-west1.firebasedatabase.app/").reference
        val months: ArrayList<String> = ArrayList<String>()
        db.child("calendar").addValueEventListener( object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val name: String = postSnapshot.getKey().toString()
                    months.add(name)
                }
            }
        })
        return months

    }
}