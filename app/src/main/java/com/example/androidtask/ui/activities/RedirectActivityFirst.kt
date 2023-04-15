package com.example.androidtask.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidtask.R
import com.example.androidtask.databinding.ActivityMainBinding
import com.example.androidtask.databinding.RedirectLayoutBinding
import com.example.androidtask.ui.ImplementerFill
import com.example.androidtask.ui.MainActivity
import com.example.androidtask.ui.fillrequest.FillRequestFragment
import com.example.androidtask.util.CommonUtil
import com.example.androidtask.util.GetObjectGson
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RedirectActivityFirst : AppCompatActivity() {
    var binding :RedirectLayoutBinding? =null
    @Inject lateinit var util: CommonUtil
    @Inject lateinit var gson: GetObjectGson
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RedirectLayoutBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding?.fillLayout?.setOnClickListener {
          startActivity(Intent(this@RedirectActivityFirst,MainActivity::class.java)
              .putExtra(CURRENT_IMPLEMENTER,
              FILL_FRAGMENT))
           /* gson.setCurrentImplementer(
                Gson().toJson(ImplementerFill("fill")),this)*/
        }

        binding?.detailsAd?.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java).putExtra(CURRENT_IMPLEMENTER,
                DETAILS_FRAGMENT))
        }
    }
    companion object {
        val FRAGMENT_TAG_FILL = "fill_request"
        val CURRENT_IMPLEMENTER = "CURRENT_IMPLEMENTER"
        val FILL_FRAGMENT = "FILL"
        val DETAILS_FRAGMENT = "DETAILS"

    }
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }

    }

}
