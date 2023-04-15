package com.example.androidtask.ui.selectionbottomsheet.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.data.model.DataProperty
import com.example.androidtask.data.model.Option
import com.example.androidtask.databinding.RootOneItemBinding
import com.example.androidtask.util.CommonUtil
import com.example.androidtask.util.setVisibilty
import io.reactivex.disposables.CompositeDisposable

// outer adaptor to set the material card of options and properties
class RootSelectionListAdaptor(//val itemClickedAction : (Contentx)->Unit,
                          //val itemAgreeAction : (Any)->Unit
    val arrayListMiddleProperties: ArrayList<DataProperty?>,
    val utilii: CommonUtil,
    val callBackSelectionRoot: (DataProperty?) -> Unit,
    var subLists: Pair<Option, ArrayList<DataProperty?>>?,
    val dispossibleObserver: CompositeDisposable
    // val canceled : (Pair<Any,Int>)->Unit, val utilii: Util,
                              //  val itemClickSubscripers : (Content)->Unit, val util: Util
) : RecyclerView.Adapter<RootSelectionListAdaptor.ViewHolder>() {

    var subListInfo : Pair<Option, ArrayList<DataProperty?>>?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RootOneItemBinding.
        inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    //    holder.bindItems(imageModel.image!![position],/*modelData*/imageModel.image!!)
        holder.bindItems(arrayListMiddleProperties[position])
        //  setAnimation(holder.itemView, position)

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return  arrayListMiddleProperties.size
    }
    fun updateList(data: List<DataProperty?>) {
        if (arrayListMiddleProperties.isEmpty() ==true) {
            arrayListMiddleProperties.addAll(data)
            notifyDataSetChanged()
        } else {
            arrayListMiddleProperties.addAll(data)
            notifyItemRangeChanged(0, data!!.size)
        }
    }

    fun setSubList(list: Pair<Option, ArrayList<DataProperty?>>) {
        subListInfo = list

    }

    /* fun updateOrderStatus(position: Int,status: Int){
         arrayListOfImagessValues[position].working_status = status
         notifyItemChanged(position)

     }*/

    //the class is hodling the list view

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class ViewHolder(
        val itemBinding: RootOneItemBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItems(
            itemData: DataProperty?
        ) {
             itemBinding.headerSOption.text = itemData?.name?:""
            itemBinding.valueOption.text = itemData?.value?:"" // selected value
            if (subListInfo?.first == itemData?.selectedOption) {
                itemData?.selectedOption?.data = subListInfo?.second
              //  subLists = subListInfo
            }
            if (itemData?.selectedOption!= null/*subListInfo?.first?.parent ==itemData?.parent ||
                itemData?.selectedOption?.parent == subListInfo?.first?.parent ||*//*itemData?.parent == itemData?.selectedOption?.parent||*/
            /*(selectedOption?.size?:0)>0*/
                 ){ // checking parent with new
           // if (itemData?.selectedOption?.child== true && !itemData.selectedOption?.data.isNullOrEmpty()) {
                // set the nested list
                //itemData?.selectedOption = subListInfo?.first
                itemBinding.nestedOptionsChild.setVisibilty(View.VISIBLE)
                val nestedAdaptor = NestedListAdaptor(
                    /*subListInfo?.second?:ArrayList()*/
                    itemData.selectedOption?.data?: ArrayList(), //itemData.selectedOption?.data?:ArrayList(),
                    utilii,
                    callBackSelectionRoot,subListInfo,dispossibleObserver // nested list
                )
                utilii.setRecycleView(itemBinding.nestedOptionsChild,nestedAdaptor,LinearLayoutManager.VERTICAL
                    ,itemBinding.arrowSubCategory.context,null,false)
            }// else
              //  itemBinding.nestedOptionsChild.setVisibilty(View.GONE) // hide the item
            itemBinding.constrainSubCategory.setOnClickListener{
                itemData?.selectedOption =null // remove old selected options
                callBackSelectionRoot.invoke(itemData) // when clicked on the root we need to open the bottom sheet
            }
            if (!itemData?.other_value.isNullOrEmpty()) {
                itemBinding.otherEditTex.setText(itemData?.other_value)
                itemBinding.cardOther.setVisibilty(View.VISIBLE)
                utilii.setOnChange(itemBinding.otherEditTex,itemData!!,dispossibleObserver)

            } else
                itemBinding.cardOther.setVisibilty(View.GONE)
        }
    }

//    var defaultSelectedItem = -1 // defqult no thing selected
}



//class ClickAction(item : Datax)