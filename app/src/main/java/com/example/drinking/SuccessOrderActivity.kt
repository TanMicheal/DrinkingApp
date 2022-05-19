package com.example.drinking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.drinking.models.RestaurentModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_success_order.*

class SuccessOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_order)

        // setup actionbar
        val restaurantModel: RestaurentModel? = intent.getParcelableExtra("RestaurantModel")
        setSupportActionBar(findViewById(R.id.toolbar))
        val actionbar: ActionBar? = supportActionBar
        actionbar?.setTitle(restaurantModel?.name)
        actionbar?.setSubtitle(restaurantModel?.address)
        actionbar?.setDisplayHomeAsUpEnabled(false)
        buttonDone.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }
}