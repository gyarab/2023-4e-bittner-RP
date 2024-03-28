package com.example.rp_2024.databaseStuff

import androidx.lifecycle.LiveData

class MyRepository(private val myDao: MyDao) {

    val getAllOrderedByName: LiveData<List<Person>> = myDao.getAllOrderedByName()
    val getAllOrderedBySurname: LiveData<List<Person>> = myDao.getAllOrderedBySurname()
    val getAllOrderedByAge: LiveData<List<Person>> = myDao.getAllOrderedByAge()
    val getAll: List<Person> = myDao.getAll()
    val getIngredientsOrderedByName: LiveData<List<Ingredient>> = myDao.getIngredientsOrderedByName()

    suspend fun upsertPerson (person:Person){
        myDao.upsertPerson(person)
    }

    suspend fun upsertIngredient(ingredient: Ingredient){
        myDao.upsertIngredient(ingredient)
    }

    suspend fun deletePerson(person: Person){
        myDao.deletePerson(person)
    }

    suspend fun deleteIngredient(ingredient: Ingredient){
        myDao.deleteIngredient(ingredient)
    }

    fun getByNameAndSurname (n: String, sn: String) : List<Person>{
        return myDao.getByNameAndSurname(n, sn)
    }



    fun getPerson (id: Int): Person{
        return myDao.getPerson(id)
    }

}