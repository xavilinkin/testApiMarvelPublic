package com.example.mymarvelapp.domain

import com.example.mymarvelapp.data.MarvelRepository
import com.example.mymarvelapp.data.model.MarvelModel
import javax.inject.Inject

class GetMarvelUseCase @Inject constructor(private val repository:MarvelRepository) {

    suspend operator fun invoke(): MarvelModel? {
        return repository.getAllMarvel()
    }
}