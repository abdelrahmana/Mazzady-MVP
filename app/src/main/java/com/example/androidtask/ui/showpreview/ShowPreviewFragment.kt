package com.example.androidtask.ui.showpreview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtask.databinding.FragmentShowPreviewBinding
import com.example.androidtask.ui.showpreview.adaptor.AdaptorDataPreview
import com.example.androidtask.util.CommonUtil
import com.example.androidtask.util.Constants
import com.example.androidtask.util.GetObjectGson
import com.example.androidtask.util.setVisibilty
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShowPreviewFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @Inject lateinit var getObjectGson: GetObjectGson
    @Inject lateinit var util: CommonUtil
    var binding : FragmentShowPreviewBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShowPreviewBinding.inflate(layoutInflater,container,false)
        val adaptorListOneItem = AdaptorDataPreview(LinkedHashMap(),
            ArrayList(), ArrayList()
        )
        setRecycleView(binding!!,adaptorListOneItem,getObjectGson
            .getPreviewList(arguments?.getString(Constants.PREVIEWLIST)?:"")?:LinkedHashMap())
        return binding!!.root
    }

    private fun setRecycleView(
        binding: FragmentShowPreviewBinding,
        adaptorListOneItem: AdaptorDataPreview,
        previewList: LinkedHashMap<String, String>
    ) {
        util.setRecycleView(binding.listData,adaptorListOneItem,LinearLayoutManager.VERTICAL,requireContext(),null,false)
        if (previewList.isNullOrEmpty())
            binding.noResults.setVisibilty(View.VISIBLE)
            else {
            binding.noResults.setVisibilty(View.GONE)

            adaptorListOneItem.setPreviewLinkedList(previewList,requireActivity())
            adaptorListOneItem.notifyDataSetChanged()
        }
    }


}