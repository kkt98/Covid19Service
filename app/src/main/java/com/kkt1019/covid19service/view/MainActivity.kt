package com.kkt1019.covid19service.view

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kkt1019.covid19service.R
import com.kkt1019.covid19service.databinding.ActivityMainBinding
//import com.kkt1019.covid19service.utils.network.MyViewMocdelFactory
import com.kkt1019.covid19service.viewmodel.MyViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.NaverMapSdk.NaverCloudPlatformClient
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1000
    private var locationSource: FusedLocationSource? = null
    private var naverMap: NaverMap? = null
    lateinit var uiSettings : UiSettings
//    val marker = Marker()

    private lateinit var vm : MyViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        vm = ViewModelProvider(this, MyViewModel.MyViewMocdelFactory(application))[MyViewModel::class.java]

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewModel = vm

        //지도
        NaverMapSdk.getInstance(this).client = NaverCloudPlatformClient("ght6e8nfpq")

        //위치정보 퍼미션
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        // 뷰 역할을 하는 프래그먼트 객체 얻기
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.naver_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.naver_map, it).commit()
            }

        // 인터페이스 역할을 하는 NaverMap 객체 얻기
        // 프래그먼트(MapFragment)의 getMapAsync() 메서드로 OnMapReadyCallback 을 등록하면 비동기로 NaverMap 객체를 얻을 수 있다고 한다.
        // NaverMap 객체가 준비되면 OnMapReady() 콜백 메서드 호출
        mapFragment.getMapAsync(this)

    }

    //위치정보 퍼미션
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (locationSource!!.onRequestPermissionsResult(
                requestCode, permissions, grantResults
            )
        ) {
            if (!locationSource!!.isActivated) { // 권한 거부됨
                naverMap!!.locationTrackingMode = LocationTrackingMode.None
                AlertDialog.Builder(this)
                    .setTitle("경고")
                    .setMessage("앱을 이용하시려면 위치 정보 사용 권한이 필요합니다.")
                    .setPositiveButton("확인") { dialog, which -> //
                        dialog.dismiss()
                    }.create().show()
            }
            return
        }
        super.onRequestPermissionsResult(
            requestCode, permissions, grantResults
        )

    }

    // 네이버 맵 객체 획득 후, 이벤트 코드.
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        naverMap.isNightModeEnabled = true // 이쁜 야간모드 지원 ㅎㅎ

        naverMap.locationSource = locationSource

        uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true
        // 요기서 마커 불러오고.. 말풍선도 만들기..

        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        naverMap.mapType = NaverMap.MapType.Basic

        vm.getAllCovid19Place.observe(this, Observer {

            it.forEach{ data ->

                val marker = Marker()
                marker.position = LatLng(data.lat!!.toDouble(), data.lng!!.toDouble())
                marker.map = naverMap

                if(data.centerType == "중앙/권역" ){

                    marker.icon = MarkerIcons.BLACK
                    marker.iconTintColor = Color.RED

                    marker.onClickListener

                }

                //마커 클릭시 정보 띄우기
                val infoWindow = InfoWindow()
                infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
                    override fun getText(infoWindow: InfoWindow): CharSequence {

                        return "센터 이름 : ${data.centerName} \n 시설명 : ${data.facilityName} \n 주소 : ${data.address}" +
                                "\n 전화번호 : ${data.phoneNumber} \n 센터 유형 : ${data.centerType}"
                    }
                }

                // 마커를 클릭하면:
                val listener = Overlay.OnClickListener { overlay ->
                    val marker = overlay as Marker

                    if (marker.infoWindow == null) {
                        // 현재 마커에 정보 창이 열려있지 않을 경우 열기
                        infoWindow.open(marker)
                    } else {
                        // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                        infoWindow.close()
                    }

                    true
                }

                marker.onClickListener = listener

            }

        })
    }

}