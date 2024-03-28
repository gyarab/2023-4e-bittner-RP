package com.example.rp_2024.databaseStuff

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyViewModel(application: Application): AndroidViewModel(application){
    val getAllOrderedByName: LiveData<List<Person>>
    val getAllOrderedBySurname: LiveData<List<Person>>
    val getAllOrderedByAge: LiveData<List<Person>>
    val repository: MyRepository
    val getAll: List<Person>
    val getIngredientsOrderedByName: LiveData<List<Ingredient>>
    init{
        val personDao = MyDatabase.getDatabase(application).personDao()
        repository = MyRepository(personDao)
        getAllOrderedByName = repository.getAllOrderedByName
        getAllOrderedBySurname = repository.getAllOrderedBySurname
        getAllOrderedByAge = repository.getAllOrderedByAge
        getAll = repository.getAll
        getIngredientsOrderedByName = repository.getIngredientsOrderedByName
    }

    fun upsertPerson(person: Person){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.upsertPerson(person)
        }
    }

    fun upsertIngredient(ingredient: Ingredient){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.upsertIngredient(ingredient)
        }
    }

    fun deletePerson(person: Person){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deletePerson(person)
        }
    }

    fun deleteIngredient(ingredient: Ingredient){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteIngredient(ingredient)
        }
    }

    fun getByNameAndSurname (n: String, sn: String) : List<Person>{
        return repository.getByNameAndSurname(n, sn)
    }
    fun getPerson (id: Int): Person{
        return repository.getPerson(id)
    }
}