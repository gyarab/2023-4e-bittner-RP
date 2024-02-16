package com.example.rp_2024

sealed interface PeopleEvent {
    data object AddPerson : PeopleEvent
}