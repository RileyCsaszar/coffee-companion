package com.example.coffeecompanion.ui.brew_guide

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.example.coffeecompanion.Database.CoffeeType
import com.example.coffeecompanion.Database.CoffeeTypesDatabase
import com.example.coffeecompanion.Database.CoffeeTypesDatabaseDao
import com.example.coffeecompanion.databinding.FragmentBrewGuideBinding
import com.example.coffeecompanion.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.widget.Toast
import com.example.coffeecompanion.MainActivity
import android.content.DialogInterface
import com.rengwuxian.materialedittext.MaterialEditText
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class BrewGuideFragment : Fragment() {

    //private lateinit var homeViewModel: BrewGuideViewModel

    private lateinit var binding: FragmentBrewGuideBinding
    lateinit var dataSource: CoffeeTypesDatabaseDao
    lateinit var brewGuideViewModel: BrewGuideViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_brew_guide,container,false)


        val application = requireNotNull(this.activity).application
        dataSource = CoffeeTypesDatabase.getInstance(application).coffeeTypesDatabaseDao
        val viewModelFactory = BrewGuideViewModelFactory(dataSource,application)
        brewGuideViewModel = ViewModelProviders.of(this,viewModelFactory).get(BrewGuideViewModel::class.java)
        binding.brewGuideFab.setOnClickListener{view: View ->
            showSignUpDialog()
        }

        updateUI()

        binding.setLifecycleOwner(this)
        binding.brewGuideViewModel = brewGuideViewModel

        return binding.root
    }


    private fun updateUI() {
        //wait for the data to populate
        brewGuideViewModel.coffee.observe(this, Observer<List<CoffeeType>> { data: List<CoffeeType>? ->
            // Update the UI.
            val arrayAdapter = BrewGuideAdapter(
                requireContext(),
                R.layout.brew_list_item,
                brewGuideViewModel.coffee.value!!,
                this
            )
            binding.brewList.adapter = arrayAdapter
        })
    }


    suspend fun updateData(priority: Int, coffee: CoffeeType) {
        withContext(Dispatchers.Default) {
            var newCoffee: CoffeeType = CoffeeType(
                coffee.coffeeId,
                coffee.name,
                coffee.minsToBrew,
                coffee.grind,
                coffee.instructions,
                coffee.amount,
                priority
            )

            dataSource.update(newCoffee)
        }
    }

    suspend fun insertDAta(coffee: CoffeeType) {
        withContext(Dispatchers.Default) {
            dataSource.update(coffee)
        }
    }


    private fun showSignUpDialog() {
        val alertDialog = AlertDialog.Builder(context!!)
        alertDialog.setTitle("New Brew Method")
        alertDialog.setMessage("Please fill in the information")

        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.popup_layout, null)

        var name = view.findViewById(R.id.coffee_name) as EditText
        var grind = view.findViewById(R.id.coffee_grind) as EditText
        var instructions = view.findViewById(R.id.coffee_instructions) as EditText
        var time = view.findViewById(R.id.coffee_time) as EditText
        var amount = view.findViewById(R.id.coffee_amount) as EditText

        alertDialog.setView(view)
        alertDialog.setIcon(R.drawable.icon)

        alertDialog.setNegativeButton("CANCEL",
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
                //Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
            })

        alertDialog.setPositiveButton("CONTINUE",
            DialogInterface.OnClickListener { dialogInterface, i ->

                var newCoffee: CoffeeType = CoffeeType(
                    name = name.getText().toString(),
                    grind = grind.getText().toString(),
                    instructions = instructions.getText().toString(),
                    minsToBrew = time.getText().toString().toDouble(),
                    amount = amount.getText().toString().toDouble(), priority = 1)
                GlobalScope.async { dataSource.insert(newCoffee) }

                updateUI()

                Toast.makeText(context, "Added Coffee!", Toast.LENGTH_SHORT).show()
            })
        alertDialog.show()
    }


}