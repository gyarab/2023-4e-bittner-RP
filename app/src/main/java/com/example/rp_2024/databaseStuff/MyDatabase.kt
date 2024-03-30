package com.example.rp_2024.databaseStuff

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.runBlocking

@Database(
    entities = [Person::class, Ingredient::class, Dish::class, RecipeLine::class, Event::class, EventAttendance::class, EventDish::class, EventShoppingLine::class],
    version = 7,
    exportSchema = true,
)
abstract class MyDatabase: RoomDatabase() {
    abstract fun personDao(): MyDao



    //singleton
    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {




            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    MyDatabase::class.java, "person_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        runBlocking {
                                val dao = INSTANCE?.personDao()
                                dao?.upsertPerson(Person(20, "alois", "jirásek", 0, "", 	323087057, -1, -1, "", "", "staré pověsti", false))
                                dao?.upsertPerson(Person(21, "george", "orwell", 3, "", 		454584257, -1, -1, "", "541616158", "", false))
                                dao?.upsertPerson(Person(22, "karel", "mácha", 2, "", 			959505857, -1, -1, "macha@com", "546496158", "", false))
                                dao?.upsertPerson(Person(23, "božena", "němcová", 1, "", 			-618413743, -1, 24, "nemcova@bozena", "543185158", "babička", false))
                                dao?.upsertPerson(Person(24, "Jana", "němcová", 4, "", 			-618413743, -1, -1, "jana@mail", "557465158", "babička", false))
                        }

                    }
                }).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}