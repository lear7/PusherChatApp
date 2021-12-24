package com.gkd.data.managers

import com.gkd.data.common.DataResult
import com.gkd.domain.entities.Persona
import kotlinx.coroutines.flow.Flow
import javax.inject.Qualifier

/**
 * Created by Rim Gazzah on 8/28/20.
 **/
interface CharactersRepository {
    fun getAllCharacters(): Flow<DataResult<List<Persona>>>
    fun searchCharacters(name: String): Flow<DataResult<List<Persona>>>
}