package com.example.drinking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_restaurant_menu.*

//customize import
import com.example.drinking.R
import com.example.drinking.adapter.MenuListAdapter
import com.example.drinking.models.Menus
import com.example.drinking.models.RestaurentModel
import com.google.firebase.auth.FirebaseAuth

class RestaurantMenuActivity : AppCompatActivity(), MenuListAdapter.MenuListClickListener {

    private var itemsInTheCartList: MutableList<Menus?>? = null
    private var totalItemInCartCount = 0
    private var menuList: List<Menus?>? = null
    private var menuListAdapter: MenuListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu)

        // setup actionbar
        val restaurantModel = intent?.getParcelableExtra<RestaurentModel>("RestaurantModel")
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(restaurantModel?.name)
        actionBar?.setSubtitle(restaurantModel?.address)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        menuList = restaurantModel?.menus

        initRecycleView(menuList)
        checkoutButton.setOnClickListener {
            if (itemsInTheCartList != null && itemsInTheCartList!!.size <= 0) {
                Toast.makeText(this@RestaurantMenuActivity, "Vui lòng thêm sản phẩm vào giỏ hàng", Toast.LENGTH_LONG).show()
            } else if (itemsInTheCartList == null) {
                Toast.makeText(this@RestaurantMenuActivity, "Vui lòng thêm sản phẩm vào giỏ hàng", Toast.LENGTH_LONG).show()
            } else {
                restaurantModel?.menus = itemsInTheCartList
                val intent = Intent(this@RestaurantMenuActivity, PlaceYourOrderActivity::class.java)
                intent.putExtra("RestaurantModel", restaurantModel)
                startActivityForResult(intent, 1000)
            }
        }
    }
    private fun initRecycleView(menus: List<Menus?>?) {
        menuRecyclerView.layoutManager = GridLayoutManager(this, 2)
        menuListAdapter = MenuListAdapter(menus, this)
        menuRecyclerView.adapter = menuListAdapter
    }

    override fun addToCartClickListener(menu: Menus) {
        if (itemsInTheCartList == null) {
            itemsInTheCartList = ArrayList()
        }
        itemsInTheCartList?.add(menu)
        totalItemInCartCount = 0
        for (menu in itemsInTheCartList!!) {
            totalItemInCartCount = totalItemInCartCount + menu?.totalIncart!!
        }
        checkoutButton.text = "Thanh toán (" + totalItemInCartCount +") sản phẩm"

    }

    override fun updateCartClickListener(menu: Menus) {
        val index = itemsInTheCartList!!.indexOf(menu)
        itemsInTheCartList?.removeAt(index)
        itemsInTheCartList?.add(menu)
        totalItemInCartCount = 0
        for (menu in itemsInTheCartList!!) {
            totalItemInCartCount = totalItemInCartCount + menu?.totalIncart!!
        }
        checkoutButton.text = "Thanh toán (" + totalItemInCartCount +") sản phẩm"
    }

    override fun removeFromCartClickListener(menu: Menus) {
        if (itemsInTheCartList!!.contains(menu)) {
            itemsInTheCartList?.remove(menu)
            totalItemInCartCount = 0
            for (menu in itemsInTheCartList!!) {
                totalItemInCartCount = totalItemInCartCount + menu?.totalIncart!!
            }
            checkoutButton.text = "Thank toán (" + totalItemInCartCount +") sản phẩm"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            finish()
        }
    }
}