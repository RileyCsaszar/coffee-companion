package com.example.coffeecompanion.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CoffeeTypesDatabaseDao {
    @Insert
    fun insert(coffee: CoffeeType)

    @Update
    fun update(coffee: CoffeeType)

    @Query("select * from coffee_types_table where coffeeId = :key")
    fun get(key: Long): CoffeeType?

    @Query("delete from coffee_types_table")
    fun clear()

    @Query("select * from coffee_types_table order by coffeeId desc limit 1")
    fun getLastCoffee(): CoffeeType?

    @Query("select * from coffee_types_table order by priority desc")
    fun getAllCoffee(): LiveData<List<CoffeeType>>

    @Query("select count(*) from coffee_types_table")
    fun getNumEntries(): Int
}