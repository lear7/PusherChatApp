package com.gkd.data.managers

import com.gkd.data.common.CallErrors
import com.gkd.data.common.DataResult
import com.gkd.data.common.applyCommonSideEffects
import com.gkd.data.services.CharacterService
import com.gkd.domain.dto.toModel
import com.gkd.domain.entities.Persona
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Created by Rim Gazzah on 8/28/20.
 **/
class CharactersRepositoryImpl constructor(private val characterApi: CharacterService) :
    CharactersRepository {

    override fun getAllCharacters(): Flow<DataResult<List<Persona>>> = flow {
        characterApi.getAllCharacters().run {
            if (this.isSuccessful) {
                if (this.body() == null) {
                    emit(DataResult.Error(CallErrors.ErrorEmptyData))
                } else {
                    emit(DataResult.Success(this.body()!!.results.toModel()))
                }
            } else {
                emit(DataResult.Error(CallErrors.ErrorServer))
            }
        }
    }.applyCommonSideEffects().catch {
        emit(DataResult.Error(CallErrors.ErrorException(it)))
    }

    override fun searchCharacters(name: String): Flow<DataResult<List<Persona>>> = flow {
        characterApi.searchCharacterByName(name).run {
            if (this.isSuccessful) {
                if (this.body() == null) {
                    emit(DataResult.Error(CallErrors.ErrorEmptyData))
                } else {
                    emit(DataResult.Success(this.body()!!.results.toModel()))
                }
            } else {
                emit(DataResult.Error(CallErrors.ErrorServer))
            }
        }
    }.applyCommonSideEffects().catch {
        emit(DataResult.Error(CallErrors.ErrorException(it)))
    }
}