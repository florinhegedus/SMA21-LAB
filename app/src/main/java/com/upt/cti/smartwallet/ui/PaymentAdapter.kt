package com.upt.cti.smartwallet.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.upt.cti.smartwallet.R
import com.upt.cti.smartwallet.model.PaymentType

class PaymentAdapter(context: Context, var resource: Int, var items: ArrayList<PaymentType>)
    : ArrayAdapter<PaymentType>(context, R.layout.item_payment, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(context)

        val view: View = layoutInflater.inflate(resource, null)

        var titleView: TextView = view.findViewById(R.id.titleText)
        var descriptionView: TextView = view.findViewById(R.id.descriptionText)


        var payment: PaymentType = items[position]

        titleView.text = payment.name
        descriptionView.text = payment.type


        return view
    }
}

