package com.example.rp_2024.people

sealed interface PeopleEvent {
    data object AddPerson : PeopleEvent
}