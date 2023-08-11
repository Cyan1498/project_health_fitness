package com.grupoc.project_health_fitness.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.grupoc.project_health_fitness.R

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var MapaGoogle :GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)
        createFragment()
    }

    private fun createFragment() {
        val mapFragment=supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(mapa: GoogleMap) {
        MapaGoogle=mapa
        val cordenada= LatLng(36.204824, 138.252924)
        MapaGoogle.addMarker(MarkerOptions().position(cordenada).title("Japon"))
    }
}