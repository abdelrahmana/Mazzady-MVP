package com.example.androidtask.ui.fillrequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtask.data.model.*
import com.example.androidtask.data.source.remote.repository.CategoryOptionsRepository
import com.example.androidtask.ui.selectionbottomsheet.CategoryImplementer
import com.example.androidtask.ui.selectionbottomsheet.InterfaceSelection
import com.example.androidtask.ui.selectionbottomsheet.OptionImplementer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FillRequestViewModel @Inject constructor(val categoryOptionsRepository: CategoryOptionsRepository) : ViewModel() {
    private val _currentImplementerMutable = MutableLiveData<InterfaceSelection?>()
    var currentImplementerLiveData = _currentImplementerMutable
    private val _categoryMutable = MutableLiveData<ArrayList<Category?>?>()
    var categoryImplementerLiveData :LiveData<ArrayList<Category?>?> = _categoryMutable
    private val _rootrMutable = MutableLiveData<ArrayList<DataProperty?>?>()
    var rootImplementerLiveData :LiveData<ArrayList<DataProperty?>?> = _rootrMutable
    private val _subPropertyrMutable = MutableLiveData<Pair<Option,ArrayList<DataProperty?>>?>()
    var subPropertyImplementerLiveData :LiveData<Pair<Option,ArrayList<DataProperty?>>?> = _subPropertyrMutable
    private val _error = MutableLiveData<String?>()
    var errorLiveData :LiveData<String?> = _error

    // selected category
    private val _selectedCategory = MutableLiveData<Category?>()
    var selectedCategory : LiveData<Category?> = _selectedCategory

    private val _selectedSubCategory = MutableLiveData<Children?>()
    var selectedSubCategory : LiveData<Children?> = _selectedSubCategory
    fun setSubProperty(sub : Pair<Option,ArrayList<DataProperty?>>?){
        _subPropertyrMutable.postValue(sub)
    }
    fun setSelectedSubCategory(subCategory: Children?){
        _selectedSubCategory.postValue(subCategory)
    }
    private val _selectedOption = MutableLiveData<Option?>()
    var selectedOption : LiveData<Option?> = _selectedOption
    fun setSelectedOption(option: Option?){
        _selectedOption.postValue(option)
    }

    fun setSelectedCategory(category: Category?){
        _selectedCategory.postValue(category)
    }
    fun setCurrentMutable(interfaceSelection: InterfaceSelection?){
        _currentImplementerMutable.postValue(interfaceSelection)
    }
    fun setError(error: String?){
        _error.postValue(error)
    }
    fun getCategories() { // set category
        // load loader of internet here
        viewModelScope.launch {
            categoryOptionsRepository.getCategories{categories,error->
                categories?.let {
                    // set the middle ware
                    _categoryMutable.value =  it.categories as ArrayList<Category?>// set value to implementer
                       /* CategoryImplementer(this@FillRequestViewModel,
                            it.categories as ArrayList<Category?>).getMiddleWareModel()*/
                }
                error?.let {
                    setError(it)
                }
            }
        }
    }

    fun getProperties(subcategoryId : Int) {
        // load loader of internet here
        viewModelScope.launch {
            categoryOptionsRepository.getProperties(subcategoryId){properties,error->
                properties?.let {
                    _rootrMutable.value = properties as ArrayList<DataProperty?> // set value to implementer
                       /* DataPropertyTop(this@FillRequestViewModel,
                            properties as ArrayList<DataProperty?>).getMiddleWareModel()*/
                }
                error?.let {
                    setError(it)
                }
            }
        }
    }
    fun getSubProperty(option : Option) { // you have the selection id
        // load loader of internet here
        viewModelScope.launch {
            categoryOptionsRepository.getSubProperties(option.id?:0){properties,error->
                properties?.let {
                    _subPropertyrMutable.value =  Pair(option,properties as ArrayList<DataProperty?>)// set value to implementer
                       /* DataPropertyTop(this@FillRequestViewModel,
                            properties as ArrayList<DataProperty?>).getMiddleWareModel()*/
                }
                error?.let {
                    setError(it)
                }
            }
        }
    }
}