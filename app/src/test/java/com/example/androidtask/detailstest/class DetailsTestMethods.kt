package com.example.androidtask.detailstest

import com.example.androidtask.ui.detailsbids.DetailsFragment
import org.junit.Before
import org.junit.Test


class DetailsTestMethods {
    lateinit var detailsFragment: DetailsFragment
    @Before
    fun `set user info`() {
        detailsFragment = DetailsFragment()
    }
    @Test
    fun `check the list added`(){
     val list =   detailsFragment.GetDefaultArrayList(3)
        assert(list.size>6)
    }
}