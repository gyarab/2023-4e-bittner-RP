package com.example.rp_2024

import androidx.lifecycle.ViewModel
import com.example.rp_2024.databaseStuff.PersonDao
import kotlinx.coroutines.flow.MutableStateFlow

class PeopleViewModel (
    private val dao: PersonDao
) : ViewModel()  {

    private val _state = MutableStateFlow(PeopleState())

    fun OnEvent(event: PeopleEvent){
        when(event){
            PeopleEvent.AddPerson -> {

            }
        }
    }
}