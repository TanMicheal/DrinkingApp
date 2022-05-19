package com.example.drinking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drinking.databinding.ActivityMainBinding
import com.example.drinking.models.RestaurentModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import java.io.*

class MainActivityBackup : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityMainBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    //Firebase RealtimeDatabase
    private var databaseReference: DatabaseReference?= null
    private var database: FirebaseDatabase?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Main"

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        //init firebaseDatabase
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Profile")

        checkUser()

        //init recyclerview
        val restaurantModel = getRestaurantData()
        initRecyclerView(restaurantModel)

//        //handle logout
//        binding.logoutBtn.setOnClickListener {
//            firebaseAuth.signOut()
//            checkUser()
//        }
    }

    private fun checkUser() {
//        //check user is logged in or not
//        val firebaseUser = firebaseAuth.currentUser
//        if (firebaseUser != null) {
//            //user not null, logged in
//            val email = firebaseUser.email
//            val userReference = databaseReference?.child(firebaseUser?.uid!!)
//            userReference?.addValueEventListener(object: ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val firstname = snapshot.child("firstname").value.toString().trim()
//                    val lastname = snapshot.child("lastname").value.toString().trim()
//                    binding.nameText.text = firstname+" "+lastname
//                }
//                override fun onCancelled(error: DatabaseError) {
////                    Toast.makeText(this@MainActivity, "DatabaseError", Toast.LENGTH_SHORT).show()
//                    val firstname = "DatabaseError"
//                    val lastname = "DatabaseError"
//                    binding.nameText.text = firstname+" "+lastname
//                }
//            })
//
//            //set to text view
//            binding.emailText.text = email
//        }
//        else {
//            //user is null, not logged in, goto login activity
//            startActivity(Intent(this,LoginActivity::class.java))
//            finish()
//        }
    }

    private fun initRecyclerView(restaurantList: List<RestaurentModel?>?) {
//        val recyclerViewRestaurant = findViewById<RecyclerView>(R.id.recyclerViewRestaurant)
//        recyclerViewRestaurant.layoutManager = LinearLayoutManager(this)
//        val adapter = RestaurantListAdapter(restaurantList, this)
//        recyclerViewRestaurant.adapter =adapter
    }

    private fun getRestaurantData(): List<RestaurentModel?>? {
        val inputStream: InputStream = resources.openRawResource(R.raw.restaurent)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader: Reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var n : Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }

        }catch (e: Exception){}
        val jsonStr: String = writer.toString()
        val gson = Gson()
        val restaurantModel = gson.fromJson<Array<RestaurentModel>>(jsonStr, Array<RestaurentModel>::class.java).toList()
        return restaurantModel
    }

//    override fun onItemClick(restaurantModel: RestaurentModel) {
//        val intent = Intent(this@MainActivityBackup, RestaurantMenuActivity::class.java)
//        intent.putExtra("RestaurantModel", restaurantModel)
//        startActivity(intent)
//    }
}