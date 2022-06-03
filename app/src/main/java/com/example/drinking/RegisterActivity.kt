package com.example.drinking

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.drinking.databinding.ActivityRegisterBinding
import com.example.drinking.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class RegisterActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityRegisterBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri : Uri
    private lateinit var dialog : Dialog
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""
    private var confirmPassword = ""
    private var firstName = ""
    private var lastName = ""

    //Firebase RealtimeDatabase
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionbar, //enable back button
        actionBar = supportActionBar!!
        actionBar.title = "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating your account...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        //init firebaseDatabase
//        database = Firebase.database.reference
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")

        //handle click, open login activity
        binding.alreadyHaveAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        //handle click, begin register
        binding.registerBtn.setOnClickListener {
            //validate data before log in
            validateData()
        }
    }

    private fun validateData() {
        //get data
        email = binding.editTextTextEmailAddress.text.toString().trim()
        password = binding.editTextTextPassword.text.toString().trim()
        confirmPassword = binding.editTextTextConfirmPassword.text.toString().trim()
        firstName  = binding.editTextTextFirstname.text.toString().trim()
        lastName = binding.editTextTextLastname.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //invalid email format
            binding.editTextTextEmailAddress.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)) {
            //password isn't entered
            binding.editTextTextPassword.error = "Please enter password"
        }
        else if (TextUtils.isEmpty(confirmPassword)) {
            //password isn't entered
            binding.editTextTextConfirmPassword.error = "Please confirm password"
        }
        else if (password.length <6) {
            //password length is less than 6
            binding.editTextTextPassword.error = "Password must at least 6 characters long"
        }
        else if (password != confirmPassword) {
            //The confirm password is different from the password
            binding.editTextTextConfirmPassword.error = "Confirm password must same with password"
        }
        else if (TextUtils.isEmpty(firstName)) {
            //password isn't entered
            binding.editTextTextFirstname.error = "Please enter first name"
        }
        else if (TextUtils.isEmpty(lastName)) {
            //password isn't entered
            binding.editTextTextLastname.error = "Please enter last name"
        }
        else {
            //data is valid, continues register
            firebaseRegister()
        }
    }

    private fun firebaseRegister() {
        //show progress
        progressDialog.show()

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //register success
                progressDialog.dismiss()

                //get current user
                val firebaseUser = firebaseAuth.currentUser
                databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                val email = firebaseUser!!.email
                val firstname = binding.editTextTextFirstname.text.toString().trim()
                val lastname = binding.editTextTextLastname.text.toString().trim()
                //create user data in database
                val userId = firebaseUser!!.uid
//                val userModel = UserModel(firstName, lastName)
//                val userModel = UserModel("alex", "ho")
//                database.child("users").child(userId).setValue(userModel)
//                database.setValue("users")

                val user = UserModel(firstname,lastname)
                if (userId != null){
//
                    databaseReference.child(userId).setValue(user).addOnCompleteListener {
                        if (it.isSuccessful){

                            Toast.makeText(this, "Account create with email $email", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()

                        } else{

//                            hideProgressBar()
                            Toast.makeText(this@RegisterActivity, "Lỗi! Hồ sơ không được cập nhật", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            .addOnFailureListener { e->
                //sign up failed
                progressDialog.dismiss()
                Toast.makeText(this, "Sign Up failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

//    private fun hideProgressBar() {
//        dialog.dismiss()
//    }

//    private fun uploadProfilePic() {
//        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.profile}")
//        storageReference = FirebaseStorage.getInstance().getReference("User/"+auth.currentUser?.uid)
//        storageReference.putFile(imageUri).addOnSuccessListener {
//
//            hideProgressBar()
//            Toast.makeText(this@RegisterActivity, "Tải lên thành công", Toast.LENGTH_SHORT).show()
//
//        }.addOnFailureListener {
//
//            hideProgressBar()
//            Toast.makeText(this@RegisterActivity, "Tải lên thất bại", Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() //go back to previous activity, when back button of actionbar is clicked
        return super.onSupportNavigateUp()
    }
}