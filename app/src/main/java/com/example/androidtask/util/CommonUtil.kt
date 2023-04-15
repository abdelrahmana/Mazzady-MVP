package com.example.androidtask.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.data.model.DataProperty
import com.example.androidtask.data.model.GridModel
import com.example.androidtask.data.model.MiddlePropertiesCategories
import com.example.androidtask.ui.activities.RedirectActivityFirst.Companion.CURRENT_IMPLEMENTER
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CommonUtil(val context: Context) {
    fun setRecycleView(recyclerView: RecyclerView?, adaptor: RecyclerView.Adapter<*>,
                       verticalOrHorizontal: Int?, context: Context, gridModel: GridModel?, includeEdge : Boolean) {
        var layoutManger : RecyclerView.LayoutManager? = null
        if (gridModel==null) // normal linear
            layoutManger = LinearLayoutManager(context, verticalOrHorizontal!!,false)
        else
        {
            layoutManger = GridLayoutManager(context, gridModel.numberOfItems)
            if (recyclerView?.itemDecorationCount==0)
                recyclerView.addItemDecoration(SpacesItemDecoration(gridModel.numberOfItems, gridModel.space, includeEdge))
        }
        recyclerView?.apply {
            setLayoutManager(layoutManger)
            setHasFixedSize(true)
            adapter = adaptor

        }
    }
    fun changeFragmentBack(activity: FragmentActivity, fragment: Fragment, tag: String, bundle: Bundle?, id : Int ) {

        val transaction = activity.supportFragmentManager.beginTransaction()
        if (bundle != null) {
            fragment.arguments = bundle
        }
        transaction.replace(id, fragment, tag)
        transaction.addToBackStack(tag)
        //    transaction.addToBackStack(null)
        transaction.commit()

    }
     fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> handleCellularInSureDevice(true)
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
            val capabilities = connectivityManager.getNetworkCapabilities(networkCapabilities)
            capabilities?.let {
                result = it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&  capabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }
        //     if (result)
        //        return internetIsConnected()

        return result
    }
    fun handleCellularInSureDevice(connectionTypeIsCellular : Boolean) : Boolean {
        var result = true
        if(connectionTypeIsCellular && Build.MANUFACTURER == "QUALCOMM"){
            result = false
        }
        return result
    }
     fun setOnChange(
        autoComplete: EditText,
       itemdata : DataProperty,dispossible:CompositeDisposable
    ) { // keyword
        dispossible.add(
            RxTextView.textChanges(autoComplete)
                .debounce(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .map { it.toString() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({keyword->
                    itemdata.other_value =keyword
                }, {
                }))

    }


}