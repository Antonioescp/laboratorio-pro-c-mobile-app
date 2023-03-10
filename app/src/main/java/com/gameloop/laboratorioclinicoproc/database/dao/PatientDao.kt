package com.gameloop.laboratorioclinicoproc.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(patient: Patient)

    @Update
    suspend fun update(patient: Patient)

    @Query("SELECT * FROM patients WHERE id = :id")
    suspend fun getById(id: Long): Patient?

    @Query("DELETE FROM patients WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM patients")
    fun getAll(): LiveData<List<Patient>>

    @Query("DELETE FROM patients")
    suspend fun clear()
}