package com.example.rp_2024.databaseStuff

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import java.nio.charset.StandardCharsets

//v anotaci jsou definovány entity databáze, verze a jestli se má exportovat schéma
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


        //inicializace databáze jako singleton
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
                    .allowMainThreadQueries()   //potenciálně zhoršuje běh aplikace, ale při zanedbatelné velikosti dat, se kterými pracuje moje aplikace to je jedno
                    .fallbackToDestructiveMigration()
                    //.openHelperFactory(factory) //mělo by přidávat šifrování databáze, ale zpúsobí že je databáze pořát zavřená a když se aplikace pokusí nahrát data, tak spadne
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}