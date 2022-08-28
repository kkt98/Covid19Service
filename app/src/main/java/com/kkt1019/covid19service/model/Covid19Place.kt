package com.kkt1019.covid19service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "covid19Place")
data class Covid19Place(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "centerName")
    val centerName: String,

    @ColumnInfo(name = "facilityName")
    val facilityName: String,

    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String,

    @ColumnInfo(name = "updatedAt")
    val updatedAt: String,

    @ColumnInfo(name = "lat")
    val lat: String,

    @ColumnInfo(name = "lng")
    val lng: String,

    @ColumnInfo(name = "centerType")
    val centerType: String,


)
