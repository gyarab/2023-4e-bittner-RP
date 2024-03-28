package com.example.rp_2024

import android.os.Bundle
import com.example.rp_2024.databinding.ActivityPeopleBinding

class PeopleActivity : DrawerBaseActivity() {

    private lateinit var activityPeopleBinding : ActivityPeopleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPeopleBinding = ActivityPeopleBinding.inflate(layoutInflater)

        setContentView(activityPeopleBinding.root)
        allocateActivityTitle("evidence členů")

    }
}

