package com.shri.doordashlite.restaurants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.AppBarLayout
import com.shri.doordashlite.R
import com.shri.doordashlite.restaurants.model.Store

class RestaurantListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<Store?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RestaurantViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.three_column_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == RecyclerView.NO_POSITION) return
        if (items[position] == null) return
        when (holder) {
            is RestaurantViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setAdapterList(stores: List<Store?>?) {
        if (stores?.isNotEmpty() == true)
            items.addAll(stores)
    }

    private inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val coverImageView: ImageView = itemView.findViewById(R.id.restaurant_cover_image)
        private val restaurantName: TextView = itemView.findViewById(R.id.restaurant_name)
        private val description: TextView = itemView.findViewById(R.id.description)
        private val deliveryTime: TextView = itemView.findViewById(R.id.delivery_time)
        init {
            itemView.isEnabled = true
            val lp: LinearLayout.LayoutParams = AppBarLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(30, 15, 30, 15)
            itemView.layoutParams = lp
        }

        fun bind(storeDTO: Store?) {
            if (storeDTO != null) {
                storeDTO.cover_img_url?.let {
                    Glide.with(itemView)
                        .load(it)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(coverImageView)
                }
                restaurantName.text = storeDTO.name
                val desc: List<String> = storeDTO.description.split(",")
                val sb: StringBuilder = java.lang.StringBuilder()
                if (desc.isNotEmpty()) sb.append(desc[0])
                if (desc.size >= 2) sb.append(", ").append(desc[1])
                if (desc.size >= 3) sb.append(", ").append(desc[2])
                description.text = sb.toString()
            }
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }
}
