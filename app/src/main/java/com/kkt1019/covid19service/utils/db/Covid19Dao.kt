package com.kkt1019.covid19service.utils.db

import androidx.room.*
import com.kkt1019.covid19service.model.Covid19Place

@Dao
interface Covid19Dao {

    @Query("SELECT * FROM covid19Place")
    fun getAllCovid19Place(): List<Covid19Place>

    @Insert
    fun insertCovid19Place(covid19Place: Covid19Place)

    @Update
    fun updateCovid19Place(covid19Place: Covid19Place)

    @Delete
    fun deleteCovid19Place(covid19Place: Covid19Place)

}