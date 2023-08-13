package com.grupoc.project_health_fitness.view.fragments

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.grupoc.project_health_fitness.R

class MapaFragment : Fragment(), OnMapReadyCallback {

    private lateinit var MapaGoogle :GoogleMap
    private var currentMapStyle: Int = R.raw.style_json
    var fabVisible = false
    private val gymMarkers: MutableList<Marker> = mutableListOf()
    private val parkMarkers: MutableList<Marker> = mutableListOf()

    companion object{
        const val REQUEST_CODE_LOCATION = 0
        var style_json = R.raw.style_json
        var style2_json = R.raw.style2_json
        var style3_json = R.raw.style3_json
        var style4_json = R.raw.style4_json
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mapa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        // initializing variables of floating
        // action button on below line.

        val button_function = view.findViewById<FloatingActionButton>(R.id.button_function)
        val button_gym = view.findViewById<FloatingActionButton>(R.id.button_gym)
        val button_theme = view.findViewById<FloatingActionButton>(R.id.button_theme)
        val button_park=view.findViewById<FloatingActionButton>(R.id.button_park)

        // on below line we are initializing our
        // fab visibility boolean variable
        fabVisible = false

        // on below line we are adding on click listener
        // for our add floating action button
        button_function.setOnClickListener {
            // on below line we are checking
            // fab visible variable.
            if (!fabVisible) {

                // if its false we are displaying home fab
                // and settings fab by changing their
                // visibility to visible.
                button_gym.show()
                button_theme.show()
                button_park.show()

                // on below line we are setting
                // their visibility to visible.
                button_gym.visibility = View.VISIBLE
                button_theme.visibility = View.VISIBLE
                button_park.visibility=View.VISIBLE

                // on below line we are checking image icon of add fab
                button_function.setImageDrawable(resources.getDrawable(R.drawable.ic_close))

                // on below line we are changing
                // fab visible to true
                fabVisible = true
            } else {

                // if the condition is true then we
                // are hiding home and settings fab
                button_gym.hide()
                button_theme.hide()
                button_park.hide()

                // on below line we are changing the
                // visibility of home and settings fab
                button_gym.visibility = View.GONE
                button_theme.visibility = View.GONE
                button_park.visibility = View.GONE

                // on below line we are changing image source for add fab
                button_function.setImageDrawable(resources.getDrawable(R.drawable.ic_plus))
                clearGymMarkers()
                clearParkMarkers()
                // on below line we are changing
                // fab visible to false.
                fabVisible = false
            }
        }

        button_park.setOnClickListener {
            clearGymMarkers()
            Toast.makeText(requireContext(), "Marcando parques...", Toast.LENGTH_SHORT).show()
            val markerBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_ejercicio)
            val scaledBitmap = Bitmap.createScaledBitmap(markerBitmap, 60, 55, false)
            clearParkMarkers()
            val latLngNuevoChimbote = LatLng(-9.11612660535203, -78.51456909050911)
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngNuevoChimbote,14f)
            MapaGoogle.animateCamera(cameraUpdate,5000,null)
            for (ParkInfo in parkInfoList) {
                val markerOptions = MarkerOptions()
                    .position(ParkInfo.latLng)
                    .title(ParkInfo.title)
                    .snippet(ParkInfo.description)
                    .icon(BitmapDescriptorFactory.fromBitmap(scaledBitmap))

                val marker = MapaGoogle.addMarker(markerOptions)
                marker?.let {
                    parkMarkers.add(it)
                }
            }

            MapaGoogle.setOnMarkerClickListener { marker ->
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle(marker.title)
                    .setMessage(marker.snippet)
                    .setPositiveButton("OK", null)
                    .create()
                dialog.show()

                true
            }
        }

        // on below line we are adding
        // click listener for our home fab
        button_gym.setOnClickListener {
            clearParkMarkers()
            // on below line we are displaying a toast message.
            Toast.makeText(requireContext(), "Marcando gimnasios...", Toast.LENGTH_SHORT).show()
            val markerBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_gym)
            val scaledBitmap = Bitmap.createScaledBitmap(markerBitmap, 60, 55, false)
            val latLngNuevoChimbote = LatLng(-9.11612660535203, -78.51456909050911)
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngNuevoChimbote,14f)
            MapaGoogle.animateCamera(cameraUpdate,5000,null)
            for (gymInfo in gymInfoList) {
                val markerOptions = MarkerOptions()
                    .position(gymInfo.latLng)
                    .title(gymInfo.title)
                    .snippet(gymInfo.description)
                    .icon(BitmapDescriptorFactory.fromBitmap(scaledBitmap))

                val marker = MapaGoogle.addMarker(markerOptions)
                marker?.let {
                    parkMarkers.add(it)
                }
            }


            MapaGoogle.setOnMarkerClickListener { marker ->
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle(marker.title)
                    .setMessage(marker.snippet)
                    .setPositiveButton("OK", null)
                    .create()
                dialog.show()

                true
            }
        }

        // on below line we are adding on
        // click listener for settings fab
        button_theme.setOnClickListener {
            // Cambiar el estilo del mapa al siguiente estilo.
            when (currentMapStyle) {
                R.raw.style_json -> currentMapStyle = R.raw.style2_json
                R.raw.style2_json -> currentMapStyle = R.raw.style3_json
                R.raw.style3_json -> currentMapStyle = R.raw.style4_json
                R.raw.style4_json -> currentMapStyle = R.raw.style_json
            }

            // Aplicar el nuevo estilo del mapa.
            setMapStyle(MapaGoogle, currentMapStyle)

            Toast.makeText(requireContext(), "Cambiando el estilo del mapa...", Toast.LENGTH_LONG).show()

        }
    }


    // Función para aplicar un estilo de mapa desde un archivo JSON.
    private fun setMapStyle(googleMap: GoogleMap, styleResId: Int) {
        val success = googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                requireContext(), styleResId
            )
        )

        if (!success) {
            Log.e(TAG, "Fallo al analizar el estilo.")
        }
    }

    data class GymInfo(val latLng: LatLng, val title: String, val description: String)

    // En la parte superior del fragmento
    private val gymInfoList: List<GymInfo> = listOf(
        GymInfo(LatLng(-9.113623197765701, -78.51520381989593), "Gimnasio 1", "Descripción del gimnasio 1"),
        GymInfo(LatLng(-9.114989312396476, -78.51000012366427), "Gimnasio 2", "Descripción del gimnasio 2"),
        // Agrega más gimnasios aquí
    )

    data class ParkInfo(val latLng: LatLng, val title: String, val description: String)

    // En la parte superior del fragmento
    private val parkInfoList: List<ParkInfo> = listOf(
        ParkInfo(LatLng(-9.113623197765701, -78.51520381989593), "Parque Biosaludable Bellamar", "Descripción del gimnasio 1"),
        ParkInfo(LatLng(-9.114989312396476, -78.51000012366427), "Parque 'El Pescador'", "Descripción del gimnasio 2"),
        ParkInfo(LatLng(- 9.118356686889372, -78.52431990863224), "Parque Biosaludable Santa Rosa", "Descripción del gimnasio 3"),
        // Agrega más gimnasios aquí
    )

    private fun clearGymMarkers() {
        for (marker in gymMarkers) {
            marker.remove()
        }
        gymMarkers.clear()
    }

    private fun clearParkMarkers() {
        for (marker in parkMarkers) {
            marker.remove()
        }
        parkMarkers.clear()
    }


    override fun onMapReady(mapa: GoogleMap) {
        MapaGoogle=mapa
        MapaGoogle.clear()
        MapaGoogle.isTrafficEnabled = false
        MapaGoogle.isIndoorEnabled = false
        MapaGoogle.uiSettings.isCompassEnabled = false
        MapaGoogle.uiSettings.isMapToolbarEnabled = false
    //    val coordenada= LatLng(-9.127690670368244, -78.51780027952374)
      //  val markerBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_gym)
        //val scaledBitmap = Bitmap.createScaledBitmap(markerBitmap, 40, 45, false)

        //val markerOptions = MarkerOptions()
          //  .position(coordenada)
           // .title("Japón")
            //.snippet("Este es un gimnasio muy popular en la ciudad.")
            //.icon(BitmapDescriptorFactory.fromBitmap(scaledBitmap)) // Aquí se establece el icono personalizado

        // Agregar el marcador al mapa
        //MapaGoogle.addMarker(markerOptions)

        //MapaGoogle.setOnMarkerClickListener { marker ->
            //val dialog = AlertDialog.Builder(requireContext())
               // .setTitle(marker.title)
               // .setMessage(marker.snippet)
               // .setPositiveButton("OK", null)
              //  .create()
            //dialog.show()

          //  true
        //}

        enableLocation()
    }

    private fun isLocationPermissionGranted()=
        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        )== PackageManager.PERMISSION_GRANTED



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
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){
            redirectToAppSettings()
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }


    private fun isGPSEnabled(): Boolean {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun showEnableGPSDialog() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("El GPS está desactivado. ¿Deseas activarlo?")
            .setPositiveButton("Sí") { _, _ ->
                startActivity(intent) // Redirigir a la pantalla de configuración de la aplicación
            }
            .setNegativeButton("No", null)
            .create()
        dialog.show()

    }

    private fun redirectToAppSettings() {

        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("Los permisos estan desactivados. ¿Deseas activarlo?")
            .setPositiveButton("Sí") { _, _ ->
                intent.data = uri
                startActivity(intent) // Redirigir a la pantalla de configuración de la aplicación
            }
            .setNegativeButton("No", null)
            .create()
        dialog.show()

    }

    override fun onResume() {
        super.onResume()
        if (::MapaGoogle.isInitialized && isLocationPermissionGranted()) {
            if (isGPSEnabled()) {
                MapaGoogle.isMyLocationEnabled = true
            } else {
                // Aquí puedes mostrar un mensaje indicando que el GPS no está activado
                showEnableGPSDialog()
            }
        }
    }
}



