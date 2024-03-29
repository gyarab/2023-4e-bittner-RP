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

    @Upsert
    suspend fun upsertDish(dish: Dish)

    @Delete
    suspend fun deleteDish(dish: Dish)

    @Upsert
    suspend fun upsertRecipeLine(line: RecipeLine)

    @Delete
    suspend fun deleteRecipeLine(line: RecipeLine)

    @Query("SELECT * FROM dish ORDER BY name")
    fun getDishesOrderedByName(): LiveData<List<Dish>>

    @Query("SELECT * FROM RecipeLine WHERE dishId = :dishId ORDER BY amount DESC")
    fun getRecipeLinesForDishByNameLive(dishId: Int): LiveData<List<RecipeLine>>

    @Query("SELECT * FROM RecipeLine WHERE dishId = :dishId ORDER BY amount DESC")
    fun getRecipeLinesForDishByName(dishId: Int): List<RecipeLine>

    @Query("SELECT * FROM ingredient ORDER BY name")
    fun getIngredientsOrderedByNameLive(): LiveData<List<Ingredient>>

    @Query("SELECT * FROM ingredient ORDER BY name")
    fun getIngredientsOrderedByName(): List<Ingredient>

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

    @Query("SELECT * FROM ingredient WHERE id=:id")
    fun getIngredient(id: Int): Ingredient
}