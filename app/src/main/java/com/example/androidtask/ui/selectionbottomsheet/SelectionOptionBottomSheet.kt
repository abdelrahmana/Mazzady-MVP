package com.example.androidtask.ui.selectionbottomsheet

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtask.R
import com.example.androidtask.data.model.MiddlePropertiesCategories
import com.example.androidtask.databinding.SelectionBottomsheetBinding
import com.example.androidtask.ui.fillrequest.FillRequestViewModel
import com.example.androidtask.ui.selectionbottomsheet.adaptor.SelectionListAdaptor
import com.example.androidtask.util.CommonUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


// used to get area and city besides get the medical
@AndroidEntryPoint
class SelectionOptionBottomSheet() : BottomSheetDialogFragment()/*, CommonPresenter.CommonCallsInterface*/{
    @Inject
    lateinit var progressDialog : Dialog
    @Inject
    lateinit var util : CommonUtil
    @Inject lateinit var dispossible : CompositeDisposable
    val model : FillRequestViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // for making the bottom sheet background transparent
      //  webService = ApiManagerDefault(activity!!).apiService
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }
    var hashmapData : HashMap<String,Any>?=null
   lateinit var dialog : BottomSheetDialog
   lateinit var binding : SelectionBottomsheetBinding
   var filteredArrayList : ArrayList<MiddlePropertiesCategories>? = ArrayList()
    var originalList : ArrayList<MiddlePropertiesCategories>? = ArrayList()

    var adaptorSelection : SelectionListAdaptor?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SelectionBottomsheetBinding.inflate(layoutInflater)
       /* hashmapData=  gsonObject.getGenericMap(arguments?.getString(TRIPOBJECT)?:"")
        binding.cancelButton.setOnClickListener{ // this should call send
            hashmapData?.put(CANCEL_REASON,binding.reasonCancel)
            model.postCancelTrip(hashmapData!!) // call cancel here
        }*/

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
         dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        binding = SelectionBottomsheetBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
      //  dialog.getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
         return dialog
     }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModelObservers()
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val dialog = dialog as BottomSheetDialog
                val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
                val behavior = BottomSheetBehavior.from(bottomSheet!!)
                //    behavior.state = BottomSheetBehavior.PEEK_HEIGHT_AUTO

                val newHeight = activity?.window?.decorView?.measuredHeight
                val viewGroupLayoutParams = bottomSheet.layoutParams
                viewGroupLayoutParams.height = newHeight ?: 0
                bottomSheet.layoutParams = viewGroupLayoutParams
                BottomSheetBehavior.from(bottomSheet!!).peekHeight = 1000
                //   behavior?.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            }
        })

    }

    private fun setOnChange(
        autoComplete: EditText,
        originalList: ArrayList<MiddlePropertiesCategories>,
    ) { // keyword
        dispossible.add(
            RxTextView.textChanges(autoComplete)
            .skip(1)
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .map { it.toString() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({keyword->
                checkKeyWordWithOriginalList(keyword,filteredArrayList,originalList)
                adaptorSelection?.notifyDataSetChanged()

            }, {
                Log.e(tag, "search text changes error : ${it.message}")
            }))

    }
     fun checkKeyWordWithOriginalList(keyword: String,
                                      filteredArrayList: ArrayList<MiddlePropertiesCategories>?,
                                      originalList: ArrayList<MiddlePropertiesCategories>) {
        filteredArrayList?.clear()
        if (keyword.isNotEmpty()) {
            originalList.forEach{
                if (it.name?.lowercase()!!.contains(keyword))
                    filteredArrayList?.add(it)
            }

        }else {
            // AdapterRemoveItem.add(it)
            filteredArrayList?.addAll(originalList)
        }
    }
    val callBackSelectionItem : (Int)->Unit = { // position selection

        currentImplementer?.setSelection(filteredArrayList?.get(it)?.id?:0) // send the callback in view model to listener
      //  filteredArrayList?.get(it) // selected item
        dismiss()
    }
    var currentImplementer : InterfaceSelection? =null
    var observerImplementer : Observer<InterfaceSelection?>? =null
    private fun setViewModelObservers() { // get current selection
        observerImplementer=  Observer{ // for sending to maintenance
            it?.let { implementer->
                // get current list
                currentImplementer = implementer
                originalList?.addAll(implementer.getMiddleWareModel())
                filteredArrayList?.addAll(implementer.getMiddleWareModel())
                adaptorSelection = SelectionListAdaptor(filteredArrayList!!,callBackSelectionItem)
                binding.selectionParentName.text = implementer.getParentName(requireContext())
                util.setRecycleView(binding.itemValue,adaptorSelection!!,LinearLayoutManager.VERTICAL,
                    requireContext(),null,false)
                setOnChange(binding.searchEditText,originalList?:ArrayList())

            }
        }
       model.currentImplementerLiveData.observe(requireActivity(),observerImplementer!!)


      /*  model.networkLoader.observe(requireActivity(), Observer{
            it?.let { progress->
                progress.setDialog(progressDialog) // open close principles
                model.setNetworkLoader(null)
            }
        })

        model.errorViewModel.observe(requireActivity()) {
            it?.let { error ->
                util.showSnackMessages(requireActivity(), error)

            }
        }*/
    }
    override fun onDestroyView() {
        observerImplementer?.let {
            model.currentImplementerLiveData.removeObserver(it)
        }
        dispossible.dispose()
    //  model.clearLiveDataOnActivity(requireActivity())
        //   model?.notifedItemSelectedAdaptor?.removeObservers(requireActivity()!!)
        super.onDestroyView()
    }
    companion object {
    }

}