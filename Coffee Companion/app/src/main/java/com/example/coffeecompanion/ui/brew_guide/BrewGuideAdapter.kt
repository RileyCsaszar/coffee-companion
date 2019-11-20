package com.example.coffeecompanion.ui.brew_guide

import android.content.Context
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.coffeecompanion.R

data class BrewTypeData(var brewName: String, var groundSize: String,
                        var coffeeQuantity: String, var time: String, var instructions: String)

class BrewGuideAdapter(
    context: Context,
    private val layoutResource: Int,
    brewList: List<BrewTypeData>
) : ArrayAdapter<BrewTypeData>(context, layoutResource, brewList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View

        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(layoutResource, null)
        }
        else {
            view = convertView
        }

        val currentBrew = getItem(position)

        if (currentBrew != null) {
            val titleText = view.findViewById(R.id.brew_item_title) as TextView
            val grindText = view.findViewById(R.id.brew_item_grind) as TextView
            val amountText = view.findViewById(R.id.brew_item_amount) as TextView
            val timeText = view.findViewById(R.id.brew_item_time) as TextView
            val instructionsText = view.findViewById(R.id.brew_item_instructions) as TextView

            titleText.text = currentBrew.brewName
            grindText.text = "Grind size: " + currentBrew.groundSize
            amountText.text = "Quantity: " + currentBrew.coffeeQuantity
            timeText.text = "Time: " + currentBrew.time
            instructionsText.text = "Instructions: " + currentBrew.instructions
        }

        return view
    }
}