package com.example.coffeecompanion.ui.brew_guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.coffeecompanion.databinding.FragmentBrewGuideBinding
import com.example.coffeecompanion.R


class BrewGuideFragment : Fragment() {

    private lateinit var homeViewModel: BrewGuideViewModel

    private lateinit var binding: FragmentBrewGuideBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentBrewGuideBinding>(inflater,
            R.layout.fragment_brew_guide,container,false)

        homeViewModel = ViewModelProviders.of(this).get(BrewGuideViewModel::class.java)

        val your_array_list = ArrayList<BrewTypeData>()
        your_array_list.add(BrewTypeData("Name", "Size",
            "Quantity", "Time", "Instructions"))
        your_array_list.add(BrewTypeData("Name2", "Size2",
            "Quantity2", "Time2", "Instructions2"))

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        val arrayAdapter = BrewGuideAdapter(
            requireContext(),
            R.layout.brew_list_item,
            your_array_list
        )

        binding.brewList.adapter = arrayAdapter

        return binding.root
    }
}