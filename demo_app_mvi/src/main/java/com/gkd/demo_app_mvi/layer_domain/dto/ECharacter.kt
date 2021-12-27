package com.gkd.domain.dto

import com.gkd.domain.entities.Persona
import com.gkd.domain.entities.Location
import com.gkd.domain.entities.Origin

data class ECharacter(
    val id: Int,
    val name: String,
    val species: String,
    val status: String,
    val gender: String,
    val image: String,
    val created: String,
    val episode: List<Any>,
    val location: Location,
    val origin: Origin,
    val type: String,
    val url: String
)

fun ECharacter.toModel() = Persona(id, name, species, status, gender, image)
fun List<ECharacter>.toModel() = map { it.toModel() }

