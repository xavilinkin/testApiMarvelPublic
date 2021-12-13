package com.example.mymarvelapp.data

import com.example.mymarvelapp.data.model.MarvelModel
import com.example.mymarvelapp.data.model.MarvelProvider
import com.example.mymarvelapp.data.network.MarvelService
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val api: MarvelService,
    private val provider: MarvelProvider
) {

    suspend fun getAllMarvel(): MarvelModel? {
        val response = api.getAllMarvel()
        provider.allMarvels = response
        return response
    }

    suspend fun getCharacterMarvel(id: String): MarvelModel? {
        val response = api.getCharacterMarvel(id)
        provider.characterMarvel = response
        return response
    }
}