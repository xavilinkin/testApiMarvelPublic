package com.example.mymarvelapp.domain

import com.example.mymarvelapp.data.MarvelRepository
import com.example.mymarvelapp.data.model.MarvelModel
import javax.inject.Inject

class GetCharacterMarvelUseCase @Inject constructor(private val repository: MarvelRepository) {

    suspend operator fun invoke(id: String): MarvelModel? {
        return repository.getCharacterMarvel(id)
    }
}