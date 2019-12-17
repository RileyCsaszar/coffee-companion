package com.example.coffeecompanion.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

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

    fun calcResult(){
        for (item in answers){
            if (item == 1){
                // do thing
            }
            else if (item == 2){
                // do thing
            }
        }
    }

}