package com.example.ksptest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ksptest.data.Plant
import com.example.ksptest.data.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantListViewModel @Inject constructor(
    plantRepository: PlantRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val growZone : MutableStateFlow<Int> = MutableStateFlow(
        savedStateHandle[GROW_ZONE_SAVED_STATE_KEY] ?: NO_GROW_ZONE
    )

    val plants: LiveData<List<Plant>> = growZone.flatMapLatest {zone->
        if (zone == NO_GROW_ZONE) {
            plantRepository.getPlants()
        }else {
            plantRepository.getPlantsWithGrowZoneNumber(zone)
        }
    }.asLiveData()

    init {
        viewModelScope.launch {
            if (isFiltered()) {
                clearGrowZoneNumber()
            }else {
                setGrowZoneNumber(9)
            }
        }
    }

    fun setGrowZoneNumber(num:Int) {
        growZone.value = num
    }

    fun clearGrowZoneNumber(){
        growZone.value = NO_GROW_ZONE
    }

    fun isFiltered() = growZone.value != NO_GROW_ZONE


    companion object {
        private const val NO_GROW_ZONE = -1
        private const val GROW_ZONE_SAVED_STATE_KEY = "GROW_ZONE_SAVED_STATE_KEY"
    }
}