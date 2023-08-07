package com.grupoc.project_health_fitness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.tabs.TabLayout
import com.grupoc.project_health_fitness.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var loadingAnimationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar la vista de la pantalla de inicio
        val splashView = LayoutInflater.from(this).inflate(R.layout.logo_splash, null)
        setContentView(splashView)

        // Acceder al LottieAnimationView usando su ID
        loadingAnimationView = splashView.findViewById(R.id.animate_heart)
        // Cargar la animación en el LottieAnimationView
        loadingAnimationView.setAnimation(R.raw.logo_animation)
        // Reproducir la animación
        loadingAnimationView.playAnimation()

        // Después de unos segundos (por ejemplo, 3 segundos), ocultar la pantalla de inicio y cargar el layout principal
        Handler(Looper.getMainLooper()).postDelayed({
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setupView()

        }, 3000) // Retraso de 3 segundos

    }

    private fun setupView(){

        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager

        //Agrega una nueva pestaña al TabLayout y establece su texto como "Login".
        tabLayout.addTab(tabLayout.newTab().setText("LOGIN"))
        tabLayout.addTab(tabLayout.newTab().setText("REGISTRO"))

        val fragmentManager: FragmentManager = supportFragmentManager
        // pasa el FragmentManager y el ciclo de vida de la actividad como parámetros.
        adapter = ViewPagerAdapter(fragmentManager, lifecycle)
        //Establece el adaptador del ViewPager2 como el adaptador personalizado ViewPagerAdapter.
        viewPager2.adapter = adapter

        // Establece el offscreenPageLimit en 2 para mantener en memoria las páginas adyacentes.
        //viewPager2.offscreenPageLimit = 2

        //Agrega un listener a las pestañas del TabLayout para detectar cuando una pestaña se
        // selecciona, se deselecciona o se vuelve a seleccionar
        //// Conectar el TabLayout con el ViewPager2
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        // Registra un callback en el ViewPager2 para detectar cuándo cambia la página actual
        // OnPageChangeCallback: para seleccionar la pestaña correspondiente en el TabLayout cuando se cambia la página.
        //al cambiar las páginas del ViewPager2, el TabLayout se actualice correctamente y muestre la pestaña activa.
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }


}