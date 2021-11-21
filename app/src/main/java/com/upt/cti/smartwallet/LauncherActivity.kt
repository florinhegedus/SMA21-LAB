package com.upt.cti.smartwallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.upt.cti.smartwallet.model.FirebaseHelper

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        //FirebaseHelper.init()
        var listView = findViewById<ListView>(R.id.listview)

        val listItems = arrayOf("Item1", "Item2", "Item3")


        val db = FirebaseDatabase.getInstance("https://smart-wallet-6240c-default-rtdb.europe-west1.firebasedatabase.app/").reference
        db.child("smart wallet").addListenerForSingleValueEvent( object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var months = ArrayList<String>()
                for (postSnapshot in snapshot.children) {
                    val name: String = postSnapshot.getKey().toString()
                    months.add(name)
                }

                val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, months)
                listView.adapter = adapter
            }
        })
    }
}