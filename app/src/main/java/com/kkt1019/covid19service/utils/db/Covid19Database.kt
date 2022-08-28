package com.kkt1019.covid19service.utils.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kkt1019.covid19service.model.Covid19Place

@Database(entities = [Covid19Place::class], version = 1)
abstract class Covid19Database: RoomDatabase() {

    abstract fun covid19Dao(): Covid19Dao

    companion object{

        private var INSTANCE: Covid19Database? = null

        fun getDatabase(context: Context): Covid19Database?{

            if(INSTANCE == null){

                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    Covid19Database::class.java,
                    "covid19_database")
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE

        }

    }

}