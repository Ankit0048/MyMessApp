package `in`.day1.mymessapp.Activity.Firebase

import `in`.day1.mymessapp.Activity.IntroActivity
import `in`.day1.mymessapp.Activity.MainActivity
import `in`.day1.mymessapp.Activity.Models.User
import `in`.day1.mymessapp.Activity.MyProfileActivity
import `in`.day1.mymessapp.Activity.Utils.Constants
import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FireStoreClass {
    private var mFireStore = FirebaseFirestore.getInstance()
    private var docExist: Boolean = false

    //    registering an user in the firebase store
    fun registerUser(acitivity: IntroActivity, userInfo : User) {
//        Add the user only if it is not present in the firestore till now
        mFireStore.collection(Constants.USERS).document(getCurrentUserId()).get()
            .addOnSuccessListener {
                    document ->
                val loggedUser = document.toObject(User::class.java)
                if (loggedUser == null) {
                    mFireStore.collection(Constants.USERS)
                        .document(getCurrentUserId()).set(userInfo, SetOptions.merge())
                        .addOnSuccessListener {
//                            Move to the next activity upon the success
                            acitivity.userRegisteredSuccess()
                        }.addOnFailureListener {
                            acitivity.hideProgressDialog()
                            Log.e("Error :" ,"Registering FireStore Database Failed")
                        }
                }
                else {
                    acitivity.userRegisteredSuccess()
                }
            }
            .addOnFailureListener {
                acitivity.hideProgressDialog()
                docExist = false

            }


    }
//Login the user in different activities as per the need
    fun loginUser(activity: Activity) {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener {
                    document ->
                val loggedUser = document.toObject(User::class.java)
                when(activity){
                    is MainActivity -> {
                        activity.updateNavigationUserDetails(loggedUser!!)
                    }
                    is MyProfileActivity -> {
                        activity.setUserData(loggedUser!!)
                    }
                }
            }.addOnFailureListener {
                    e->
                when(activity){

                    is MainActivity -> {
                        activity.hideProgressDialog()
                    }

                    is MyProfileActivity -> {
                        activity.hideProgressDialog()
                    }
                }
                Log.e("Error Signing in", "Failed Login", e)
            }
    }

//    Function to update the user data so that the
    fun updateUserProfileData(activity: MyProfileActivity, userHashMap: HashMap<String, Any>) {
        mFireStore.collection(Constants.USERS).document(getCurrentUserId()).
        update(userHashMap).addOnSuccessListener {
            Log.i("UPDATE SUCCESS", "UPDATE SUCCESFULLY THE PROFILE")
            Toast.makeText(activity, "User Data Updated Successfully", Toast.LENGTH_SHORT).show()
            activity.profileUpdateSuccess()
        }.addOnFailureListener {
            Log.e("Error: ", "Whiile updating something went wrong")
        }

    }
    //    function to return the user Id
    fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser?.uid ?: ""
    }
}