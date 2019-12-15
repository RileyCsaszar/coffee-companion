package com.example.coffeecompanion.ui.brew_guide

import android.app.PendingIntent.getActivity
import android.content.Context
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.coffeecompanion.Database.CoffeeType
import com.example.coffeecompanion.Database.CoffeeTypesDatabaseDao
import com.example.coffeecompanion.R
import com.example.coffeecompanion.databinding.FragmentBrewGuideBinding
import com.example.coffeecompanion.ui.notifications.binding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.sql.DataSource

//data class BrewTypeData(var brewName: String, var groundSize: String,
//                        var coffeeQuantity: String, var time: String, var instructions: String)


class BrewGuideAdapter(
    context: Context,
    private val layoutResource: Int,
    brewList: List<CoffeeType>,
    fragment: BrewGuideFragment
) : ArrayAdapter<CoffeeType>(context, layoutResource, brewList) {
    var fragment: BrewGuideFragment = fragment


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
            val ranking = view.findViewById(R.id.favorite) as ImageView

            titleText.text = currentBrew.name
            grindText.text = "Grind size: " + currentBrew.grind
            amountText.text = "Quantity: " + currentBrew.amount
            timeText.text = "Time: " + currentBrew.minsToBrew
            instructionsText.text = "Instructions: " + currentBrew.instructions
            if (currentBrew.priority != 0) {
                ranking.setImageResource(R.drawable.star_full)
            } else ranking.setImageResource(R.drawable.star_empty)
            ranking.setOnClickListener{view:View ->
                if (currentBrew.priority != 0) {
                    //ranking.setImageResource(R.drawable.star_empty)
                    GlobalScope.launch { (fragment.updateData(0,currentBrew)) }
                    //notifyDataSetChanged()
                } else {
                    //ranking.setImageResource(R.drawable.star_full)
                    GlobalScope.launch { (fragment.updateData(1,currentBrew)) }
                    //notifyDataSetChanged()
                }
                notifyDataSetChanged()
            }
        }

        return view
    }
}