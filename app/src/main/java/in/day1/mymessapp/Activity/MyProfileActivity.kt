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
import android.view.WindowManager
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MyProfileActivity : BaseActivity() {


    private var mProfileImageUri : String = ""
    private lateinit var binding: ActivityMyProfileBinding
    private lateinit var mUserDetails: User

    private var mSelectedImageFileUri : Uri?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        Setting up the action bar here
        setupActionBar()

//        Getting the userDate from the firestore class and which further sets the data
        FireStoreClass().loginUser(this@MyProfileActivity)

//        on click listener
        binding.profileUserImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
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

        binding.profileUpdate.setOnClickListener {
            if(mSelectedImageFileUri!= null) {
                uploadUserImage()
            }
            else {
                showProgressDialog("Please Wait")
                updateUserProfileData()
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
        mUserDetails = loggedUser
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

//    Upload the image on the goolefirebase storage
    private fun uploadUserImage() {
        showProgressDialog("Please Wait")

        if (mSelectedImageFileUri != null) {

            val sRef : StorageReference = FirebaseStorage.getInstance().reference.
            child("USER_IMAGE " + System.currentTimeMillis()
            +  getFileExtension(mSelectedImageFileUri))

            sRef.putFile(mSelectedImageFileUri!!).addOnSuccessListener {
                taskSnapshot ->
                    Log.i("Firebase Image Url", taskSnapshot.metadata!!.reference!!.downloadUrl
                        .toString())

                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                    uri ->
                    Log.i("Downloadable Uri ", uri.toString())
                    mProfileImageUri = uri.toString()
                    hideProgressDialog()
                    updateUserProfileData()
                }
            }.addOnFailureListener{
                exception ->
                Log.e("Exception", exception.message!!)
            }

        }
    }
//    Function to get the file extension
    private fun getFileExtension(uri: Uri?): String? {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri!!))
    }

//    Function to updateProfile
    fun profileUpdateSuccess() {
        hideProgressDialog()
        setResult(Activity.RESULT_OK)
        finish()
    }

//    FUnction update user Profile data
    private fun updateUserProfileData() {
        val userHashMap  = HashMap<String, Any>()
        var anyChangesMade =false
        if (mProfileImageUri.isNotEmpty() && mProfileImageUri != mUserDetails.image) {
            userHashMap[Constants.IMAGE] = mProfileImageUri
            anyChangesMade = true
        }

        if (mUserDetails.name != binding.profileUsername.text.toString()) {
            userHashMap[Constants.NAME] = binding.profileUsername.text.toString()
            anyChangesMade = true
        }

        if (mUserDetails.mobile.toString() != binding.profileMobile.text.toString()) {
            try {
                anyChangesMade = true
                userHashMap[Constants.MOBILE] = binding.profileMobile.text.toString().toLong()
            } catch (e: Exception) {
                Log.e("Error", "Mobile Number is not Long error")
                Toast.makeText(this, "Enter Valid NUmber", Toast.LENGTH_SHORT).show()
            }
        }

            if (anyChangesMade) {
                FireStoreClass().updateUserProfileData(this, userHashMap)
            }
            else {
                hideProgressDialog()
                Toast.makeText(this, "No changes Made", Toast.LENGTH_SHORT).show()
            }
        }

}