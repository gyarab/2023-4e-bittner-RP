package com.example.rp_2024.databaseStuff

import androidx.lifecycle.LiveData

class MyRepository(private val myDao: MyDao) {

    val getAllOrderedByName: LiveData<List<Person>> = myDao.getAllOrderedByName()
    val getAllOrderedBySurname: LiveData<List<Person>> = myDao.getAllOrderedBySurname()
    val getAllOrderedByAge: LiveData<List<Person>> = myDao.getAllOrderedByAge()
    val getAll: List<Person> = myDao.getAll()
    val getIngredientsOrderedByNameLive: LiveData<List<Ingredient>> = myDao.getIngredientsOrderedByNameLive()
    val getDishesOrderedByName: LiveData<List<Dish>> = myDao.getDishesOrderedByName()
    val getIngredientsOrderedByName: List<Ingredient> = myDao.getIngredientsOrderedByName()

    suspend fun upsertPerson (person:Person){
        myDao.upsertPerson(person)
    }

    suspend fun upsertDish(dish: Dish){
        myDao.upsertDish(dish)
    }

    suspend fun deleteDish(dish: Dish){
        myDao.deleteDish(dish)
    }

    suspend fun upsertRecipeLine(line: RecipeLine){
        myDao.upsertRecipeLine(line)
    }

    suspend fun deleteRecipeLine(line: RecipeLine){
        myDao.deleteRecipeLine(line)
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

    fun getRecipeLinesForDishByNameLive(dishId: Int): LiveData<List<RecipeLine>>{
        return myDao.getRecipeLinesForDishByNameLive(dishId)
    }

    fun getRecipeLinesForDishByName(dishId: Int): List<RecipeLine>{
        return myDao.getRecipeLinesForDishByName(dishId)
    }

    fun getPerson(id: Int): Person{
        return myDao.getPerson(id)
    }

    fun getIngredient(id: Int): Ingredient{
        return myDao.getIngredient(id)
    }

}