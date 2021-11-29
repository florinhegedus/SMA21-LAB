package com.upt.cti.smartwallet.service

import android.content.Context
import android.widget.Toast
import com.upt.cti.smartwallet.model.Payment
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class OfflineService {
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

    public fun loadAllPaymentsFromFile(context: Context): List<Payment>{
        try{
            var payments = ArrayList<Payment>()
            val path = context.filesDir
            for(file in path.listFiles()){
                val fis = context.openFileInput(file.name)
                val ins = ObjectInputStream(fis)
                val payment: Payment = ins.readObject() as Payment
                payments.add(payment)
                ins.close()
                fis.close()
            }
            return payments
        } catch(e: IOException){
            Toast.makeText(context, "Cannot access local data", Toast.LENGTH_SHORT).show()
        } catch(e: ClassNotFoundException){
            e.printStackTrace()
        }
        return ArrayList<Payment>()
    }

}