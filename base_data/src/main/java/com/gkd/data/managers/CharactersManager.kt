package com.gkd.data.managers

import com.gkd.data.common.Result
import com.gkd.domain.entities.Persona
import kotlinx.coroutines.flow.Flow

/**
 * Created by Rim Gazzah on 8/28/20.
 **/
interface CharactersManager {
    fun getAllCharacters(): Flow<Result<List<Persona>>>
    fun searchCharacters(name: String): Flow<Result<List<Persona>>>
}