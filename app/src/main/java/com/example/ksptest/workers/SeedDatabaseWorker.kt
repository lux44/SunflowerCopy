package com.example.ksptest.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.ksptest.data.AppDatabase
import com.example.ksptest.data.Plant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeedDatabaseWorker(
    context:Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {


    companion object {
        private const val TAG = "SeedDatabaseWorker"
        const val KEY_FILENAME = "PLANT_DATA_FILENAME"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).use {inputStream->
                    JsonReader(inputStream.reader()).use {jsonReader-> 
                        val plantType = object : TypeToken<List<Plant>>() {}.type
                        val plantList: List<Plant> = Gson().fromJson(jsonReader, plantType)

                        val database = AppDatabase.getInstance(applicationContext)
                        database.plantDao().insertAll(plantList)

                        Result.success()
                    }
                }
            } else{
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        }catch (e:Exception){
            Log.e(TAG, "Error seeding database", e)
            Result.failure()
        }
    }
}