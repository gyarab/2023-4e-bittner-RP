package com.example.rp_2024.databaseStuff

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

//metody nejnižší úrovně sloužící k přístupu do databáze
@Dao
interface MyDao {

    //upsert přidá řádek, pokud v tabulce už není řádek se stejným primárním klíčem, pak ho updatuje
    @Upsert
    suspend fun upsertPerson(person: Person)

    //vymaže řádek, podle primary key
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

    @Upsert
    suspend fun upsertEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Upsert
    suspend fun upsertEventAttendance(attendance: EventAttendance)
    @Delete
    suspend fun deleteEventAttendance(attendance: EventAttendance)

    @Upsert
    suspend fun upsertEventDish(dish: EventDish)

    @Delete
    suspend fun deleteEventDish(dish: EventDish)

    @Upsert
    suspend fun upsertEventShoppingLine(line: EventShoppingLine)

    @Delete
    suspend fun deleteEventShoppingLine(line: EventShoppingLine)

    //provede SQLite querry
    @Query("DELETE from EventShoppingLine WHERE eventId=:id")
    abstract fun deleteEventShoppingLines(id: Int)

    //provede SQLite querry a vrátí výsledek v podobě Objektu, který má metoda jako návratovou hodnotu
    //správnost querry se kontroluje při kompilaci
    @Query("SELECT * FROM dish ORDER BY name")
    fun getDishesOrderedByNameLive(): LiveData<List<Dish>>

    @Query("SELECT * FROM dish ORDER BY name")
    fun getDishesOrderedByName(): List<Dish>

    @Query("SELECT * FROM RecipeLine WHERE dishId = :dishId ORDER BY amount DESC")
    fun getRecipeLinesForDishByNameLive(dishId: Int): LiveData<List<RecipeLine>>

    @Query("SELECT * FROM RecipeLine WHERE dishId = :dishId ORDER BY amount DESC")
    fun getRecipeLinesForDishByName(dishId: Int): List<RecipeLine>

    @Query("SELECT * FROM ingredient ORDER BY name")
    fun getIngredientsOrderedByNameLive(): LiveData<List<Ingredient>>

    @Query("SELECT * FROM ingredient ORDER BY name")
    fun getIngredientsOrderedByName(): List<Ingredient>

    //metody getAll... bez konkrétní entity v názvu vrací objekty Person, dělal jsem je první a pak jsem je nějak nepřejmenoval
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

    @Query("SELECT * FROM person WHERE id=:id")
    fun getPersonLive(id: Int): LiveData<Person?>

    @Query("SELECT * FROM person WHERE status=1 OR status=2 ORDER BY birthdate ASC")
    fun getAdultsAndInstruktors(): List<Person>

    @Query("SELECT * FROM person WHERE status=2 ORDER BY birthdate ASC")
    fun getAdults(): List<Person>

    @Query("SELECT * FROM ingredient WHERE id=:id")
    fun getIngredient(id: Int): Ingredient

    @Query("SELECT * FROM event ORDER BY start ASC")
    fun getEventsOrderedByDateLive(): LiveData<List<Event>>

    @Query("SELECT * FROM EventDish WHERE eventId=:id")
    fun getEventDishesForEvent(id: Int): List<EventDish>

    @Query("SELECT * FROM eventAttendance WHERE eventId=:id")
    fun getAttendanceForEvent(id: Int): List<EventAttendance>

    @Query("SELECT * FROM EventShoppingLine WHERE eventId=:id")
    fun getShoppingLinesForEventLive(id: Int): LiveData<List<EventShoppingLine>>

    @Query("SELECT * FROM EventShoppingLine WHERE eventId=:id")
    fun getShoppingLinesForEvent(id: Int): List<EventShoppingLine>

    @Query("WITH at AS (SELECT * FROM EventAttendance WHERE eventId=:id) SELECT * FROM Person LEFT JOIN at ON Person.id=at.personId WHERE Person.status<4 ORDER BY Person.birthdate ASC")
    fun getAttendanceOrderedByAgeLive(id: Int): LiveData<Map<Person, EventAttendance?>>

    @Query("WITH at AS (SELECT * FROM EventAttendance WHERE eventId=:id) SELECT COUNT(*) FROM at JOIN person ON at.personId=Person.id WHERE Person.birthdate>:unix AND at.atends=1")
    fun getChildrenCountForEvent(id: Int, unix: Long): Int  //unix je počet milisekund od epochy po datum před 18 lety
    @Query("WITH at AS (SELECT * FROM EventAttendance WHERE eventId=:id) SELECT COUNT(*) FROM at JOIN person ON at.personId=Person.id WHERE Person.birthdate<:unix AND Person.isic=1 AND at.atends=1")
    fun getStudentCountForEvent(id: Int, unix: Long): Int  //unix je počet milisekund od epochy po datum před 18 lety

    @Query("WITH at AS (SELECT * FROM EventAttendance WHERE eventId=:id) SELECT COUNT(*) FROM at JOIN person ON at.personId=Person.id WHERE Person.birthdate<:unix AND Person.isic!=1 AND at.atends=1")
    fun getAdultCountForEvent(id: Int, unix: Long): Int  //unix je počet milisekund od epochy po datum před 18 lety

    @Query("SELECT * FROM EventDish JOIN Dish ON EventDish.dishId=Dish.id WHERE EventDish.eventId=:id")
    fun getEventDishes(id: Int): LiveData<Map<EventDish, Dish>>

    @Query("SELECT RecipeLine.* FROM EventDish JOIN RecipeLine ON EventDish.dishId=RecipeLine.dishId WHERE EventDish.eventId=:id")
    fun getForEventShopping(id: Int): List<RecipeLine>
//.id, RecipeLine.dishId, RecipeLine.ingredientId, RecipeLine.amount, RecipeLine.measurement

    @Query("SELECT COUNT(*) FROM EventAttendance WHERE eventId=:id AND atends=1")
    fun getEventAttendanceCount(id: Int): Int

}