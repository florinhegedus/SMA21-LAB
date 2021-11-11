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

            db.child("January").setValue(MonthlyExpenses("January", 1000f, 2000f))
            db.child("February").setValue(MonthlyExpenses("February", 0f, 0f))
            db.child("March").setValue(MonthlyExpenses("March", 0f, 0f))
            db.child("April").setValue(MonthlyExpenses("April", 0f, 0f))
            db.child("May").setValue(MonthlyExpenses("May", 0f, 0f))
            db.child("June").setValue(MonthlyExpenses("June", 0f, 0f))
            db.child("July").setValue(MonthlyExpenses("July", 0f, 0f))
            db.child("August").setValue(MonthlyExpenses("August", 0f, 0f))
            db.child("September").setValue(MonthlyExpenses("September", 0f, 0f))
            db.child("October").setValue(MonthlyExpenses("October", 0f, 0f))
            db.child("November").setValue(MonthlyExpenses("November", 0f, 0f))
            db.child("December").setValue(MonthlyExpenses("December", 0f, 0f))

            db.addValueEventListener(getData)
            db.addListenerForSingleValueEvent(getData)
        }
        var getData = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()
                for(i in snapshot.children){
                    var income = i.child("income").getValue()
                    var expense = i.child("expense").getValue()
                    sb.append("${i.key} $income $expense")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }

        fun getIncome(monthName: String): String{
            var retIncome = "wow"
            db.child(monthName).get().addOnSuccessListener {
                if(it.exists()){
                    val income = it.child("income").value
                    retIncome = income.toString()
                } else
                    retIncome = "no_income"
            }
            return retIncome
        }
    }


}