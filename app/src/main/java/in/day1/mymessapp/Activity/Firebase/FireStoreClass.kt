package `in`.day1.mymessapp.Activity.Firebase

import `in`.day1.mymessapp.Activity.IntroActivity
import `in`.day1.mymessapp.Activity.Models.User
import `in`.day1.mymessapp.Activity.Utils.Constants
import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FireStoreClass {
    private var mFireStore = FirebaseFirestore.getInstance()

    //    registering an user in the firebase store
    fun registerUser(acitivity: IntroActivity, userInfo : User) {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId()).set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                acitivity.userRegisteredSuccess()
            }.addOnFailureListener {
                Log.e("Error :" ,"Registering FireStore Database Failed")
            }
    }

    //    Function to get the databases from the firestore


    //    function to return the user Id
    fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser?.uid ?: ""
    }
}