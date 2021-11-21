package com.upt.cti.smartwallet.model

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder

class FirebaseHelper {

    companion object {
        val db = FirebaseDatabase.getInstance("https://smart-wallet-6240c-default-rtdb.europe-west1.firebasedatabase.app/").reference
        fun init() {

            db.setValue("Monthly Expenses")
            val jan = MonthlyExpenses("January", 0f, 0f)
            db.child("calendar").child(jan.month).setValue(mapOf<String, Float>("income" to jan.income, "expenses" to jan.expenses))
            val feb = MonthlyExpenses("February", 0f, 0f)
            db.child("calendar").child(feb.month).setValue(mapOf<String, Float>("income" to feb.income, "expenses" to feb.expenses))
            val mar = MonthlyExpenses("March", 0f, 0f)
            db.child("calendar").child(mar.month).setValue(mapOf<String, Float>("income" to mar.income, "expenses" to mar.expenses))

            val payment1 = PaymentType("2021-09-28 16:33:08", "Pizza Calzone", "food", 15.5)
            val payment2 = PaymentType("2021-10-10 08:23:09", "Paris", "travel", 20.5)
            val payment3 = PaymentType("2021-12-10 00:13:10", "AC/DC", "entertainment", 30.5)
            db.child("smart wallet").child(payment1.time).setValue(mapOf("name" to payment1.name, "cost" to payment1.cost, "type" to payment1.type))
            db.child("smart wallet").child(payment2.time).setValue(mapOf("name" to payment2.name, "cost" to payment2.cost, "type" to payment2.type))
            db.child("smart wallet").child(payment3.time).setValue(mapOf("name" to payment3.name, "cost" to payment3.cost, "type" to payment3.type))


        }
    }


}