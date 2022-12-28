package com.gameloop.laboratorioclinicoproc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.gameloop.laboratorioclinicoproc.database.dao.LabTestCategoryDao
import com.gameloop.laboratorioclinicoproc.database.dao.PatientDao
import com.gameloop.laboratorioclinicoproc.database.model.labtestcategory.LabTestCategory
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient

@Database(entities = [Patient::class, LabTestCategory::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LabDatabase : RoomDatabase() {
    abstract val patients: PatientDao
    abstract val labTestCategories: LabTestCategoryDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: LabDatabase

        fun getInstance(context: Context): LabDatabase {
            synchronized(this) {
                return if (::INSTANCE.isInitialized) {
                    INSTANCE
                } else {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LabDatabase::class.java,
                        "lab"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE
                }
            }
        }
    }
}