package com.example.androidtask.selectoption

import com.example.androidtask.data.model.MiddlePropertiesCategories
import com.example.androidtask.ui.detailsbids.DetailsFragment
import com.example.androidtask.ui.selectionbottomsheet.SelectionOptionBottomSheet
import org.junit.Before
import org.junit.Test


class DetailsTestMethods {
    lateinit var selectOption: SelectionOptionBottomSheet
    @Before
    fun `set user info`() {
        selectOption = SelectionOptionBottomSheet()
    }
    @Test
    fun `check List in search`(){
        val filteredList = ArrayList<MiddlePropertiesCategories>()
     val list =   selectOption.checkKeyWordWithOriginalList("hello",
         filteredList,ArrayList())
       val selectedObject :List<MiddlePropertiesCategories>? = filteredList.filter { it.name == "hello" }
        assert( filteredList.isNotEmpty())
    }
}