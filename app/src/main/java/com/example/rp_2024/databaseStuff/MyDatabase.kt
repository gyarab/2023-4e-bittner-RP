package com.example.rp_2024.databaseStuff

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import java.nio.charset.StandardCharsets

@Database(
    entities = [Person::class, Ingredient::class, Dish::class,
        RecipeLine::class, Event::class, EventAttendance::class,
        EventDish::class, EventShoppingLine::class],
    version = 11,
    exportSchema = true,
)
abstract class MyDatabase: RoomDatabase() {
    abstract fun dao(): MyDao



    //singleton
    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                if(tempInstance.isOpen) {
                    return tempInstance
                }
            }

            System.loadLibrary("sqlcipher")
            val password = "Password1!"

            val factory = SupportOpenHelperFactory(password.toByteArray(StandardCharsets.UTF_8))

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    MyDatabase::class.java, "my_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    //.openHelperFactory(factory) //should add encryption, but doesn't work
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}