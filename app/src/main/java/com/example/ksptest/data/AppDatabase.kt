package com.example.ksptest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.ksptest.data.dao.GardenPlantingDao
import com.example.ksptest.data.dao.PlantDao
import com.example.ksptest.utilities.DATABASE_NAME
import com.example.ksptest.utilities.PLANT_DATA_FILENAME
import com.example.ksptest.workers.SeedDatabaseWorker
import com.example.ksptest.workers.SeedDatabaseWorker.Companion.KEY_FILENAME

@Database(entities = [GardenPlanting::class, Plant::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun gardenPlantingDao(): GardenPlantingDao

    abstract fun plantDao(): PlantDao

    companion object {

        @Volatile private var instance : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase {
            return instance ?: synchronized<AppDatabase>(this){
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                                .setInputData(workDataOf(KEY_FILENAME to PLANT_DATA_FILENAME))
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                ).build()
        }
    }
}