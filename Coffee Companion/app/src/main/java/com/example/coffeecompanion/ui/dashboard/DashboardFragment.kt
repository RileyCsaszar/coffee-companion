package com.example.coffeecompanion.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.coffeecompanion.Database.CoffeeTypesDatabase
import com.example.coffeecompanion.Database.CoffeeTypesDatabaseDao
import com.example.coffeecompanion.MainActivity
import com.example.coffeecompanion.R
import com.example.coffeecompanion.databinding.FragmentDashboardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    lateinit var binding: FragmentDashboardBinding
    private lateinit var database : CoffeeTypesDatabaseDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        var current = activity as MainActivity
        dashboardViewModel.coffee = current.brewlist

        binding.q1Option1.setOnClickListener { update(0, 1) }
        binding.q1Option2.setOnClickListener { update(0, 2) }
        binding.q2Option1.setOnClickListener { update(1, 1) }
        binding.q2Option2.setOnClickListener { update(1, 2) }
        binding.q3Option1.setOnClickListener { update(2, 1) }
        binding.q3Option2.setOnClickListener { update(2, 2) }
        binding.q4Option1.setOnClickListener { update(3, 1) }
        binding.q4Option2.setOnClickListener { update(3, 2) }
        binding.q5Option1.setOnClickListener { update(4, 1) }
        binding.q5Option2.setOnClickListener { update(4, 2) }
        binding.q6Option1.setOnClickListener { update(5, 1) }
        binding.q6Option2.setOnClickListener { update(5, 2) }
        binding.q7Option1.setOnClickListener { update(6, 1) }
        binding.q7Option2.setOnClickListener { update(6, 2) }

        binding.submitButton.setOnClickListener {
            binding.resultText.text = "The best Brew for you is "+dashboardViewModel.calcResult()+"!"
            binding.resultText.visibility = View.VISIBLE
            binding.quizView.visibility = View.GONE
        }

        return binding.root
    }

    fun update(index : Int, value : Int){
        if(dashboardViewModel.changeAnswer(index, value)){
            binding.submitButton.visibility = View.VISIBLE
        }
    }
}