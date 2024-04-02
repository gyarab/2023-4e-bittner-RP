package com.example.rp_2024

import android.os.Bundle
import com.example.rp_2024.databinding.ActivityEventsBinding

//aktivita hostující event_nav a všechny související fragmenty
class EventsActivity : DrawerBaseActivity() {

    private lateinit var activityEventsBinding : ActivityEventsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEventsBinding = ActivityEventsBinding.inflate(layoutInflater)

        setContentView(activityEventsBinding.root)
        allocateActivityTitle("výpravy")

    }
}

