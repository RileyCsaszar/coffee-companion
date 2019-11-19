package com.example.coffeecompanion.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coffee_types_table")
data class CoffeeType (
    @PrimaryKey(autoGenerate = true)
    var coffeeId: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "mins_to_brew")
    val minsToBrew: Double = 0.0,

    @ColumnInfo(name = "grind")
    val grind: String = "",

    @ColumnInfo(name = "instructions")
    val instructions: String = "",

    @ColumnInfo(name = "amount")
    val amount: Double = 0.0,

    @ColumnInfo(name = "priority")
    val priority: Int
    )