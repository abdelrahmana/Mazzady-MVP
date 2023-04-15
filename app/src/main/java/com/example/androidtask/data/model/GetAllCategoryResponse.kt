package com.example.androidtask.data.model

data class GetAllCategoryResponse(
    val code: Int?,
    val `data`: Data?,
    val msg: String?
)

data class Data(
    val ads_banners: List<AdsBanner?>?=null,
    val categories: List<Category?>?=null,
    val google_version: String?=null,
    val huawei_version: String?=null,
    val ios_version: String?=null,
    val statistics: Statistics?=null
)

data class AdsBanner(
    val duration: Int?,
    val img: String?,
    val media_type: String?
)

data class Category(
    val children: List<Children?>?,
    val circle_icon: String?,
    val description: String?,
    val disable_shipping: Int?,
    val id: Int?,
    val image: String?,
    val name: String?,
    val slug: String?,
    var isSelected : Boolean = false
)
data class Statistics(
    val auctions: Int?,
    val products: Int?,
    val users: Int?
)

data class Children(
    val children: Any?,
    val circle_icon: String?,
    val description: String?,
    val disable_shipping: Int?,
    val id: Int?,
    val image: String?,
    val name: String?,
    val slug: String?,
    var isSelected :Boolean? = false
)