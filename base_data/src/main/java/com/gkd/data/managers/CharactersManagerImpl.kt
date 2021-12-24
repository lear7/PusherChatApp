package com.gkd.data.managers

import com.gkd.data.services.ApiService
import com.gkd.data.common.CallErrors
import com.simple.domain.dto.toModel
import com.simple.domain.entities.Persona
import kotlinx.coroutines.flow.Flow
import com.gkd.data.common.Result
import kotlinx.coroutines.flow.flow

/**
 * Created by Rim Gazzah on 8/28/20.
 **/
class CharactersManagerImpl(private val api: ApiService) : CharactersManager {
    override fun getAllCharacters(): Flow<Result<List<Persona>>> = flow {
        api.getAllCharacters().run {
            if (this.isSuccessful) {
                if (this.body() == null) {
                    emit(Result.Error(CallErrors.ErrorEmptyData))
                } else {
                    emit(Result.Success(this.body()!!.results.toModel()))
                }
            } else {
                emit(Result.Error(CallErrors.ErrorServer))
            }
        }
    }.applyCommonSideEffects().catch {
        emit(Result.Error(CallErrors.ErrorException(it)))
    }

    override fun searchCharacters(name: String): Flow<Result<List<Persona>>> = flow {
        api.searchCharacterByName(name).run {
            if (this.isSuccessful) {
                if (this.body() == null ) {
                    emit(Result.Error(CallErrors.ErrorEmptyData))
                } else {
                    emit(Result.Success(this.body()!!.results.toModel()))
                }
            } else {
                emit(Result.Error(CallErrors.ErrorServer))
            }
        }
    }.applyCommonSideEffects().catch {
        emit(Result.Error(CallErrors.ErrorException(it)))
    }
}

//TODO : GET RESPONSE GENERIC FUNCTION
