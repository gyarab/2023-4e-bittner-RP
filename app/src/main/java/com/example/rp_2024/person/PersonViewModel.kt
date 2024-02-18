package com.example.rp_2024.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rp_2024.databaseStuff.Person
import com.example.rp_2024.databaseStuff.PersonDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonViewModel(
    private val dao: PersonDao
) : ViewModel() {

    private val _state = MutableStateFlow(PersonState())

    fun onEvent(event: PersonEvent){
        when(event){
            PersonEvent.SavePerson -> {
                val name = _state.value.name
                val surname = _state.value.surname
                val birthdate = _state.value.birthdate
                val swimmer = _state.value.swimmer

                if(name.isBlank() || surname.isBlank()){
                    return
                }

                val person = Person(
                    name = name,
                    surname = surname,
                    birthdate = birthdate,
                    swimmer = swimmer
                )
                viewModelScope.launch {
                    dao.upsertPerson(person)
                }
                _state.update { it.copy(
                    name = "",
                    surname = "",
                    birthdate = "",
                    swimmer = false
                ) }
            }
            is PersonEvent.SetBirthdate -> {
                _state.update { it.copy(
                    birthdate = event.birthdate
                ) }
            }
            is PersonEvent.SetName -> {
                _state.update { it.copy(
                    name = event.name
                ) }
            }
            is PersonEvent.SetSurname -> {
                _state.update { it.copy(
                    surname = event.surname
                ) }
            }
            is PersonEvent.SetSwimmer -> {
                _state.update { it.copy(
                    swimmer = event.swimmer
                ) }
            }
        }
    }
}