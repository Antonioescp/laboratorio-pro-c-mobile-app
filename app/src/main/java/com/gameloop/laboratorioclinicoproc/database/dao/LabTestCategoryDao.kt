package com.gameloop.laboratorioclinicoproc.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gameloop.laboratorioclinicoproc.database.model.labtestcategory.LabTestCategory

@Dao
interface LabTestCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newCategory: LabTestCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newCategories: List<LabTestCategory>)

    @Query("SELECT * FROM lab_test_category")
    fun getAll(): LiveData<List<LabTestCategory>>
}