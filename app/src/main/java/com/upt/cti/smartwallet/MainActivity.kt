package com.upt.cti.smartwallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //MonthService.init()

        val addButton = findViewById<Button>(R.id.addMonthButton)
        addButton.setOnClickListener{
            Toast.makeText(this, "Searching", Toast.LENGTH_SHORT).show()

            val srcMonth = findViewById<EditText>(R.id.monthName)
            var searchMonth = srcMonth.text.toString()

            val intent = Intent(this, MonthActivity::class.java)
            intent.putExtra("month", searchMonth)
            startActivity(intent)
        }

        val spinner: Spinner = findViewById(R.id.spinner)
        var month = ""
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                Toast.makeText(applicationContext, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
                month = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        val srcButton = findViewById<Button>(R.id.searchButton)
        srcButton.setOnClickListener {
            val intent = Intent(this, MonthActivity::class.java)
            intent.putExtra("month", month)
            startActivity(intent)
        }

        val db = FirebaseDatabase.getInstance("https://smart-wallet-6240c-default-rtdb.europe-west1.firebasedatabase.app/").reference
        db.child("calendar").addListenerForSingleValueEvent( object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var months = ArrayList<String>()
                for (postSnapshot in snapshot.children) {
                    val name: String = postSnapshot.getKey().toString()
                    months.add(name)
                }

                val arrayAdapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, months)
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = arrayAdapter
            }
        })


    }

}