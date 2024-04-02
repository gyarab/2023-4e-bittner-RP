package com.example.rp_2024

import android.os.Bundle
import com.example.rp_2024.databinding.ActivityDishesBinding

//aktivita hostující dish_nav a všechny odpovídající fragmenty
class DishesActivity : DrawerBaseActivity() {

    private lateinit var activityDishesBinding : ActivityDishesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDishesBinding = ActivityDishesBinding.inflate(layoutInflater)

        setContentView(activityDishesBinding.root)
        allocateActivityTitle("jídla")

    }
}

