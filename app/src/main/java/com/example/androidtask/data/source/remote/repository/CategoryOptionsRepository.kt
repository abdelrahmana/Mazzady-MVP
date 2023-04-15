package com.example.androidtask.data.source.remote.repository

import com.example.androidtask.data.model.Data
import com.example.androidtask.data.model.DataProperty
import com.example.androidtask.data.model.GetAllCategoryResponse
import com.skydoves.sandwich.ApiResponse

interface CategoryOptionsRepository {
    suspend fun getCategories( completion: (Data?, String?) -> Unit)
    suspend fun getProperties( subCategoryId : Int,completion: (List<DataProperty?>?, String?) -> Unit)
    suspend fun getSubProperties(optionId : Int,completion: (List<DataProperty?>?, String?) -> Unit)

}