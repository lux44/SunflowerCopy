package com.example.ksptest.data.repository

import com.example.ksptest.data.dao.PlantDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlantRepository @Inject constructor(private val plantDao: PlantDao){

    fun getPlants() = plantDao.getPlants()

    fun getPlant(plantId:String) = plantDao.getPlant(plantId)

    fun getPlantsWithGrowZoneNumber(growZoneNumber:Int)
        = plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)

    companion object {
        @Volatile private var  instance: PlantRepository? = null

        fun getInstance(plantDao: PlantDao) =
            instance ?: synchronized(this) {
                instance ?: PlantRepository(plantDao).also { instance = it }
            }
    }
}