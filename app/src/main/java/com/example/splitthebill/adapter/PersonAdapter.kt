package com.example.splitthebill.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.splitthebill.R
import com.example.splitthebill.model.Person

class PersonAdapter(
    context: Context,
    private val personList: MutableList<Person>,
    private val forCalculate: Boolean = false
) : ArrayAdapter<Person>(context, R.layout.tile_person, personList) {
    private data class TilePersonHolder(
        val nameTv: TextView, val paidPriceTv: TextView, val priceToPayTv: TextView, val priceToReceiveTv: TextView
        )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val person = personList[position]

        var personTileView = convertView

        if (personTileView == null) {
            // Inflo uma nova célula
            personTileView =
                (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.tile_person,
                    parent,
                    false
                )

            val tilePersonHolder = TilePersonHolder(
                personTileView.findViewById(R.id.nameTv),
                personTileView.findViewById(R.id.paidPriceTv),
                personTileView.findViewById(R.id.priceToPayTv),
                personTileView.findViewById(R.id.priceToReceiveTv)
            )
            personTileView.tag = tilePersonHolder
        }

        with(personTileView?.tag as TilePersonHolder) {
            nameTv.text = person.name
            paidPriceTv.text = String.format("Pagou: R$ %.2f", person.valorPago)
            if (forCalculate) {
                priceToPayTv.text = String.format("Deve Pagar: R$ %.2f", person.valorPagar)
                priceToReceiveTv.text = String.format("Deve Receber: R$ %.2f", person.valorReceber)
            } else {
                priceToPayTv.visibility = View.GONE
                priceToReceiveTv.visibility = View.GONE
            }
        }

        return personTileView
    }
}
