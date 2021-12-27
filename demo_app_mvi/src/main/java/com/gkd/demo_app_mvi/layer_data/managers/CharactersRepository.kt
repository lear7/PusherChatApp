package com.gkd.demo_app_mvi.layer_data.managers

import com.gkd.demo_app_mvi.layer_data.common.DataResult
import com.gkd.domain.entities.Persona
import kotlinx.coroutines.flow.Flow

/**
 * Created by Rim Gazzah on 8/28/20.
 **/
interface CharactersRepository {
    fun getAllCharacters(): Flow<DataResult<List<Persona>>>
    fun searchCharacters(name: String): Flow<DataResult<List<Persona>>>
}