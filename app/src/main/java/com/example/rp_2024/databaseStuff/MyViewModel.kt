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
    val getIngredientsOrderedByNameLive: LiveData<List<Ingredient>>
    val getIngredientsOrderedByName: List<Ingredient>

    val getDishesOrderedByName: LiveData<List<Dish>>
    init{
        val personDao = MyDatabase.getDatabase(application).personDao()
        repository = MyRepository(personDao)
        getAllOrderedByName = repository.getAllOrderedByName
        getAllOrderedBySurname = repository.getAllOrderedBySurname
        getAllOrderedByAge = repository.getAllOrderedByAge
        getAll = repository.getAll
        getIngredientsOrderedByNameLive = repository.getIngredientsOrderedByNameLive
        getIngredientsOrderedByName = repository.getIngredientsOrderedByName
        getDishesOrderedByName = repository.getDishesOrderedByName
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

    fun upsertDish(dish: Dish){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.upsertDish(dish)
        }
    }

    fun upsertRecipeLine(line: RecipeLine){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.upsertRecipeLine(line)
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

    fun deleteDish(dish: Dish){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteDish(dish)
        }
    }

    fun deleteRecipeLine(line: RecipeLine){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteRecipeLine(line)
        }
    }

    fun getByNameAndSurname (n: String, sn: String) : List<Person>{
        return repository.getByNameAndSurname(n, sn)
    }
    fun getPerson(id: Int): Person{
        return repository.getPerson(id)
    }

    fun getIngredient(id: Int): Ingredient{
        return repository.getIngredient(id)
    }

    fun getRecipeLinesForDishByNameLive(dishId: Int): LiveData<List<RecipeLine>>{
        return repository.getRecipeLinesForDishByNameLive(dishId)
    }

    fun getRecipeLinesForDishByName(dishId: Int): List<RecipeLine>{
        return repository.getRecipeLinesForDishByName(dishId)
    }
}