package `in`.day1.mymessapp.Activity

import `in`.day1.mymessapp.Activity.Firebase.FireStoreClass
import `in`.day1.mymessapp.Activity.Models.User
import `in`.day1.mymessapp.Activity.Utils.Constants
import `in`.day1.mymessapp.R
import `in`.day1.mymessapp.databinding.ActivityMyProfileBinding
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

class MyProfileActivity : BaseActivity() {




    private lateinit var binding: ActivityMyProfileBinding

    private var mSelectedImageFileUri : Uri?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Setting up the action bar here
        setupActionBar()

//        Getting the userDate from the firestore class and which further sets the data
        FireStoreClass().loginUser(this@MyProfileActivity)

//        on click listener
        binding.profileUserImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
                showImageChoose()
            }
            else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.READ_STORAGE_PERMISSION_CODE
                )
            }
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(binding.myProfileToolbar)
        val toolbar = supportActionBar
        if (toolbar!= null) {
            toolbar.setDisplayHomeAsUpEnabled(true)
            toolbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
            toolbar.title = resources.getString(R.string.my_profile)
        }

        binding.myProfileToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    fun setUserData(loggedUser: User) {
//        Set the image of the user
        if (loggedUser.image != "") {
            Glide
                .with(this@MyProfileActivity)
                .load(loggedUser.image)
                .centerCrop()
                .placeholder(R.drawable.img)
                .into(
                    binding.profileUserImage
                )
        }
        binding.profileUserEmail.setText(loggedUser.email)
        binding.profileUsername.setText(loggedUser.name)
        if (loggedUser.mobile != (0).toLong()) {
            binding.profileMobile.setText(loggedUser.mobile.toString())
        }
    }

    //  On getting the permission result from the gallery we need to use the
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if( requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageChoose()
            }
            else {
                Toast.makeText(this, "The permission is denied. Go to App settings to grant permission", Toast.LENGTH_LONG).show()
            }
        }
    }

    //    Choose image from the gallery by creating the gallery chooser intent
    private fun showImageChoose(){
//    select open the gallery intent
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, Constants.PICK_IMAGE_REQUEST_CODE)
    }

    //    What to do from the result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.PICK_IMAGE_REQUEST_CODE
            && data!!.data != null) {
            mSelectedImageFileUri = data.data

            try {
                Glide
                    .with(this@MyProfileActivity)
                    .load(mSelectedImageFileUri)
                    .centerCrop()
                    .placeholder(R.drawable.img)
                    .into(
                        binding.profileUserImage
                    )
            }
            catch (e: Exception) {
                Log.e("Error Image Reading", e.message!!)
            }
        }
    }
}