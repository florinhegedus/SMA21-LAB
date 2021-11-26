package com.upt.cti.smartwallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.upt.cti.smartwallet.model.FirebaseHelper
import com.upt.cti.smartwallet.model.PaymentType
import com.upt.cti.smartwallet.ui.PaymentAdapter

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        //FirebaseHelper.init()
        var listView = findViewById<ListView>(R.id.listview)
        var payments = ArrayList<PaymentType>()

        val db = FirebaseDatabase.getInstance("https://smart-wallet-6240c-default-rtdb.europe-west1.firebasedatabase.app/").reference
        db.child("smart wallet").addListenerForSingleValueEvent( object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                for (postSnapshot in snapshot.children) {
                    var time: String = postSnapshot.getKey().toString()
                    var name = postSnapshot.child("name").value.toString()
                    var type = postSnapshot.child("type").value.toString()
                    var cost = postSnapshot.child("cost").value.toString().toDouble()
                    payments.add(PaymentType(time, name, type, cost))
                }

                val adapter = PaymentAdapter(applicationContext, R.layout.item_payment, payments)
                listView.adapter = adapter

            }
        })

        val addButton = findViewById<FloatingActionButton>(R.id.addButton)
        addButton.setOnClickListener{
            val intent = Intent(this, AddPaymentActivity::class.java)
            intent.putExtra("date", "new item")
            startActivity(intent)
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val element = parent.getItemAtPosition(position) // The item that was clicked
            val intent = Intent(this, AddPaymentActivity::class.java)
            val date = view.findViewById<TextView>(R.id.dateText).text.toString()
            intent.putExtra("date", date)
            startActivity(intent)
        }


    }
}