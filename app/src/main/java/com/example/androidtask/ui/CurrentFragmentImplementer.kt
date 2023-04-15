package com.example.androidtask.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.androidtask.R
import com.example.androidtask.ui.activities.RedirectActivityFirst
import com.example.androidtask.ui.detailsbids.DetailsFragment
import com.example.androidtask.ui.fillrequest.FillRequestFragment
import com.example.androidtask.util.CommonUtil

interface CurrentFragmentImplementer {
    fun setCurrentImplementer(requireactivity : FragmentActivity,container : Int,util:CommonUtil)
}
class ImplementerFill(
val fragmentKey : String,/*val bundle: Bundle? = null*/) :CurrentFragmentImplementer {
    override fun setCurrentImplementer(
        requireactivity: FragmentActivity,
        container: Int,
        util: CommonUtil
    ) {
        util.changeFragmentBack(
            requireactivity,
            FillRequestFragment(),
            RedirectActivityFirst.FRAGMENT_TAG_FILL,
            null,
            container
        )
    }
}

    class ImplementerShowData(
        val fragmentKey: String,
       // val bundle: Bundle? = null
    ) : CurrentFragmentImplementer {
        override fun setCurrentImplementer( requireactivity : FragmentActivity,container: Int,util:CommonUtil) {
            util.changeFragmentBack(
                requireactivity,
                DetailsFragment(),
                RedirectActivityFirst.FRAGMENT_TAG_FILL,
                null,
                container
            )
        }

    }
