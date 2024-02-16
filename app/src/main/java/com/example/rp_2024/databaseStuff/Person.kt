package com.example.rp_2024.databaseStuff

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val surname: String,
    val birthdate: String,
    val swimmer: Boolean
)
