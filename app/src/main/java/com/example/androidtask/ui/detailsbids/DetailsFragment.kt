package com.example.androidtask.ui.detailsbids

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtask.databinding.FragmentDetailsBinding
import com.example.androidtask.ui.detailsbids.adaptor.AdaptorSeller
import com.example.androidtask.ui.detailsbids.adaptor.ImageSliderAdapter
import com.example.androidtask.util.CommonUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @Inject lateinit var util: CommonUtil
    lateinit var binding : FragmentDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater,container,false)
        setImagesSlider(GetDefaultArrayList(3))
        setSellersList(GetDefaultArrayList(4))
        return binding.root
    }

     fun GetDefaultArrayList(numberOfElemnts : Int): ArrayList<Any?> {
        val arrayList =   ArrayList<Any?>()
        for (i in 0 until numberOfElemnts) {
          arrayList.add("")
        }
        return arrayList
    }

    private fun setImagesSlider(array:ArrayList<Any?>) {
       val mediaAdapter = ImageSliderAdapter(requireContext(),array)
        binding.mediaPager.setAdapter(mediaAdapter)
        TabLayoutMediator(binding.tabLayout, binding.mediaPager,
            TabLayoutMediator.TabConfigurationStrategy { tab: TabLayout.Tab?, position: Int -> }).attach()
    }

    private fun setSellersList(array: ArrayList<Any?>) {
        val sellerAdaptor = AdaptorSeller(array)
        util.setRecycleView(binding.recycleViewBidders,sellerAdaptor,LinearLayoutManager.VERTICAL,requireContext(),null,false)

    }

    companion object {

    }
}