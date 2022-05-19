package com.example.drinking.adapter

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinking.R
import com.example.drinking.models.Menus
import kotlinx.android.synthetic.main.menu_list_row.view.*

class MenuListAdapter(val menuList: List<Menus?>?, val clickListener: MenuListClickListener): RecyclerView.Adapter<MenuListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuListAdapter.MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.menu_list_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuListAdapter.MyViewHolder, position: Int) {
        holder.bind(menuList?.get(position)!!)
    }

    override fun getItemCount(): Int {
       return if (menuList == null)return 0 else menuList.size
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val thumbImage: ImageView = view.thumbImage
        val menuName: TextView = view.menuName
        val menuPrice: TextView = view.menuPrice
        val addToCartButton: TextView = view.addToCartButton
        val addMoreLayout : LinearLayout = view.addMoreLayout
        val imageMinus: ImageView = view.imageMinus
        val imageAddOne: ImageView = view.imageAddOne
        val tvCount: TextView = view.tvCount

        fun bind(menus: Menus){
            menuName.text = menus?.name
            menuPrice.text = "Giá: ${menus?.price}đ"
            addToCartButton.setOnClickListener {
                menus?.totalIncart = 1
                clickListener.addToCartClickListener(menus)
                addMoreLayout?.visibility = View.VISIBLE
                addToCartButton.visibility = View.GONE
                tvCount.text = menus?.totalIncart.toString()
            }
            imageMinus.setOnClickListener {
                var total: Int = menus?.totalIncart
                total--
                if (total > 0) {
                    menus?.totalIncart = total
                    clickListener.updateCartClickListener(menus)
                    tvCount.text = menus?.totalIncart.toString()
                } else {
                    menus.totalIncart = total
                    clickListener.removeFromCartClickListener(menus)
                    addMoreLayout.visibility = View.GONE
                    addToCartButton.visibility = View.VISIBLE
                }
            }
            imageAddOne.setOnClickListener {
                var total: Int = menus?.totalIncart
                total++
                if(total <= 10) {
                    menus.totalIncart = total
                    clickListener.updateCartClickListener(menus)
                    tvCount.text = total.toString()
                }
            }
            Glide.with(thumbImage)
                .load(menus?.url)
                .into(thumbImage)
        }
    }

    interface MenuListClickListener {
        fun addToCartClickListener(menu: Menus)
        fun updateCartClickListener(menu: Menus)
        fun removeFromCartClickListener(menu: Menus)
    }
}