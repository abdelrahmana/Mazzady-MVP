package com.example.androidtask.ui.detailsbids.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.data.model.DataProperty
import com.example.androidtask.databinding.OneItemSellerBinding
import com.example.androidtask.databinding.RootOneItemBinding

// outer adaptor to set the material card of options and properties
class AdaptorSeller(//val itemClickedAction : (Contentx)->Unit,
                          //val itemAgreeAction : (Any)->Unit
    val arraySeller: ArrayList<Any?>,
) : RecyclerView.Adapter<AdaptorSeller.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OneItemSellerBinding.
        inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    //    holder.bindItems(imageModel.image!![position],/*modelData*/imageModel.image!!)
        holder.bindItems(arraySeller[position])
        //  setAnimation(holder.itemView, position)

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return  arraySeller.size
    }
    fun updateList(data: List<DataProperty?>) {
        if (arraySeller.isEmpty() ==true) {
            arraySeller.addAll(data)
            notifyDataSetChanged()
        } else {
            arraySeller.addAll(data)
            notifyItemRangeChanged(0, data!!.size)
        }
    }

    inner class ViewHolder(
        val itemBinding: OneItemSellerBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItems(
            itemData: Any?
        ) {
        }
    }
}
