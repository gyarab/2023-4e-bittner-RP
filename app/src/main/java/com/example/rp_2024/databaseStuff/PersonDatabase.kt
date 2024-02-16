package com.example.rp_2024.databaseStuff

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Person::class],
    version = 1
)
abstract class PersonDatabase: RoomDatabase() {
    abstract val dao: PersonDao
}