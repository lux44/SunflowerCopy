package com.example.ksptest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ksptest.BuildConfig
import com.example.ksptest.data.repository.GardenPlantingRepository
import com.example.ksptest.data.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    plantRepository: PlantRepository,
    private val gardenPlantingRepository: GardenPlantingRepository,
) : ViewModel(){

    val plantId: String = savedStateHandle.get<String>(PLANT_ID_SAVED_STATE_KEY)!!

    val isPlanted = gardenPlantingRepository.isPlanted(plantId).asLiveData()
    val plant = plantRepository.getPlant(plantId).asLiveData()

    private val _showSnackBar = MutableLiveData(false)
    val showSnackBar : LiveData<Boolean>
        get() = _showSnackBar

    fun addPlantToGarden(){
        viewModelScope.launch {
            gardenPlantingRepository.createGardenPlanting(plantId)
            _showSnackBar.value = true
        }
    }

    fun dismissSnackBar(){
        _showSnackBar.value = false
    }

    fun hasValidUnsplashKey() = BuildConfig.UNSPLASH_ACCESS_KEY != "null"

    companion object {
        private const val PLANT_ID_SAVED_STATE_KEY = "plantId"
    }
}