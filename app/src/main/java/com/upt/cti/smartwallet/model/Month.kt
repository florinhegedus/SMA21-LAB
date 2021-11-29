package com.upt.cti.smartwallet.model

import java.text.SimpleDateFormat
import java.util.*

object Month {
    var cMonth: Int = getCurrentMonth()

    public fun incrementMonth(){
        cMonth += 1
    }

    public fun decrementMonth(){
        cMonth -= 1
    }

    private fun getCurrentMonth(): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        return Integer.parseInt(sdf.format(Date()).substring(5, 7))
    }
}