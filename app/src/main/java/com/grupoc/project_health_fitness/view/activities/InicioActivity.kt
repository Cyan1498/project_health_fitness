package com.grupoc.project_health_fitness.view.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.grupoc.project_health_fitness.MainActivity
import com.grupoc.project_health_fitness.R
import com.grupoc.project_health_fitness.databinding.ActivityInicioBinding
import com.grupoc.project_health_fitness.utils.interfaces.BottomNavVisibilityListener
import com.grupoc.project_health_fitness.view.fragments.ConsumoFragment
import com.grupoc.project_health_fitness.view.fragments.recordatorio.RecordListFragment

//import com.grupoc.project_health_fitness.view.fragments.recordatorio.RecordFormFragment22

//, BottomNavVisibilityListener
class InicioActivity : AppCompatActivity(){

//    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityInicioBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var FirebaseUser: FirebaseAuth
    private var showBackArrow: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        setSupportActionBar(binding.toolbar)
//        binding.navView.setNavigationItemSelectedListener(this)
        firebaseAuth = Firebase.auth
        FirebaseUser = FirebaseAuth.getInstance()

        val drawerLayout = binding.drawerLayout
        val navigationView = binding.navView
        val navController = findNavController(R.id.nav_fragment)
        val user = firebaseAuth.currentUser
        val email = user?.email

        navigationView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
//        toggle.isDrawerIndicatorEnabled = true // Mostrar el ícono del menú
        toggle.syncState()


//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)

        //Mostar el icon de menu en todos los fragments principales
        // (se puede comentar y funciona pero aparece la flecha levemente)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.resumenFragment) {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                toggle.isDrawerIndicatorEnabled = true
                toggle.syncState()
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toggle.isDrawerIndicatorEnabled = true  // Mostrar el ícono del menú
                toggle.syncState()
            }
        }


//        showUserInfo(email.toString(), user.toString())

//        navigationView.setNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.consumoFragment -> {
//                    openFragment(ConsumoFragment())
//                    true
//                }
//                R.id.recordatorioFragment -> {
//                    openFragment(RecordListFragment())
//                    true
//                }
//                R.id.nav_salir -> {
//                    showLogoutConfirmationDialog()
//                    true // Devuelve true para indicar que el evento se ha manejado correctamente
//                }
//                else -> false // Devuelve false si no se maneja el evento
//            }
//        }
//    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.consumoFragment -> openFragment(ConsumoFragment())
////                R.id.fitnessFragment -> openFragment(FitnessFragment())
////                R.id.recorridoFragment -> openFragment(RecorridoFragment())
//            R.id.recordatorioFragment -> openFragment(RecordFormFragment())
//            R.id.nav_salir -> {
//                showLogoutConfirmationDialog()
//            }
//        }
//        binding.drawerLayout.closeDrawers()
//        return true
//    }

//    private fun openFragment(fragment: Fragment) {
//        val fragmentManager = this.supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.nav_fragment, fragment)
//        fragmentTransaction.commit()
//        binding.drawerLayout.closeDrawers()
//    }
//
//    private fun logoutMenu() {
//        FirebaseUser.signOut()
//        startActivity(Intent(this, MainActivity::class.java))
//    }
//
//    private fun showLogoutConfirmationDialog() {
//        val alertDialog = AlertDialog.Builder(this)
//        alertDialog.setTitle("Confirmar salida")
//        alertDialog.setMessage("¿Estás seguro de que deseas salir?")
//        alertDialog.setPositiveButton("Sí") { _, _ ->
//            logoutMenu()
//        }
//        alertDialog.setNegativeButton("No", null)
//        alertDialog.show()
//    }

        //    private fun showUserInfo(email: String, user: String) {
//        val headerView = binding.navView.getHeaderView(0)
//        val tvemail = headerView.findViewById<TextView>(R.id.tv_email_menu)
//        val tvuser = headerView.findViewById<TextView>(R.id.tv_user_menu)
//        tvemail.text = email
//        tvuser.text = user
//    }
    }

    private fun setupBottomNavigation() {
        // Configurar Navigation Bottom
        val bottomNavController = findNavController(R.id.nav_fragment)
        val bottomNavigationView = binding.bottomNavigationView
        //Vincula automáticamente el controlador de navegación con la vista del BottomNavigationView,
        //estableciendo la navegación y la selección de elementos de manera coherente.
        bottomNavigationView.setupWithNavController(bottomNavController)
    }



    //Mostar el icono de las flechas en el los fragments secundarios
    fun setShowBackArrow(showBackArrow: Boolean) {
        this.showBackArrow = showBackArrow
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackArrow)
        toggle.isDrawerIndicatorEnabled = !showBackArrow

        if (showBackArrow) {
            toggle.toolbarNavigationClickListener = View.OnClickListener {
                onBackPressed()
            }
        } else {
            toggle.toolbarNavigationClickListener = null
        }

        toggle.syncState()
    }





    //Ocultar o mostrar el bottom navigation
    fun setBottomNavigationVisible(visible: Boolean) {
        if (visible) {
            binding.bottomNavigationView.visibility = View.VISIBLE
        } else {
            binding.bottomNavigationView.visibility = View.GONE
        }
    }

    //Mostrar o ocultar la navegacion lateral
    fun setDrawerEnabled(enabled: Boolean) {
        if (enabled) {
            toggle.isDrawerIndicatorEnabled = true
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        } else {
            toggle.isDrawerIndicatorEnabled = false
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
        toggle.syncState()
    }

}