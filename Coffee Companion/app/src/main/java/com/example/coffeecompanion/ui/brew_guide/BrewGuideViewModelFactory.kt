package com.example.coffeecompanion.ui.brew_guide

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coffeecompanion.Database.CoffeeTypesDatabaseDao

class BrewGuideViewModelFactory(
    private val dataSource: CoffeeTypesDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrewGuideViewModel::class.java)) {
            return BrewGuideViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}