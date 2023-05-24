package com.example.ksptest.viewmodels

import com.example.ksptest.data.PlantAndGardenPlantings
import java.text.SimpleDateFormat
import java.util.Locale

class PlantAndGardenPlantingViewModel ( plantings: PlantAndGardenPlantings) {

    private val plant = checkNotNull(plantings.plant)
    private val gardenPlanting = plantings.gardenPlantings[0]

    val waterDateString : String = dateFormat.format(gardenPlanting.lastWateringDate.time)

    val wateringInterval
        get() = plant.wateringInterval

    val imageUrl
        get() = plant.imageUrl

    val plantDateString : String = dateFormat.format(gardenPlanting.plantDate.time)

    val plantId
        get() = plant.plantId

    companion object {
        private val dateFormat = SimpleDateFormat("MM d, yyyy", Locale.US)
    }
}