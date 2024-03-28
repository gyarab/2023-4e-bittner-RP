package com.example.rp_2024.databaseStuff

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface MyDao {
    @Upsert
    suspend fun upsertPerson(person: Person)

    @Delete
    suspend fun deletePerson(person: Person)

    @Upsert
    suspend fun upsertIngredient(ingredient: Ingredient)

    @Delete
    suspend fun deleteIngredient(ingredient: Ingredient)

    @Query("SELECT * FROM ingredient ORDER BY name")
    fun getIngredientsOrderedByName(): LiveData<List<Ingredient>>

    @Query("SELECT * FROM person WHERE status<4 ORDER BY name ASC")
    fun getAllOrderedByName(): LiveData<List<Person>>

    @Query("SELECT * FROM person WHERE status<4 ORDER BY surname ASC")
    fun getAllOrderedBySurname(): LiveData<List<Person>>

    @Query("SELECT * FROM person WHERE status<4 ORDER BY birthdate ASC")
    fun getAllOrderedByAge(): LiveData<List<Person>>

    @Query("SELECT * FROM person")
    fun getAll(): List<Person>

    @Query("SELECT * From person WHERE name LIKE :n AND surname LIKE :sn ")
    fun getByNameAndSurname(n: String, sn: String): List<Person>

    @Query("SELECT * FROM person WHERE id=:id")
    fun getPerson(id: Int): Person
}