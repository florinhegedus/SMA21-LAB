package com.upt.cti.smartwallet.model

class MonthlyExpenses {
    var month: String = ""
    var income: Float = 0f
    var expenses: Float = 0f

    constructor(month: String, income: Float, expenses: Float){
        this.month = month
        this.income = income
        this.expenses = expenses
    }
}