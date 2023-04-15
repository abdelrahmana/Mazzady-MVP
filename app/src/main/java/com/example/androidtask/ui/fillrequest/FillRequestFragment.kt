package com.example.androidtask.ui.fillrequest

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtask.R
import com.example.androidtask.data.model.Category
import com.example.androidtask.data.model.Children
import com.example.androidtask.data.model.DataProperty
import com.example.androidtask.databinding.FillRequestFragmentBinding
import com.example.androidtask.ui.selectionbottomsheet.*
import com.example.androidtask.ui.selectionbottomsheet.adaptor.RootSelectionListAdaptor
import com.example.androidtask.ui.showpreview.ShowPreviewFragment
import com.example.androidtask.util.CommonUtil
import com.example.androidtask.util.Constants
import com.example.androidtask.util.showHide
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FillRequestFragment : Fragment() {
    @Inject
    lateinit var progressDialog : Dialog
    @Inject lateinit var util: CommonUtil
    @Inject lateinit var dispossibleObserver : CompositeDisposable
    private val fillRequestViewModel: FillRequestViewModel by activityViewModels()
    var binding :FillRequestFragmentBinding? =null
    var adaptorRootList : RootSelectionListAdaptor?=null
    var propertiesList = ArrayList<DataProperty?>()
    var categoriesList = ArrayList<Category?>() // will be used on the bottomsheet
    var hashMapSelection = LinkedHashMap<String,String>()
    var selectedCategory : Category? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FillRequestFragmentBinding.inflate(layoutInflater,container,false)
        adaptorRootList = RootSelectionListAdaptor(propertiesList,util,callBackSelectionRoot,null,dispossibleObserver)
        util.setRecycleView(binding?.optionsRecycleView,adaptorRootList!!,
            LinearLayoutManager.VERTICAL,requireContext(),null,false)
        observeLiveData()
        if (util.isInternetAvailable(requireContext())){
            progressDialog.showHide(false)
            fillRequestViewModel.getCategories() // get categories
        }
        setClickActionMainCategory(binding)
        return binding!!.root
    }
    var callBackSelectionRoot :(DataProperty?)->Unit = {
        fillRequestViewModel.setCurrentMutable( // set the selection to option layer
            OptionImplementer(fillRequestViewModel, it)
        )
        val popupWindow = SelectionOptionBottomSheet()
        popupWindow.show(requireActivity().supportFragmentManager,"")
    }
    private fun setClickActionMainCategory(binding: FillRequestFragmentBinding?) { // main category
        binding?.let {
            it.containerMainCat.setOnClickListener {
                // send the payload migration
                fillRequestViewModel.setCurrentMutable(
                    CategoryImplementer(fillRequestViewModel,categoriesList))
                val popupWindow = SelectionOptionBottomSheet()
                popupWindow.show(requireActivity().supportFragmentManager,"")
            }
            it.submitId.setOnClickListener{
                lifecycleScope.launch {
                    setValuesSelectionForOptions(propertiesList, hashMapSelection)
                    util.changeFragmentBack(requireActivity(),ShowPreviewFragment(),SHOW_PAYMENT,
                        bundleOf(Constants.PREVIEWLIST to Gson().toJson(hashMapSelection)), R.id.containerFrame)
                }
            }
        }
    }

    private fun setValuesSelectionForOptions(propertiesList: ArrayList<DataProperty?>, hashMapSelection: LinkedHashMap<String, String>) {
     val selectedPropertiesParent  =getSelectedParentProperties(propertiesList) // parent list that choosed
        val selectedSubProperties = getSelectedOptionFromChildsAndSubChilds(selectedPropertiesParent) // each child that
       // val selectedPropertiesSubChild = getSelectedOptionFromChilds(selectedSubProperties)
        selectedPropertiesParent.forEach{
            hashMapSelection.put(it?.selectedOption?.parentSlug?:"",/*it?.selectedOption?.slug?:""*/getSpecificValueDataProperty(it))
        }
        selectedSubProperties?.forEach {
            it?.forEach {
                hashMapSelection.put(it?.selectedOption?.parentSlug?:"",/*it?.selectedOption?.slug?:""*/getSpecificValueDataProperty(it))
            }
        }
        setForEachSubChild(selectedSubProperties) // generic
        hashMapSelection.forEach {
            Log.v(it.key?:"" , it.value?:"")
        }
        /* selectedSubProperties?.map {
             it?.selectedOption?.data?.forEach {
                 hashMapSelection.put(it?.selectedOption?.parentSlug?:"",it?.slug?:"")
             }
         }
         selectedPropertiesSubChild?.map{

         }*/
    }

    private fun setForEachSubChild(selectedSubProperties: List<ArrayList<DataProperty?>?>?) {
        selectedSubProperties?.forEach {
          val getSubChildsForThisOption =  getSelectedOptionFromChildsAndSubChilds(it)
           if (!getSubChildsForThisOption.isNullOrEmpty())
               setForEachSubChild(getSubChildsForThisOption) // recersive here when each item is ok with us
            it?.forEach {
                hashMapSelection.put(it?.selectedOption?.parentSlug?:"",/*it?.selectedOption?.slug?:""*/getSpecificValueDataProperty(it))
            }
        }
    }
    fun getSpecificValueDataProperty(dataProperty: DataProperty?): String {
        if (dataProperty?.other_value?.isEmpty()==false)
            // has values
                return dataProperty.other_value ?:""
        else
           return dataProperty?.selectedOption?.slug?:""
    }

    private fun getSelectedOptionFromChildsAndSubChilds(selectedPropertiesParent: List<DataProperty?>?):
            List<ArrayList<DataProperty?>?>? {
     val selectedChildsProperty = selectedPropertiesParent?.filter { // has sub properties
            !it?.selectedOption?.data.isNullOrEmpty() // get the only ones has the data
        }
       val selectedChildData : List<ArrayList<DataProperty?>?>? =
           selectedChildsProperty?.map { it?.selectedOption?.data } // filter them in new arraylist contains them only
      //  val filteredResult = ArrayList<DataProperty?>()

        return selectedChildData
    }

    private fun getSelectedParentProperties(propertiesList: ArrayList<DataProperty?>): List<DataProperty?> {
      val filteredList=  propertiesList.filter {it?.selectedOption!=null // parent properties
        }
        return  filteredList
    }

    override fun onDestroyView() {
      fillRequestViewModel.currentImplementerLiveData.removeObservers(requireActivity())
        dispossibleObserver.dispose()
        super.onDestroyView()
    }

    fun observeLiveData() {
        // get api selection
        fillRequestViewModel.categoryImplementerLiveData.observe(requireActivity(), Observer{ // for sending to maintenance
            it?.let { list-> // now you have the properties root
                progressDialog.showHide(true)
               categoriesList.addAll(list)
                propertiesList.clear()
                adaptorRootList?.notifyDataSetChanged()

            }
        })
        // api get properties when subcategory is selected
        fillRequestViewModel.rootImplementerLiveData.observe(requireActivity(), Observer{ // for sending to maintenance
            it?.let { list-> // now you have the properties root
                progressDialog.showHide(true)
                propertiesList.clear() // clear old data when sub category changed
                updateAdaptor(list,propertiesList)
            }
        })
        // api get subproperty
        fillRequestViewModel.subPropertyImplementerLiveData.observe(requireActivity(), Observer{ // for sending to maintenance
            progressDialog.showHide(true)
            it?.let { list-> // now you have the properties root
             /*   var founded = false
                propertiesList.forEach {
                    it?.options?.forEach {
                        // in case of the parent is parent
                        if (it?.parent == list.first.parent && it?.id == list.first.id) // same option
                            it?.data = list.second
                    }
                }*/
                       /* if (it?.id == list.first.parent) // property id
                        {
                            founded =true
                            it?.selectedOption = list.first // set the selected option
                            it?.selectedOption?.data = list.second
                            return@forEach
                        }*/
            /*    propertiesList.map {dataProperty->
                    dataProperty?.options?.map {
                        if (it?.parent == list.first.parent) {
                            dataProperty.selectedOption = list.first
                            dataProperty.selectedOption?.data = list.second
                        }
                    }

                }*/
                adaptorRootList?.setSubList(list) // set this check in adaptor
                adaptorRootList?.notifyDataSetChanged()
                //updateAdaptor(list,propertiesList) // don't clear the supper list but append to childs
                fillRequestViewModel.setSubProperty(null)
            }
        })
      /*  fillRequestViewModel.currentImplementerLiveData.observe(requireActivity(), Observer { // for sending to maintenance
            it?.let { implementer ->
                // when update happened selection in the bottomsheet page

            }
        })*/
        fillRequestViewModel.selectedCategory.observe(requireActivity(), Observer{ // for sending to maintenance
            it?.let { category-> // now you have the properties root
                binding?.valueSubCategory?.text = ""
                binding?.valueCategory?.text = category.name?:""
                resetHashMap(hashMapSelection) // remove previous options
                selectedCategory = category
                hashMapSelection.put(category.slug?:"",category.name?:"")
                setSelectedSubCategory(category)
            }
        })
        // when sub category is selected
        fillRequestViewModel.selectedSubCategory.observe(requireActivity(), Observer{ // for sending to maintenance
            it?.let { subCategory-> // now you have the properties root
              // propertiesList.clear()
                progressDialog.show()
                binding?.valueSubCategory?.text = subCategory.name
                resetHashMap(hashMapSelection) // remove previous options
                hashMapSelection.put(selectedCategory?.slug?:"",selectedCategory?.name?:"")
                hashMapSelection.put(subCategory.slug?:"",subCategory.name?:"")
                fillRequestViewModel.getProperties(subCategory.id?:0)
            }
        })
        // when option  is selected
        fillRequestViewModel.selectedOption.observe(requireActivity(), Observer{ // for sending to maintenance
            it?.let { option-> // now you have the properties root
                // propertiesList.clear()
                if (option.child == true) {
                    progressDialog.show()
                    fillRequestViewModel.getSubProperty(option)
                }
                adaptorRootList?.notifyDataSetChanged()
                  //   hashMapSelection.put(option.parentSlug?:"",option.slug?:"") // set slug to the selected item
            }
        })

    }

    private fun resetHashMap(hashMapSelection: LinkedHashMap<String, String>) {
        hashMapSelection.clear()

    }

    private fun setSelectedSubCategory(category: Category) {
        propertiesList.clear()
        adaptorRootList?.notifyDataSetChanged()
        category.children.let { list->
            binding?.containerSubCategory?.setOnClickListener{
                fillRequestViewModel.setCurrentMutable(
                    SubCategoryImplementer(fillRequestViewModel, list as ArrayList<Children?>))
                val popupWindow = SelectionOptionBottomSheet()
                popupWindow.show(requireActivity().supportFragmentManager,"")
            }
        }
    }

    private fun updateAdaptor(
        newPropertiesList: ArrayList<DataProperty?>,
        propertiesList: ArrayList<DataProperty?>
    ) {
        propertiesList.addAll(newPropertiesList)
        adaptorRootList = RootSelectionListAdaptor(
            propertiesList,
            util,
            callBackSelectionRoot,
            null,
            dispossibleObserver
        )
        util.setRecycleView(binding?.optionsRecycleView,adaptorRootList!!,
            LinearLayoutManager.VERTICAL,requireContext(),null,false)
        // adaptorRootList?.notifyDataSetChanged()
    }
companion object {
    val SELECTEDCATEGORY = "CATEGORY"
    val SELECTEDSUBCATEGORY = "SUBCATEGORY"
    val SHOW_PAYMENT = "SHOW"
}

}