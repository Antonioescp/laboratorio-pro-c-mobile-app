package com.gameloop.laboratorioclinicoproc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gameloop.laboratorioclinicoproc.database.dao.PatientDao
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient

@Database(entities = [Patient::class], version = 1, exportSchema = false)
abstract class LabDatabase : RoomDatabase() {
    abstract val patients: PatientDao

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
                    ).build()
                    INSTANCE
                }
            }
        }
    }
}