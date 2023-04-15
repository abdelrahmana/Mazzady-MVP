package com.example.androidtask.repo

import android.content.Context
import com.example.androidtask.R
import com.example.androidtask.data.model.Data
import com.example.androidtask.data.model.DataProperty
import com.example.androidtask.data.model.GetAllCategoryResponse
import com.example.androidtask.data.source.remote.EndPoints
import com.example.androidtask.data.source.remote.repository.CategoryOptionsRepository
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CategoryOptionsRepositoryFakeTest () :
    CategoryOptionsRepository {
    override suspend fun getCategories( completion: (Data?, String?) -> Unit) {

            completion.invoke(Data(categories = ArrayList()),null)

    }

    override suspend fun getProperties(subCategoryId :Int,completion: (List<DataProperty?>?, String?) -> Unit) {
    }

    override suspend fun getSubProperties(optionId : Int,completion: (List<DataProperty?>?, String?) -> Unit) {


    }
}