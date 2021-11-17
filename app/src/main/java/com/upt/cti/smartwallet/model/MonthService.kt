package com.upt.cti.smartwallet.model

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder

class MonthService {

    companion object {
        val db = FirebaseDatabase.getInstance("https://smart-wallet-6240c-default-rtdb.europe-west1.firebasedatabase.app/").reference
        fun init() {

            db.setValue("Monthly Expenses")
            val jan = MonthlyExpenses("January", 0f, 0f)
            db.child(jan.month).setValue(mapOf<String, Float>("income" to jan.income, "expenses" to jan.expenses))
            val feb = MonthlyExpenses("February", 0f, 0f)
            db.child(feb.month).setValue(mapOf<String, Float>("income" to feb.income, "expenses" to feb.expenses))
            val mar = MonthlyExpenses("March", 0f, 0f)
            db.child(mar.month).setValue(mapOf<String, Float>("income" to mar.income, "expenses" to mar.expenses))
        }
    }


}