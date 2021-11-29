package com.upt.cti.smartwallet.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.ParseException
import android.util.Log
import android.widget.Toast
import com.upt.cti.smartwallet.model.Payment
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat

object OfflineService {
    private fun savePaymentToFile(context: Context, payment: Payment){
        val fileName = payment.time

        try{
            val fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            val os = ObjectOutputStream(fos)
            os.writeObject(payment)
            os.close()
            fos.close()
        }catch(e: IOException){
            Toast.makeText(context, "Cannot access local data", Toast.LENGTH_SHORT).show()
        }
    }

    public fun updateLocalBackup(context: Context, payment: Payment, toAdd: Boolean){
        val fileName = payment.time

        try{
            if(toAdd){
                savePaymentToFile(context, payment)
            } else{
                context.deleteFile(fileName)
            }
        } catch(e: IOException){
            Toast.makeText(context, "Cannot access local data", Toast.LENGTH_SHORT).show()
        }
    }

    public fun loadPaymentsFromFile(context: Context, month: Int): ArrayList<Payment>{
        try{
            var payments = ArrayList<Payment>()
            val path = context.filesDir
            for(file in path.listFiles()){
                if(isValid(file.name)) {
                    val fis = context.openFileInput(file.name)
                    val ins = ObjectInputStream(fis)
                    val payment: Payment = ins.readObject() as Payment
                    if(Integer.parseInt(file.name.substring(5, 7))==month)
                        payments.add(payment)
                    ins.close()
                    fis.close()
                }
            }
            return payments
        } catch(e: IOException){
            Toast.makeText(context, "Cannot access local data (loadAllPaymentsFromFile)", Toast.LENGTH_SHORT).show()
        } catch(e: ClassNotFoundException){
            e.printStackTrace()
        }
        return ArrayList<Payment>()
    }

    public fun isNetworkAvailable(context: Context): Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    public fun hasLocalStorage(context: Context): Boolean{
        Log.d("Local storage --", context.filesDir.listFiles().size.toString())
        return context.filesDir.listFiles().size>0
    }

    private fun isValid(dateStr: String): Boolean {
        if(dateStr.startsWith("2021"))
            return true
        return false
    }

}