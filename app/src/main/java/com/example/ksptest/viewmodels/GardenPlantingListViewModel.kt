package com.example.ksptest.viewmodels

import androidx.lifecycle.ViewModel
import com.example.ksptest.data.PlantAndGardenPlantings
import com.example.ksptest.data.repository.GardenPlantingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GardenPlantingListViewModel @Inject internal constructor(
    gardenPlantingRepository: GardenPlantingRepository
) : ViewModel(){
    val plantAndGardenPlanting : Flow<List<PlantAndGardenPlantings>> =
        gardenPlantingRepository.getPlantedGardens()
}