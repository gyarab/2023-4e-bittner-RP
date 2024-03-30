package com.example.rp_2024

import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

open class DrawerBaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout



    override fun setContentView(view: View) {
        drawerLayout = layoutInflater.inflate(R.layout.activity_drawer_base, null) as DrawerLayout
        var container : FrameLayout = drawerLayout.findViewById(R.id.activityContainer)
        container.addView(view)
        super.setContentView(drawerLayout)

        var toolbar : Toolbar = drawerLayout.findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(toolbar)

        var navigationView:NavigationView = drawerLayout.findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        var toggle :ActionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.draw_open, R.string.draw_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }
    override fun onNavigationItemSelected(item: MenuItem) : Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.nav1 -> {
                startActivity(Intent(this, CaesarsCipherActivity::class.java).apply{})
                overridePendingTransition(0,0)
            }
            R.id.nav2 -> {
                startActivity(Intent(this, EventsActivity::class.java).apply{})
                overridePendingTransition(0,0)
            }
            R.id.nav3 -> {
                startActivity(Intent(this, IngredientsActivity::class.java).apply{})
                overridePendingTransition(0,0)
            }
            R.id.nav4 -> {
                startActivity(Intent(this, PeopleActivity::class.java).apply{})
                overridePendingTransition(0,0)
            }
            R.id.nav5 -> {
                startActivity(Intent(this, DishesActivity::class.java).apply{})
                overridePendingTransition(0,0)
            }
        }

        return false
    }

    fun allocateActivityTitle(title: String) {
        supportActionBar?.title = title

    }

}


