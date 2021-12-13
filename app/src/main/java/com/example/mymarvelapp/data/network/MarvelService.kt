package com.example.mymarvelapp.data.network

import com.example.mymarvelapp.constants.NetworkConstants
import com.example.mymarvelapp.data.model.MarvelModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarvelService @Inject constructor(private val api: MarvelApiClient) {

    suspend fun getAllMarvel(): MarvelModel? {
        return withContext(Dispatchers.IO) {
            val response = api.getAllMarvel(
                NetworkConstants.PATH + "ts=" + NetworkConstants.TS +
                        "&apikey=" + NetworkConstants.PUBLIC_API_KEY +
                        "&hash=" + NetworkConstants.HASH
            )
            if (response.isSuccessful) {
                response.body()
            } else {
                MarvelModel(response.code(), null)
            }
        }
    }

    suspend fun getCharacterMarvel(id: String): MarvelModel? {
        return withContext(Dispatchers.IO) {
            val response = api.getCharacterMarvel(
                NetworkConstants.PATH_CHARACTERS + id + "?ts=" + NetworkConstants.TS +
                        "&apikey=" + NetworkConstants.PUBLIC_API_KEY +
                        "&hash=" + NetworkConstants.HASH
            )
            if (response.isSuccessful) {
                response.body()
            } else {
                MarvelModel(response.code(), null)
            }
        }
    }
}