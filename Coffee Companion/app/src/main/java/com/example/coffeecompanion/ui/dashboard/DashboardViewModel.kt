package com.example.coffeecompanion.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeecompanion.Database.CoffeeType
import com.example.coffeecompanion.Database.CoffeeTypesDatabase
import com.example.coffeecompanion.Database.CoffeeTypesDatabaseDao
import com.example.coffeecompanion.MainActivity
import com.example.coffeecompanion.R

class DashboardViewModel : ViewModel() {
    public lateinit var coffee: List<CoffeeType>
    private var answers = Array(7) {0}

    // 0 = no answer
    // 1 = top answer
    // 2 = bottom answer
    fun changeAnswer(index : Int, answer : Int) : Boolean{
        answers[index] = answer

        // check if any other questions need answers
        for (item in answers){
            if (item == 0) {
                return false
            }
        }
        // all questions have been answered
        return true
    }

    fun calcResult(): String {
        var result = Array(coffee.size){0}
        var j = 0
        for (item in answers){
            if (j == 0){
                if (answers[j]==1){
                    var i = 0;
                    for(pos in coffee){
                        if (coffee.get(i).minsToBrew <= 4 ){
                            result[i]++
                        }
                        i++
                    }
                }
                if (answers[j]==2){
                    var i = 0;
                    for(pos in coffee){
                        if (coffee.get(i).minsToBrew >= 4 ){
                            result[i]++
                        }
                        i++
                    }
                }

            }
            else if (j == 1){
                if (answers[j]==1){
                    var i = 0;
                    for(pos in coffee){
                        if (coffee.get(i).instructions.length <= 500 ){
                            result[i]++
                            result[i]++
                        }
                        i++
                    }
                }
                if (answers[j]==2){
                    var i = 0;
                    for(pos in coffee){
                        if (coffee.get(i).instructions.length >= 500 ){
                            result[i]++
                        }
                        i++
                    }
                }
            }
            else if (j == 2){
                if (answers[j]==1){
                    var i = 0;
                    for(pos in coffee){
                        if (coffee.get(i).amount <= 16.5 ){
                            result[i]++
                            result[i]++
                        }
                        i++
                    }
                }
                if (answers[j]==2){
                    var i = 0;
                    for(pos in coffee){
                        if (coffee.get(i).amount >= 17 ){
                            result[i]++
                            result[i]++
                        }
                        i++
                    }
                }
            }
            else if (j == 3){
                if (answers[j]==1){
                    var i = 0;
                    for(pos in coffee){
                        if (coffee.get(i).minsToBrew >= 4 ){
                            result[i]++
                        }
                        i++
                    }
                }
                if (answers[j]==2){
                    var i = 0;
                    for(pos in coffee){
                        if (coffee.get(i).minsToBrew <= 4 ){
                            result[i]++
                            result[i]++
                        }
                        i++
                    }
                }
            }
            else if (j == 4){
                if (answers[j]==1){
                    var i = 0;
                    for(pos in coffee){
                        if (coffee.get(i).grind >= "Fine" ){
                            result[i]++
                            result[i]++
                        }
                        i++
                    }
                }
                if (answers[j]==2){
                    var i = 0;
                    for(pos in coffee){
                        if (coffee.get(i).grind >= "Coarse" ){
                            result[i]++
                            result[i]++
                        }
                        i++
                    }
                }
            }
            else if (j == 5){
                if (answers[j]==1){
                    var i = 0;
                    for(pos in coffee){
                        if (coffee.get(i).minsToBrew >= 10 ){
                            result[i]++
                        }
                        i++
                    }
                }
                if (answers[j]==2){
                    var i = 0;
                    for(pos in coffee){
                        if (coffee.get(i).minsToBrew <= 10 ){
                            result[i]++
                        }
                        i++
                    }
                }
            }
            else if (j == 6){
                if (answers[j]==1){
                    var i = 0;
                    for(pos in coffee){
                        if (coffee.get(i).amount <= 16.5 ){
                            result[i]++
                            result[i]++
                        }
                        i++
                    }
                }
                if (answers[j]==2){
                    var i = 0;
                    for(pos in coffee){
                        if (coffee.get(i).amount >= 17 ){
                            result[i]++
                            result[i]++
                        }
                        i++
                    }
                }
            }
        }
        val quizAnswerPos = result.indexOf(result.max())
        return(coffee.get(quizAnswerPos).name)
    }

}