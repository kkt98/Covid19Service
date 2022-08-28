package com.kkt1019.covid19service.model

data class covid19ItemVO(var data:MutableList<covid19Items>)

data class covid19Items(

    val address:String, //주소
    val centerName:String, //접종센터 이름
    val facilityName:String, //건물 이름
    val phoneNumber:String, //전화번호
    val updatedAt:String, //업데이트 날자
    val lat:String, //위도
    val lng:String, //경도
    val centerType:String //중앙 권역
)
