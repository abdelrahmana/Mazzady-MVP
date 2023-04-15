package com.example.androidtask.data.source.remote.repository

import android.content.Context
import com.example.androidtask.R
import com.example.androidtask.data.model.Data
import com.example.androidtask.data.model.DataProperty
import com.example.androidtask.data.model.GetAllCategoryResponse
import com.example.androidtask.data.source.remote.EndPoints
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CategoryOptionsRepositoryImplementer @Inject constructor(val endPoints: EndPoints,@ApplicationContext val context : Context) : CategoryOptionsRepository{
    override suspend fun getCategories( completion: (Data?, String?) -> Unit) {
       val response =  endPoints.getAllCategories()
        response.onSuccess {
            completion.invoke(data?.data,null)
        }
        response.onException {
            completion.invoke(null,context.getString(R.string.error_happend))
        }
        response.onError {
            completion.invoke(null,context.getString(R.string.error_happend))

        }
    }

    override suspend fun getProperties(subCategoryId :Int,completion: (List<DataProperty?>?, String?) -> Unit) {
        val response =  endPoints.getProperties(subCategoryId)
        response.onSuccess {
            completion.invoke(data?.data,null)
        }
        response.onException {
            completion.invoke(null,context.getString(R.string.error_happend))
        }
        response.onError {
            completion.invoke(null,context.getString(R.string.error_happend))

        }
    }

    override suspend fun getSubProperties(optionId : Int,completion: (List<DataProperty?>?, String?) -> Unit) {
        val response =  endPoints.getOptions(optionId)
        response.onSuccess {
            completion.invoke(data?.data,null)
        }
        response.onException {
            completion.invoke(null,context.getString(R.string.error_happend))
        }
        response.onError {
            completion.invoke(null,context.getString(R.string.error_happend))

        }

    }
}