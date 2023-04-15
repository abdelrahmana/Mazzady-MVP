package com.example.androidtask.ui.selectionbottomsheet

import android.content.Context
import android.util.Log
import com.example.androidtask.R
import com.example.androidtask.data.model.*
import com.example.androidtask.ui.fillrequest.FillRequestViewModel

interface InterfaceSelection {
    fun setSelection(id :Int) // when select an item in adaptor
    fun getMiddleWareModel(): ArrayList<MiddlePropertiesCategories> // will be on adaptor
    fun getParentName(context :Context) : String

   /* fun getType() { // check what i'm

    }*/
  /*  fun getSelectedMiddleWare() : MiddlePropertiesCategories? {

    }*/

}

class CategoryImplementer(val viewModel: FillRequestViewModel,val categories : ArrayList<Category?>) :InterfaceSelection {
  // var selectedMiddleWare :Category?=null
    override fun setSelection(id: Int) {
      var selectedCategory : Category? =null
        categories.forEach{
            if (it?.id == id) {
                it.isSelected = true
                selectedCategory = it
            }
            else
                it?.isSelected = false
        }
      viewModel.setSelectedCategory(selectedCategory)
        // need to configure callback refresh all and set new subcategory
 //   selectedMiddleWare =  categories.filter {it?.id == id }.get(0)
    }

    override fun getMiddleWareModel(): ArrayList<MiddlePropertiesCategories> {
        val arrayList = ArrayList<MiddlePropertiesCategories>()
        categories.map {
            arrayList.add(MiddlePropertiesCategories(it?.circle_icon,it?.description,it?.disable_shipping,
                it?.id,it?.image,it?.name,it?.slug,null,false,false,it?.name))
        }
        return arrayList
    }

    override fun getParentName(context: Context): String {
        return context.getString(R.string.category)
    }


}

class SubCategoryImplementer(val viewModel: FillRequestViewModel,val subCategory : ArrayList<Children?>) :InterfaceSelection {
    override fun setSelection(id: Int) {
        var selectedSubCategory : Children? =null
        subCategory.forEach{
            if (it?.id == id) {
                it.isSelected = true
                selectedSubCategory = it
            }
            else
                it?.isSelected = false
        }
        viewModel.setSelectedSubCategory(selectedSubCategory)
    }

    override fun getMiddleWareModel(): ArrayList<MiddlePropertiesCategories> {
        val arrayList = ArrayList<MiddlePropertiesCategories>()

        subCategory.map {
            arrayList.add(MiddlePropertiesCategories(it?.circle_icon,it?.description,it?.disable_shipping,
                it?.id,it?.image,it?.name,it?.slug,null,false,false,it?.name))
        }
        return arrayList
    }
    override fun getParentName(context: Context): String {
        return context.getString(R.string.subcategory)
    }


}
/*class DataPropertyTop(val viewModel: FillRequestViewModel,val property :ArrayList<DataProperty?>) : InterfaceSelection {
    override fun setSelection(id: Int) {

    }

    override fun getMiddleWareModel(): ArrayList<MiddlePropertiesCategories> {
        val arrayList = ArrayList<MiddlePropertiesCategories>()

        property.map {
            arrayList.add(MiddlePropertiesCategories(id =
            it?.id, name = it?.name, slug =  it?.slug, parent = it?.parent))
        }
        return arrayList
    }
}*/
class OptionImplementer(val viewModel: FillRequestViewModel,val dataProperty :DataProperty?) : InterfaceSelection {
    override fun setSelection(id: Int) { // when select an item
        var selectedOption : Option? =null
        if (id == 0) // in case of selection others
        {
            dataProperty?.selectedOption = Option(
                false, 0, dataProperty?.name ?: "", dataProperty?.parent,
                dataProperty?.slug, dataProperty?.slug, null
            )
            dataProperty?.other_value = "Other"
            dataProperty?.value = "Other"
            selectedOption = dataProperty?.selectedOption

        }
        else {
            dataProperty?.other_value = ""
            dataProperty?.options?.forEach { // each option has sub options
                if (it?.id == id) {
                    dataProperty.value = it.name //set the value to name
                    it.parentSlug =
                        dataProperty.slug
                    selectedOption = it
                    Log.v("parent_selection", selectedOption!!.parent.toString())
                    dataProperty.selectedOption = it
                } else // if not equal current id
                    it?.data?.clear() // remove sub option data
                // else
                //it?.isSelected = false
            }
        }
        viewModel.setSelectedOption(selectedOption)
    }

    override fun getMiddleWareModel(): ArrayList<MiddlePropertiesCategories> {
        val arrayList = ArrayList<MiddlePropertiesCategories>()
        arrayList.add(MiddlePropertiesCategories(name = "Other",id =0))
        dataProperty?.options?.map {
            arrayList.add(MiddlePropertiesCategories(id =
                it?.id, name = it?.name, slug =  it?.slug, parent = it?.parent, child = it?.child,
                parentName = dataProperty.name ?:""))
        }
        return arrayList
    }

    override fun getParentName(context: Context): String {
        return dataProperty?.name?:""
    }


}