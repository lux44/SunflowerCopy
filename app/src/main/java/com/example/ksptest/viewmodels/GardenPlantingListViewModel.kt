package com.example.ksptest.viewmodels

import androidx.lifecycle.ViewModel
import com.example.ksptest.data.PlantAndGardenPlantings
import com.example.ksptest.data.repository.GardenPlantingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
@HiltViewModel
class GardenPlantingListViewModel @Inject internal constructor(
    gardenPlantingRepository: GardenPlantingRepository
) : ViewModel(){
    val plantAndGardenPlantings : Flow<List<PlantAndGardenPlantings>> =
        gardenPlantingRepository.getPlantedGardens()
}