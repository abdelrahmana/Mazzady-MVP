package com.example.androidtask.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidtask.MainCoroutineRule
import com.example.androidtask.getOrAwaitValueTest
import com.example.androidtask.repo.CategoryOptionsRepositoryFakeTest
import com.example.androidtask.ui.fillrequest.FillRequestViewModel
import com.example.androidtask.ui.selectionbottomsheet.SelectionOptionBottomSheet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FillViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule() // background executor  to excute task
    // synchronously

   @get:Rule
   var mainCoroutineRule = MainCoroutineRule() // use dispatchers coroutines

    lateinit var fillViewModelTest: FillRequestViewModel
    @Before
    fun `set user info`() {
        fillViewModelTest = FillRequestViewModel(CategoryOptionsRepositoryFakeTest())
    }

    @Test
    fun `check the get Requests failed`(){
        fillViewModelTest.getCategories()
      val value =  fillViewModelTest.categoryImplementerLiveData.getOrAwaitValueTest()
        assert(value?.isNotEmpty()==true)
    }
    @Test
    fun `check the get Requests success`(){
        fillViewModelTest.getCategories()
        val value =  fillViewModelTest.categoryImplementerLiveData.getOrAwaitValueTest()
        assert(value?.isEmpty()==true)
    }
}