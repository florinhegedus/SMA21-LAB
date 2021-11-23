package com.upt.cti.smartwallet.model

import android.graphics.Color

class PaymentType {
    var time: String = ""
    var name: String = ""
    var type: String = ""
    var cost: Double = 0.0

    constructor(time: String, name: String, type: String, cost: Double) {
        this.time = time
        this.name = name
        this.type = type
        this.cost = cost
    }

    fun getColorFromPaymentType(): Int {
        return if (this.type == "entertainment") Color.rgb(200, 50, 50)
        else if (this.type == "food") Color.rgb(50, 150, 50)
        else if (this.type == "taxes") Color.rgb(20, 20, 150)
        else if (this.type == "travel") Color.rgb(230, 140, 0)
        else Color.rgb(100, 100, 100)
    }
}