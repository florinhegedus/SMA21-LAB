package com.upt.cti.smartwallet.model

import java.text.SimpleDateFormat
import java.util.*

object Month {
    var cMonth: Int = getCurrentMonth()

    public fun incrementMonth(){
        cMonth += 1
        if(cMonth > 12)
            cMonth -= 12
    }

    public fun decrementMonth(){
        cMonth -= 1
        if(cMonth < 1)
            cMonth += 12
    }

    private fun getCurrentMonth(): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        return Integer.parseInt(sdf.format(Date()).substring(5, 7))
    }

    public fun monthToString(month: Int): String {
        if(month == 1) return "January"
        if(month == 2) return "February"
        if(month == 3) return "March"
        if(month == 4) return "April"
        if(month == 5) return "May"
        if(month == 6) return "June"
        if(month == 7) return "July"
        if(month == 8) return "August"
        if(month == 9) return "September"
        if(month == 10) return "October"
        if(month == 11) return "November"
        if(month == 12) return "December"
        return ""
    }
}