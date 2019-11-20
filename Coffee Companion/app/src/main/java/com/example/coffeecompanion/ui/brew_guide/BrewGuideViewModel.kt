package com.example.coffeecompanion.ui.brew_guide

import android.app.Application
import androidx.lifecycle.*
import com.example.coffeecompanion.Database.CoffeeType
import com.example.coffeecompanion.Database.CoffeeTypesDatabaseDao
import kotlinx.coroutines.*


class BrewGuideViewModel(
    val database: CoffeeTypesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    public lateinit var coffee: LiveData<List<CoffeeType>>

    init {
        initializeCoffee()
        coffee = database.getAllCoffee()
    }




    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text



    private fun initializeCoffee() {
        uiScope.launch {
            setCoffee()
        }
    }

    private suspend fun setCoffee() {
        return withContext(Dispatchers.IO) {
            if (database.getNumEntries() == 0) {
                database.insert(
                    CoffeeType(
                        name = "French Press",
                        minsToBrew = 4.0,
                        grind = "Course and even",
                        instructions = "boil 350grams(12 oz) and grind 30 grams of coffee at a" +
                                " coarse grind.  Gently our twice as much water as you have " +
                                "coffee into the grounds in the pot. Wait 30 seconds then stir" +
                                " gently.  Pour the rest of the water in slowly and let steep " +
                                "of four min.  Press the filter down if the press is hard to " +
                                "press the grind is to fine, and if it drops quickly the grind " +
                                "is too coarse.  Aim for about 15 to 20 lbs of pressure.  Serve " +
                                "immediately and enjoy.",
                        amount = 17.0
                    )
                )
                database.insert(
                    CoffeeType(
                        name = "Pour Over",
                        minsToBrew = 3.0,
                        grind = "Fine (like sea salt",
                        instructions = "place filter in the dripper. Wet the filter with hot water " +
                                "then drain that water. Add coffee into filter and gently tap until" +
                                " the surface is level.  Place over cup on scale.  Start a timer, " +
                                "pour for 15 seconds slowly starting at the outer rim and slowly " +
                                "spiral toward the center of the grounds until scale reaches 60 " +
                                "grams.  Wait 30 seconds, then do the reverse of the previous step, " +
                                "pour for 20 seconds from the center to the rim at the same rate " +
                                "until the scale reaches 150 grams. Wait a 60 seconds. Repeat this " +
                                "step 2 more times bringing the scale to 250 grams and 350 grams.  " +
                                "Serve immediately and enjoy",
                        amount = 20.0
                    )
                )
                database.insert(
                    CoffeeType(
                        name = "Auto Drip",
                        minsToBrew = 5.0,
                        grind = "Fine or Medium",
                        instructions = "add water to the reserve and filter and fresh ground coffee " +
                                "into the Auto Drip. Set machine to brew and wait 2-5 min.  Once " +
                                "coffee has brewed remove from heating element immediately and enjoy.",
                        amount = 20.0
                    )
                )
                database.insert(
                    CoffeeType(
                        name = "Aeropress",
                        minsToBrew = 1.5,
                        grind = "Fine",
                        instructions = "insert paper filter into AP’s(AeroPress) detachable cap. " +
                                " Wet filter and cap.  Assemble AP and ensure device is dry. " +
                                " Place AP on scale with flared end up. Add ground coffee. " +
                                " Start a timer, add twice as much water as you have coffee.  " +
                                "Let sit for 30sec.  use the remainder of the hot water to fill the AP." +
                                "  Wait 1 min and stir grounds 8-12 times.  Tighten the cap and ensure " +
                                "it is secure.  Flip over device and place over cup.  Press the " +
                                "plunger all the way down with about 30 lbs of pressure.  If it is " +
                                "lighter or heavier then 30 lbs of pressure your grind is either too " +
                                "coarse or too fine.  Serve immediately and enjoy.",
                        amount = 7.0
                    )
                )
                database.insert(
                    CoffeeType(
                        name = "Cold Brew",
                        minsToBrew = 720.0,
                        grind = "Coarse",
                        instructions = "place 350 grams(12 oz) of water and 30 grams(3 tbsp) of coffee" +
                                " into a covered container and place in refrigerator for 12-14 hours(" +
                                "preferably for most, over night).  Remove from refrigerator and " +
                                "filter with your choice of filtration technique and enjoy.",
                        amount = 24.0
                    )
                )
                database.insert(
                    CoffeeType(
                        name = "Single Serve",
                        minsToBrew = 3.0,
                        grind = "No Grind",
                        instructions = "place water and single cup coffee grounds of choice into " +
                                "single use coffee maker. Close the maker and place cup under spout. " +
                                " Press the start button of coffee maker and wait 1-3min.  Enjoy.",
                        amount = 16.0
                    )
                )
                database.insert(
                    CoffeeType(
                        name = "Stove Top Espresso Percolator",
                        minsToBrew = 5.0,
                        grind = "Fine",
                        instructions = "boil water and fill bottom of the pot with freshly boiled water." +
                                "  Place filter basket into pot and fill this basket with 23 grams of coffee." +
                                " Screw on the top of the pot.  Place the pot on a stove and set to medium heat. " +
                                " Listen to the sound of the percolator brewing, when the sounds becomes a " +
                                "hissing bubbling sound the coffee is done.  Serve and enjoy.",
                        amount = 17.0
                    )
                )
            }
        }
    }



}