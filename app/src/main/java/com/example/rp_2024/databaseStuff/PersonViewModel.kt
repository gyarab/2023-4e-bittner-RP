package com.example.rp_2024.databaseStuff

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PersonViewModel(application: Application): AndroidViewModel(application){
    private val getOrderedByName: LiveData<List<Person>>
    private val getOrderedBySurname: LiveData<List<Person>>
    private val getOrderedByBirthdate: LiveData<List<Person>>
    private val repository: PersonRepository
    init{
        val personDao = PersonDatabase.getDatabase(application).personDao()
        repository = PersonRepository(personDao)
        getOrderedByName = repository.getOrderedByName
        getOrderedBySurname = repository.getOrderedBySurname
        getOrderedByBirthdate = repository.getOrderedByBirthdate
    }

    fun upsertPerson(person: Person){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.upsertPerson(person)
        }    }
}