package com.example.coffeecompanion.ui.brew_guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.coffeecompanion.Database.CoffeeType
import com.example.coffeecompanion.Database.CoffeeTypesDatabase
import com.example.coffeecompanion.databinding.FragmentBrewGuideBinding
import com.example.coffeecompanion.R


class BrewGuideFragment : Fragment() {

    //private lateinit var homeViewModel: BrewGuideViewModel

    private lateinit var binding: FragmentBrewGuideBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentBrewGuideBinding>(inflater,
            R.layout.fragment_brew_guide,container,false)


        val application = requireNotNull(this.activity).application
        val dataSource = CoffeeTypesDatabase.getInstance(application).coffeeTypesDatabaseDao
        val viewModelFactory = BrewGuideViewModelFactory(dataSource,application)
        val brewGuideViewModel = ViewModelProviders.of(this,viewModelFactory).get(BrewGuideViewModel::class.java)

        //wait for the data to populate
        brewGuideViewModel.coffee.observe(this, Observer<List<CoffeeType>> { data: List<CoffeeType>? ->
            // Update the UI.
            val arrayAdapter = BrewGuideAdapter(
                requireContext(),
                R.layout.brew_list_item,
                brewGuideViewModel.coffee.value!!
            )
            binding.brewList.adapter = arrayAdapter
        })

        binding.setLifecycleOwner(this)
        binding.brewGuideViewModel = brewGuideViewModel

        return binding.root
    }
}