package com.example.rp_2024.databaseStuff

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//v tomto souboru jsou definovány všechny entity Room databáze, a tedy i tabulky SQLite databáze pod ní
@Entity @Parcelize
data class Person(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var surname: String,
    var status: Int,    //0 - díťe, 1 - instruktor, 2 - vedoucí, 3 - kmeňák, 4 - rodič
    var alias: String,
    var birthdate: Long,
    var fatherId: Int,
    var motherId: Int,
    var email: String,
    var phoneNumber: String,
    var note: String,
    var isic: Boolean
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

@Entity @Parcelize
data class Event(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var start: Long,
    var end: Long,
    var adultId: Int,
    var adminId: Int,
    var note: String
): Parcelable{}

@Entity @Parcelize
data class EventAttendance(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var eventId: Int,
    var personId: Int,
    var atends: Boolean
    ): Parcelable{}

@Entity @Parcelize
data class EventShoppingLine(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var eventId: Int,
    var ingredientId: Int,
    var measurement: String,
    var amount: Int,
    var bought: Boolean
): Parcelable{}

@Entity @Parcelize
data class EventDish(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var eventId: Int,
    var dishId: Int,
): Parcelable{}



