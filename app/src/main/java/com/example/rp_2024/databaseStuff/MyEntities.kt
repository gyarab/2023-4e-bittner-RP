package com.example.rp_2024.databaseStuff

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity @Parcelize
data class Person(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var surname: String,
    var status: Int,    //0 - dite, 1 - instruktor, 2 - vedouci, 3 - kmenak, 4 - rodic
    var alias: String,
    var birthdate: Long,
    var fatherId: Int,
    var motherId: Int,
    var email: String,
    var phoneNumber: String,
    var note: String
): Parcelable {
}

@Entity @Parcelize
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var note: String
): Parcelable{}

@Entity @Parcelize
data class Dish(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var note: String
): Parcelable{}

@Entity @Parcelize
data class RecipeLine(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var dishId: Int,
    var ingredientId: Int,
    var amount: Int,
    var measurement: String
): Parcelable{}

