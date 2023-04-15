package com.example.androidtask.data.model
// for the popup bottom sheet data
class MiddlePropertiesCategories (
    val circle_icon: String?=null,
    val description: String?=null,
    val disable_shipping: Int?=null,
    val id: Int? =null,
    val image: String?=null,
    val name: String?=null,
    val slug: String?=null,
    val parent:Int?=null,
    val child : Boolean?=null,
    var selected : Boolean = false, // to configure the selection
    var parentName : String? =null
)