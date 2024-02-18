package com.example.rp_2024.person

sealed interface PersonEvent {
    data object SavePerson : PersonEvent
    data class SetName(val name: String): PersonEvent
    data class SetSurname(val surname: String): PersonEvent
    data class SetBirthdate(val birthdate: String): PersonEvent
    data class SetSwimmer(val swimmer: Boolean): PersonEvent
}