package com.example.androidtask.data.source.remote

import com.example.androidtask.data.model.GetAllCategoryResponse
import com.example.androidtask.data.model.GetPropertiesResponse
import com.example.androidtask.data.source.remote.Constants.CAT
import com.example.androidtask.data.source.remote.Constants.GETALLCAT
import com.example.androidtask.data.source.remote.Constants.GETOPTIONS
import com.example.androidtask.data.source.remote.Constants.GETPROPERTIES
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EndPoints {
    @GET(GETALLCAT)
    suspend fun getAllCategories(
    ): ApiResponse<GetAllCategoryResponse>

    @GET(GETPROPERTIES)
    suspend fun getProperties(@Query(CAT)categoryId : Int
    ): ApiResponse<GetPropertiesResponse>

    @GET(GETOPTIONS+"/{id}")
    suspend fun getOptions(@Path("id") id: Int
    ): ApiResponse<GetPropertiesResponse>
}