package com.upt.cti.smartwallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.upt.cti.smartwallet.service.Month
import com.upt.cti.smartwallet.model.Payment
import com.upt.cti.smartwallet.service.OfflineService
import com.upt.cti.smartwallet.ui.PaymentAdapter
import kotlin.collections.ArrayList

class LauncherActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        auth = Firebase.auth
        val currentUser = auth.currentUser
        var userView = findViewById<TextView>(R.id.userView)
        userView.setText("User: " + currentUser?.email.toString())

        var listView = findViewById<ListView>(R.id.listview)
        var payments = ArrayList<Payment>()

        var cMonth = Month.cMonth
        val monthTitle = findViewById<TextView>(R.id.monthTextView)
        monthTitle.text = Month.monthToString(cMonth)

        val signOutButton = findViewById<Button>(R.id.signOutButton)
        signOutButton.setOnClickListener {
            signOut()
        }

        val db = FirebaseDatabase.getInstance("https://smart-wallet-6240c-default-rtdb.europe-west1.firebasedatabase.app/").reference
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        val smartwallet = db.child("smart wallet").child(auth.uid.toString())
        smartwallet.keepSynced(true)

        //OfflineService.deleteAllFiles(applicationContext)
        db.child("smart wallet").child(auth.uid.toString()).addListenerForSingleValueEvent( object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                for (postSnapshot in snapshot.children) {
                    var time: String = postSnapshot.key.toString()
                    var name = postSnapshot.child("name").value.toString()
                    var type = postSnapshot.child("type").value.toString()
                    var cost = postSnapshot.child("cost").value.toString().toDouble()
                    if(Integer.parseInt(time.substring(5, 7))==cMonth)
                        payments.add(Payment(time, name, type, cost))

                }

                val adapter = PaymentAdapter(applicationContext, R.layout.item_payment, payments)
                listView.adapter = adapter

            }
        })

        if(!OfflineService.isNetworkAvailable(applicationContext)){
            if(OfflineService.hasLocalStorage(applicationContext)) {
                payments = OfflineService.loadPaymentsFromFile(applicationContext, cMonth)
                val adapter = PaymentAdapter(applicationContext, R.layout.item_payment, payments)
                listView.adapter = adapter
            }
        } else {
            OfflineService.deleteAllFiles(applicationContext)
            OfflineService.addAllFiles(applicationContext, payments)
        }

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

        val nextButton = findViewById<Button>(R.id.nextButton)
        nextButton.setOnClickListener{
            Month.incrementMonth()
            recreate()
        }

        val prevButton = findViewById<Button>(R.id.prevButton)
        prevButton.setOnClickListener{
            Month.decrementMonth()
            recreate()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser == null){
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signOut(){
        auth.signOut()
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

}