package com.example.androidtask.ui.detailsbids.adaptor

import android.content.Context
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidtask.R
import kotlinx.android.synthetic.main.image_slider.view.*

class ImageSliderAdapter (var context: Context, var adsArray: ArrayList<Any?>) :
    RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.image_slider, parent , false)
        // THIS LINE WILL DIVIDE OUR VIEW INTO NUMBERS OF PARTS
       // view.getLayoutParams().width =  ((getScreenWidth() / NUMBERS_OF_ITEM_TO_DISPLAY).toInt())
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return adsArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }




  inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      fun bind(itemData : Any) {

      }

        }


    }


