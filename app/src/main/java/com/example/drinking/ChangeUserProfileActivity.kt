package com.example.drinking

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.drinking.databinding.ActivityChangeUserProfileBinding
import com.example.drinking.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ChangeUserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeUserProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri : Uri
    private lateinit var dialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        binding.saveBtn.setOnClickListener {

            showProgressBar()
            val firstname = binding.etFirstName.text.toString()
            val lastname = binding.etLastName.text.toString()
            val bio = binding.etBio.text.toString()

            val user = UserModel(firstname,lastname,bio)
            if (uid != null){

                databaseReference.child(uid).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful){

                        uploadProfilePic()
                        startActivity(Intent(this, UserProfileActivity::class.java))

                    } else{

                        hideProgressBar()
                        Toast.makeText(this@ChangeUserProfileActivity, "Lỗi! Hồ sơ không được cập nhật", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun uploadProfilePic() {
        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.profile}")
        storageReference = FirebaseStorage.getInstance().getReference("User/"+auth.currentUser?.uid+".jpg")
        storageReference.putFile(imageUri).addOnSuccessListener {

            hideProgressBar()
            Toast.makeText(this@ChangeUserProfileActivity, "Tải lên thành công", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {

            hideProgressBar()
            Toast.makeText(this@ChangeUserProfileActivity, "Tải lên thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressBar() {

        dialog = Dialog(this@ChangeUserProfileActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }

    private fun hideProgressBar() {

        dialog.dismiss()

    }
}