package com.example.androidtask.ui.selectionbottomsheet.adaptor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.data.model.MiddlePropertiesCategories
import com.example.androidtask.databinding.OneItemSelectionBinding
import java.util.concurrent.TimeUnit

// inner adaptor for bottom sheet selection
class SelectionListAdaptor(//val itemClickedAction : (Contentx)->Unit,
                          //val itemAgreeAction : (Any)->Unit
    val arrayListMiddleProperties: ArrayList<MiddlePropertiesCategories>,
   val  callBackSelectionItem: (Int) -> Unit,
                         // val canceled : (Pair<Any,Int>)->Unit, val utilii: Util,
                              //  val itemClickSubscripers : (Content)->Unit, val util: Util
) : RecyclerView.Adapter<SelectionListAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OneItemSelectionBinding.
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
    fun updateList(data: List<MiddlePropertiesCategories>) {
        if (arrayListMiddleProperties.isEmpty() ==true) {
            arrayListMiddleProperties.addAll(data)
            notifyDataSetChanged()
        } else {
            arrayListMiddleProperties.addAll(data)
            notifyItemRangeChanged(0, data!!.size)
        }
    }

   /* fun updateOrderStatus(position: Int,status: Int){
        arrayListOfImagessValues[position].working_status = status
        notifyItemChanged(position)

    }*/

    //the class is hodling the list view


    inner class ViewHolder(
        val itemBinding: OneItemSelectionBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {


        fun bindItems(
            itemData: MiddlePropertiesCategories
        ) {

             itemBinding.valueText.text = itemData.name?:""
            itemBinding.containerPropertyOptions.setOnClickListener{
                callBackSelectionItem.invoke(adapterPosition)
            }

        }
    }


//    var defaultSelectedItem = -1 // defqult no thing selected
}



//class ClickAction(item : Datax)