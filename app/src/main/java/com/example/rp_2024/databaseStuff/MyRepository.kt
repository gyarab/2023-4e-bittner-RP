package com.example.rp_2024.databaseStuff

import androidx.lifecycle.LiveData

//volá metody MyDao pro přístup k databázi
class MyRepository(private val myDao: MyDao) {


    val getAllOrderedByName: LiveData<List<Person>> = myDao.getAllOrderedByName()
    val getAllOrderedBySurname: LiveData<List<Person>> = myDao.getAllOrderedBySurname()
    val getAllOrderedByAge: LiveData<List<Person>> = myDao.getAllOrderedByAge()
    val getAll: List<Person> = myDao.getAll()
    val getIngredientsOrderedByNameLive: LiveData<List<Ingredient>> = myDao.getIngredientsOrderedByNameLive()
    val getDishesOrderedByNameLive: LiveData<List<Dish>> = myDao.getDishesOrderedByNameLive()
    val getDishesOrderedByName: List<Dish> = myDao.getDishesOrderedByName()
    val getIngredientsOrderedByName: List<Ingredient> = myDao.getIngredientsOrderedByName()
    val getEventsOrderedByDateLive: LiveData<List<Event>> = myDao.getEventsOrderedByDateLive()
    val getAdultsAndInstruktors: List<Person> = myDao.getAdultsAndInstruktors()
    val getAdults: List<Person> = myDao.getAdults()

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

    suspend fun upsertEvent(event: Event){
        myDao.upsertEvent(event)
    }

    suspend fun deleteEvent(event: Event){
        myDao.deleteEvent(event)
    }

    suspend fun upsertEventAttendance(attendance: EventAttendance){
        myDao.upsertEventAttendance(attendance)
    }

    suspend fun deleteEventAttendance(attendance: EventAttendance){
        myDao.deleteEventAttendance(attendance)
    }

    suspend fun upsertEventDish(dish: EventDish){
        myDao.upsertEventDish(dish)
    }

    suspend fun deleteEventDish(dish: EventDish){
        myDao.deleteEventDish(dish)
    }

    suspend fun upsertEventShoppingLine(line: EventShoppingLine){
        myDao.upsertEventShoppingLine(line)
    }

    suspend fun deleteEventShoppingLine(line: EventShoppingLine){
        myDao.deleteEventShoppingLine(line)
    }

    fun deleteEventShoppingLines(id: Int) {
        myDao.deleteEventShoppingLines(id)
    }

    fun getByNameAndSurname (n: String, sn: String) : List<Person>{
        return myDao.getByNameAndSurname(n, sn)
    }

    fun getRecipeLinesForDishByNameLive(dishId: Int): LiveData<List<RecipeLine>>{
        return myDao.getRecipeLinesForDishByNameLive(dishId)
    }

    fun getAttendanceOrderedByAgeLive(eventId: Int): LiveData<Map<Person, EventAttendance?>>{
        return myDao.getAttendanceOrderedByAgeLive(eventId)
    }

    fun getRecipeLinesForDishByName(dishId: Int): List<RecipeLine>{
        return myDao.getRecipeLinesForDishByName(dishId)
    }

    fun getPerson(id: Int): Person{
        return myDao.getPerson(id)
    }

    fun getPersonLive(id: Int): LiveData<Person?>{
        return myDao.getPersonLive(id)
    }

    fun getIngredient(id: Int): Ingredient{
        return myDao.getIngredient(id)
    }

    fun getEventDishesForEvent(id: Int): List<EventDish> {
        return myDao.getEventDishesForEvent(id)
    }

    fun getAttendanceForEvent(id: Int): List<EventAttendance> {
        return myDao.getAttendanceForEvent(id)
    }

    fun getShoppingLinesForEvent(id: Int): List<EventShoppingLine> {
        return myDao.getShoppingLinesForEvent(id)
    }
    fun getShoppingLinesForEventLive(id: Int): LiveData<List<EventShoppingLine>> {
        return myDao.getShoppingLinesForEventLive(id)
    }

    fun getChildrenCountForEvent(id: Int, unix: Long): Int{
        return myDao.getChildrenCountForEvent(id, unix)
    }

    fun getStudentCountForEvent(id: Int, unix: Long): Int{
        return myDao.getStudentCountForEvent(id, unix)
    }

    fun getAdultCountForEvent(id: Int, unix: Long): Int{
        return myDao.getAdultCountForEvent(id, unix)
    }

    fun getEventDishes(id: Int): LiveData<Map<EventDish, Dish>>{
        return myDao.getEventDishes(id)
    }

    fun getForEventShopping(id: Int): List<RecipeLine>{
        return myDao.getForEventShopping(id)
    }

    fun getEventAttendanceCount(id: Int): Int {
        return myDao.getEventAttendanceCount(id)
    }



}