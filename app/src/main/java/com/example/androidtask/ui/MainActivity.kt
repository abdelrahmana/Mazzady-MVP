package com.example.androidtask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidtask.R
import com.example.androidtask.databinding.ActivityMainBinding
import com.example.androidtask.ui.activities.RedirectActivityFirst
import com.example.androidtask.ui.activities.RedirectActivityFirst.Companion.DETAILS_FRAGMENT
import com.example.androidtask.ui.activities.RedirectActivityFirst.Companion.FILL_FRAGMENT
import com.example.androidtask.ui.fillrequest.FillRequestFragment
import com.example.androidtask.util.CommonUtil
import com.example.androidtask.util.GetObjectGson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    var binding :ActivityMainBinding? =null
    @Inject lateinit var util: CommonUtil
    @Inject lateinit var getObjectGson: GetObjectGson
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
       when(intent.getStringExtra(RedirectActivityFirst.CURRENT_IMPLEMENTER)?:""){
           FILL_FRAGMENT-> ImplementerFill("fill").setCurrentImplementer(this,R.id.containerFrame,util)
           DETAILS_FRAGMENT-> ImplementerShowData("show_data")
               .setCurrentImplementer(this,R.id.containerFrame,util)

       }
      //  util.changeFragmentBack(this,FillRequestFragment(), FRAGMENT_TAG_FILL,null,R.id.containerFrame)

    }
    companion object {
        val FRAGMENT_TAG_FILL = "fill_request"
    }
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
          finish()
        }

    }
}
