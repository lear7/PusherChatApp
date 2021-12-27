package com.gkd.demo_app_mvi.layer_data.services

import com.gkd.domain.dto.ECharacters
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Rim Gazzah on 8/19/20.
 **/
interface CharacterService {

    @GET(PATH_CHARACTER)
    suspend fun getAllCharacters(): Response<ECharacters>

    @GET(PATH_CHARACTER)
    suspend fun searchCharacterByName(@Query(KEY_NAME) name: String): Response<ECharacters>

}