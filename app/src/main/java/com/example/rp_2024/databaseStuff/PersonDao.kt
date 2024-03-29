package com.example.rp_2024.databaseStuff

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface PersonDao {
    @Upsert
    suspend fun upsertPerson(person: Person)

    @Delete
    suspend fun deletePerson(person: Person)


    @Query("SELECT * FROM person ORDER BY name ASC")
    fun getOrderedByName(): LiveData<List<Person>>

    @Query("SELECT * FROM person ORDER BY surname ASC")
    fun getOrderedBySurname(): LiveData<List<Person>>
    @Query("SELECT * FROM person ORDER BY birthdate ASC")
    fun getOrderedByBirthdate(): LiveData<List<Person>>
}