package com.example.mymarvelapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymarvelapp.data.model.MarvelModel
import com.example.mymarvelapp.domain.GetCharacterMarvelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterMarvelViewModel @Inject constructor(var getCharacter: GetCharacterMarvelUseCase) :
    ViewModel() {

    var mutableGetCharacter = MutableLiveData<MarvelModel>()

    fun onCreate(id: String) {
        viewModelScope.launch {
            mutableGetCharacter.postValue(getCharacter(id))
        }
    }
}