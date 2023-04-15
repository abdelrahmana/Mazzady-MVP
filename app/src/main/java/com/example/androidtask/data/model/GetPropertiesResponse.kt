package com.example.androidtask.data.model

data class GetPropertiesResponse(
    val code: Int?,
    val `data`: List<DataProperty?>?,
    val msg: String?
)

data class DataProperty(
    val description: String?,
    val id: Int?,
    val list: Boolean?,
    val name: String?,
    val options: List<Option?>?,
    var other_value: String?,
    val parent: Int?,
    val slug: String?,
    val type: String?,
    var value: String?,
    var selectedOption : Option?=null
)

data class Option(
    val child: Boolean?,
    val id: Int?,
    val name: String?,
    val parent: Int?,
    val slug: String?,
    var parentSlug : String?,
    var `data`: ArrayList<DataProperty?>? // inner subitems

)