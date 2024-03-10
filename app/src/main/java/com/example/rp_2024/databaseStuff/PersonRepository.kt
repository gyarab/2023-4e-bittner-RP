package com.example.rp_2024.databaseStuff

import androidx.lifecycle.LiveData

class PersonRepository(private val personDao: PersonDao) {

    val getOrderedByName: LiveData<List<Person>> = personDao.getOrderedByName()
    val getOrderedBySurname: LiveData<List<Person>> = personDao.getOrderedBySurname()
    val getOrderedByBirthdate: LiveData<List<Person>> = personDao.getOrderedByBirthdate()

    suspend fun upsertPerson (person:Person){
        personDao.upsertPerson(person)

    }
}