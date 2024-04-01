package com.example.rp_2024

import android.os.Bundle
import com.example.rp_2024.databinding.ActivityIngredientsBinding

class IngredientsActivity : DrawerBaseActivity() {

    private lateinit var activityIngredientsBinding : ActivityIngredientsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityIngredientsBinding = ActivityIngredientsBinding.inflate(layoutInflater)

        setContentView(activityIngredientsBinding.root)
        allocateActivityTitle("ingredience")

    }
}
