package com.example.myapplication.ui.map

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap : GoogleMap
    private lateinit var binding : ActivityLocationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)



    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        val customSnipet = CustomWindowGoogleMaps(this)
        mMap.setInfoWindowAdapter(customSnipet)


        val lat = -7.753212863388888
        val long = 110.43687429108138
        val margaJaya = LatLng(lat, long)

        val markerOption = MarkerOptions()
        markerOption.position(margaJaya).icon(vectorToBitmap(R.drawable.markerjaya))

        val marker = mMap.addMarker(markerOption)
        marker?.showInfoWindow()

        mMap.setOnInfoWindowClickListener {
            val uri = Uri.parse("geo:0, 0?q=$lat,$long")
            val i = Intent(Intent.ACTION_VIEW,uri)
            i.setPackage("com.google.android.apps.maps")
            startActivity(i)
        }

//        mMap.addMarker(
//            MarkerOptions()
//                .position(margaJaya)
//                .title("MARGA JAYA")
//                .snippet("Batik Kumeli No.50")
//        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(margaJaya, 18f))
    }


    private fun vectorToBitmap (@DrawableRes id : Int):BitmapDescriptor{
        val vectorDrawable = ResourcesCompat.getDrawable(resources,id,null)
        if (vectorDrawable == null){
            Log.e("BitmapHelper", "Resource not found")
            return BitmapDescriptorFactory.defaultMarker()
        }

        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0,0,canvas.width,canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


}