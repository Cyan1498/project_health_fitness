package com.grupoc.project_health_fitness.view.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.grupoc.project_health_fitness.R

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var MapaGoogle :GoogleMap

    companion object{
        const val REQUEST_CODE_LOCATION = 0
    }
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
        val coordenada= LatLng(-9.127690670368244, -78.51780027952374)
        val markerBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_gym)
        val scaledBitmap = Bitmap.createScaledBitmap(markerBitmap, 40, 45, false)

        val markerOptions = MarkerOptions()
            .position(coordenada)
            .title("Japón")
            .snippet("Este es un gimnasio muy popular en la ciudad.")
            .icon(BitmapDescriptorFactory.fromBitmap(scaledBitmap)) // Aquí se establece el icono personalizado

        // Agregar el marcador al mapa
        MapaGoogle.addMarker(markerOptions)

        MapaGoogle.setOnMarkerClickListener { marker ->
            val dialog = AlertDialog.Builder(this)
                .setTitle(marker.title)
                .setMessage(marker.snippet)
                .setPositiveButton("OK", null)
                .create()
            dialog.show()

            true
        }

        enableLocation()
    }

    private fun isLocationPermissionGranted()=
        ContextCompat.checkSelfPermission(
            this,Manifest.permission.ACCESS_FINE_LOCATION
        )==PackageManager.PERMISSION_GRANTED



    private fun enableLocation(){
        if(!::MapaGoogle.isInitialized) return
        if(isLocationPermissionGranted()){
            MapaGoogle.isMyLocationEnabled= true
            if (!isGPSEnabled()) {
                showEnableGPSDialog()
            }
        }else{
            requestLocationPermission()
        }
    }
    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this,"Ve a ajustes y activa los permisos",Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            MapaGoogle.isMyLocationEnabled=true
            }else{
                Toast.makeText(this,"Para activar ve a ajustes y acepta",Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    private fun isGPSEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun showEnableGPSDialog() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::MapaGoogle.isInitialized) return
        if(isLocationPermissionGranted()){
            MapaGoogle.isMyLocationEnabled=false
            Toast.makeText(this,"Para activar ve a ajustes y acepta",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_map, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_option1 -> {
                // Realiza la acción correspondiente a la opción 1
                return true
            }
            R.id.action_option2 -> {
                // Realiza la acción correspondiente a la opción 2
                return true
            }
            // Agrega más casos para manejar otras opciones del menú
            else -> return super.onOptionsItemSelected(item)
        }
    }
}