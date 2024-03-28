package com.example.rp_2024.databaseStuff

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.runBlocking

@Database(
    entities = [Person::class, Ingredient::class, Dish::class, RecipeLine::class],
    version = 5,
    exportSchema = true,
)
abstract class MyDatabase: RoomDatabase() {
    abstract fun personDao(): MyDao



    //singleton
    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {

            val MIGRATION_1_2 = object : Migration(1, 2) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL("DROP TABLE Person")
                    db.execSQL("CREATE TABLE IF NOT EXISTS Person (" +
                            "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "`name` TEXT, `surname` TEXT, `status` INTEGER, `alias` TEXT, `birthdate` INTEGER, `fatherId` INTEGER, " +
                            "`motherId` INTEGER, `email` TEXT, `phoneNumber` TEXT, `note` TEXT)")
                }
            }
            val MIGRATION_2_3 = object : Migration(2, 3) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL("DROP TABLE Person")
                    db.execSQL("CREATE TABLE IF NOT EXISTS Person (" +
                            "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, " +
                            "`surname` TEXT NOT NULL, `status` INTEGER NOT NULL, `alias` TEXT NOT NULL, " +
                            "`birthdate` INTEGER NOT NULL, `fatherId` INTEGER NOT NULL, `motherId` INTEGER NOT NULL, " +
                            "`email` TEXT NOT NULL, `phoneNumber` TEXT NOT NULL, `note` TEXT NOT NULL)")
                }
            }
            val MIGRATION_3_4 = object : Migration(3, 4) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL("CREATE TABLE IF NOT EXISTS Ingredient (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL)")
                    db.execSQL("CREATE TABLE IF NOT EXISTS Dish (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL)")
                    db.execSQL("CREATE TABLE IF NOT EXISTS RecipeLine (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `dishId` INTEGER NOT NULL, " +
                            "`ingredientId` INTEGER NOT NULL, `amount` INTEGER NOT NULL, `measurement` TEXT NOT NULL)")
                }
            }



            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    MyDatabase::class.java, "person_database")
                    .addMigrations(MIGRATION_2_3, MIGRATION_1_2, MIGRATION_3_4)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        runBlocking {
                                val dao = INSTANCE?.personDao()
                                dao?.upsertPerson(Person(20, "alois", "jirásek", 0, "", 	323087057, -1, -1, "", "", "staré pověsti"))
                                dao?.upsertPerson(Person(21, "george", "orwell", 3, "", 		454584257, -1, -1, "", "541616158", ""))
                                dao?.upsertPerson(Person(22, "karel", "mácha", 2, "", 			959505857, -1, -1, "macha@com", "546496158", ""))
                                dao?.upsertPerson(Person(23, "božena", "němcová", 1, "", 			-618413743, -1, 24, "nemcova@bozena", "543185158", "babička"))
                                dao?.upsertPerson(Person(24, "Jana", "němcová", 4, "", 			-618413743, -1, -1, "jana@mail", "557465158", "babička"))
                        }

                    }
                }).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}