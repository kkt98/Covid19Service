package com.kkt1019.covid19service.repositori.db

import androidx.room.*
import com.kkt1019.covid19service.model.Covid19Place

@Dao
interface Covid19Dao {

    @Query("SELECT * FROM covid19Place")
    fun getAllCovid19Place(): List<Covid19Place>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCovid19Place(vararg covid19Places: Covid19Place)

    @Update
    fun updateCovid19Place(covid19Place: Covid19Place)

    @Delete
    fun deleteCovid19Place(covid19Place: Covid19Place)

}