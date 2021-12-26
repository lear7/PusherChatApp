package com.gkd.data.services

import com.gkd.domain.dto.ECharacters
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by Rim Gazzah on 8/19/20.
 **/
interface CharacterService {

    @GET
    suspend fun getAllCharacters(@Url url: String): Response<ECharacters>

    @GET
    suspend fun searchCharacterByName(
        @Url url: String,
        @Query(KEY_NAME) name: String
    ): Response<ECharacters>

}