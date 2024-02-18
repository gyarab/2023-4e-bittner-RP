package com.example.rp_2024

import android.os.Bundle
import com.example.rp_2024.databinding.ActivityDashboardBinding

class DashboardActivity : DrawerBaseActivity() {

    private lateinit var activityDashboardBinding : ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)

        setContentView(activityDashboardBinding.root)
        allocateActivityTitle("any activity")
    }


}