package com.example.androidtask.ui.showpreview.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.databinding.OneItemPreviewBinding
import com.example.androidtask.databinding.RootOneItemBinding

// outer adaptor to set the material card of options and properties
class AdaptorDataPreview(//val itemClickedAction : (Contentx)->Unit,
                          //val itemAgreeAction : (Any)->Unit
    val arrayListMiddleProperties: LinkedHashMap<String,String>?,
    val arrayListKeys: ArrayList<String>,
        val arrayListValues : ArrayList<String>
) : RecyclerView.Adapter<AdaptorDataPreview.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OneItemPreviewBinding.
        inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    //    holder.bindItems(imageModel.image!![position],/*modelData*/imageModel.image!!)
        holder.bindItems(arrayListKeys.get(holder.adapterPosition),arrayListValues.get(holder.adapterPosition))
        //  setAnimation(holder.itemView, position)
    }
    fun setPreviewLinkedList(
        linkedHashMap: LinkedHashMap<String, String>,
        requireActivity: FragmentActivity
    ){
        arrayListMiddleProperties?.clear()
        arrayListMiddleProperties?.putAll(linkedHashMap)
        arrayListValues.clear()
        arrayListKeys.clear()
        arrayListKeys.add(requireActivity.getString(R.string.key_text))
        arrayListValues.add(requireActivity.getString(R.string.value_text))

        arrayListMiddleProperties?.forEach {
            arrayListKeys.add(it.key)
            arrayListValues.add(it.value)
        }
      //  arrayListKeys.addAll(arrayListMiddleProperties!!.keys)
       // arrayListValues.addAll(arrayListMiddleProperties.values)
        notifyDataSetChanged()
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return  arrayListKeys.size
    }
    inner class ViewHolder(
        val itemBinding: OneItemPreviewBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItems(
            key: String?,
            value: String
        ) {
            itemBinding.key.text = key?:""
            itemBinding.values.text = value
        }
    }

}



