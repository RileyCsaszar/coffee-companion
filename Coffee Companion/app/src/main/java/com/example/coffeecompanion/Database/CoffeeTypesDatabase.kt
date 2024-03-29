package com.example.coffeecompanion.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CoffeeType::class],version = 1, exportSchema = false)
abstract class CoffeeTypesDatabase : RoomDatabase() {
    abstract val coffeeTypesDatabaseDao: CoffeeTypesDatabaseDao



    companion object {
        @Volatile
        private var INSTANCE: CoffeeTypesDatabase? = null
        fun getInstance(context: Context): CoffeeTypesDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CoffeeTypesDatabase::class.java,
                        "coffee_types_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}