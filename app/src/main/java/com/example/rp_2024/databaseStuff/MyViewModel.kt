package com.example.rp_2024.databaseStuff

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//volá metody MyRepository pro přístup k databázi
//pro suspend metody vytváří coroutine scope, umožňuje tím efektivnější ukládání a mazání, prakticky běží ve vedlejšším vlákně
class MyViewModel(application: Application): AndroidViewModel(application){
    val getAllOrderedByName: LiveData<List<Person>>
    val getAllOrderedBySurname: LiveData<List<Person>>
    val getAllOrderedByAge: LiveData<List<Person>>
    val repository: MyRepository
    val getAll: List<Person>
    val getIngredientsOrderedByNameLive: LiveData<List<Ingredient>>
    val getIngredientsOrderedByName: List<Ingredient>
    val getEventsOrderedByDateLive: LiveData<List<Event>>
    val getDishesOrderedByNameLive: LiveData<List<Dish>>
    val getAdultsAndInstructors: List<Person>
    val getAdults: List<Person>
    val getDishesOrderedByName: List<Dish>

    init{
        val personDao = MyDatabase.getDatabase(application).dao()
        repository = MyRepository(personDao)
        getAllOrderedByName = repository.getAllOrderedByName
        getAllOrderedBySurname = repository.getAllOrderedBySurname
        getAllOrderedByAge = repository.getAllOrderedByAge
        getAll = repository.getAll
        getIngredientsOrderedByNameLive = repository.getIngredientsOrderedByNameLive
        getIngredientsOrderedByName = repository.getIngredientsOrderedByName
        getDishesOrderedByNameLive = repository.getDishesOrderedByNameLive
        getDishesOrderedByName = repository.getDishesOrderedByName
        getEventsOrderedByDateLive = repository.getEventsOrderedByDateLive
        getAdultsAndInstructors = repository.getAdultsAndInstruktors
        getAdults = repository.getAdults

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


    fun upsertEvent(event: Event){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.upsertEvent(event)
        }
    }

    fun deleteEvent(event: Event){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteEvent(event)
        }
    }

    fun upsertEventAttendance(attendance: EventAttendance){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.upsertEventAttendance(attendance)
        }
    }
    fun deleteEventAttendance(attendance: EventAttendance){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteEventAttendance(attendance)
        }
    }

    fun upsertEventDish(dish: EventDish){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.upsertEventDish(dish)
        }
    }

    fun deleteEventDish(dish: EventDish){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteEventDish(dish)
        }
    }

    fun upsertEventShoppingLine(line: EventShoppingLine){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.upsertEventShoppingLine(line)
        }
    }

    fun deleteEventShoppingLine(line: EventShoppingLine){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteEventShoppingLine(line)
        }
    }
    fun deleteEventShoppingLines(id: Int) {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteEventShoppingLines(id)
        }
    }

    fun getByNameAndSurname (n: String, sn: String) : List<Person>{
        return repository.getByNameAndSurname(n, sn)
    }
    fun getPerson(id: Int): Person{
        return repository.getPerson(id)
    }

    fun getPersonLive(id: Int): LiveData<Person?>{
        return repository.getPersonLive(id)
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

    fun getEventDishesForEvent(id: Int): List<EventDish> {
        return repository.getEventDishesForEvent(id)
    }

    fun getAttendanceForEvent(id: Int): List<EventAttendance> {
        return repository.getAttendanceForEvent(id)
    }

    fun getShoppingLinesForEventLive(id: Int): LiveData<List<EventShoppingLine>> {
        return repository.getShoppingLinesForEventLive(id)
    }
    fun getShoppingLinesForEvent(id: Int): List<EventShoppingLine> {
        return repository.getShoppingLinesForEvent(id)
    }
    fun getAttendanceOrderedByAgeLive(eventId: Int): LiveData<Map< Person, EventAttendance?>>{
        return repository.getAttendanceOrderedByAgeLive(eventId)
    }

    fun getChildrenCountForEvent(id: Int, unix: Long): Int{
        return repository.getChildrenCountForEvent(id, unix)
    }

    fun getStudentCountForEvent(id: Int, unix: Long): Int{
        return repository.getStudentCountForEvent(id, unix)
    }

    fun getAdultCountForEvent(id: Int, unix: Long): Int{
        return repository.getAdultCountForEvent(id, unix)
    }

    fun getEventDishes(id: Int): LiveData<Map<EventDish, Dish>>{
        return repository.getEventDishes(id)
    }

    fun getForEventShopping(id: Int): List<RecipeLine>{
        return repository.getForEventShopping(id)
    }

    fun getEventAttendanceCount(id: Int): Int {
        return repository.getEventAttendanceCount(id)
    }


}