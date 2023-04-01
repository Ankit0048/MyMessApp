package `in`.day1.mymessapp.Activity.Firebase

import `in`.day1.mymessapp.Activity.ConsumeMeal
import `in`.day1.mymessapp.Activity.IntroActivity
import `in`.day1.mymessapp.Activity.MainActivity
import `in`.day1.mymessapp.Activity.Models.Food
import `in`.day1.mymessapp.Activity.Models.History
import `in`.day1.mymessapp.Activity.Models.StarSystem
import `in`.day1.mymessapp.Activity.Models.User
import `in`.day1.mymessapp.Activity.MyProfileActivity
import `in`.day1.mymessapp.Activity.TimeCurrent.TimeCurrent
import `in`.day1.mymessapp.Activity.Utils.Constants
import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.Timer

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
                    is ConsumeMeal -> {
                        activity.getBalance(loggedUser!!.balance)
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
                    is ConsumeMeal -> {
                        TODO()
                    }
                }
                Log.e("Error Signing in", "Failed Login", e)
            }
    }

//    Function to update the user data so that the
    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
        mFireStore.collection(Constants.USERS).document(getCurrentUserId()).
        update(userHashMap).addOnSuccessListener {
            Log.i("UPDATE SUCCESS", "UPDATE SUCCESFULLY THE PROFILE")
            Toast.makeText(activity, "User Data Updated Successfully", Toast.LENGTH_SHORT).show()
            when(activity) {
                is MyProfileActivity -> {
                    activity.profileUpdateSuccess()
                }
                is ConsumeMeal -> {
                    activity.profileUpdateSuccess()
                }
            }

        }.addOnFailureListener {
            Log.e("Error: ", "Whiile updating something went wrong")

        }

    }
    //    function to return the user Id
    fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser?.uid ?: ""
    }

//    Get food items from firestore
    fun getFoodItems(activity: Activity, mealType: String) {
        Constants.DAY[TimeCurrent().getDayOfWeek()]?.let {
            mFireStore.collection(it)
                .document(mealType)
                .get()
                .addOnSuccessListener { document ->
                    val Food = document.toObject(Food::class.java)
                    when(activity){

                        is ConsumeMeal -> {
                            activity.setUpDataToRecylclerView(Food!!)

                        }
                    }
                }.addOnFailureListener { e->
                    when(activity){

                        is ConsumeMeal -> {
                            activity.hideProgressDialog()
                        }
                    }
                    Log.e("Error Signing in", "Failed Login", e)
                }
        }
    }
//    Create a document of such with the given info
    fun intiateHistoryToday(activity: MainActivity, history: History) {
    mFireStore.collection(Constants.USERS).document(getCurrentUserId())
        .collection(Constants.HISTORY).
        document(TimeCurrent().currentYYYYMMDD()).get()
        .addOnSuccessListener {
                document ->
            val data = document.toObject(History::class.java)
            if (data == null) {
                mFireStore.collection(Constants.USERS)
                    .document(getCurrentUserId())
                    .collection(Constants.HISTORY)
                    .document(TimeCurrent().currentYYYYMMDD())
                    .set(history, SetOptions.merge())
                    .addOnSuccessListener {
//                            Move to the next activity upon the success
                        Log.i("HISTORY INFO", "Created a data set on this date")
                    }.addOnFailureListener {
                        Log.e("Error :" ,"Current not Added")
                    }
            }
            else {
                Log.i("PRSENT ALREADY", "INFO IS PRESENT ALREADY")
            }
        }
        .addOnFailureListener {
            Log.e("Error : ", "Reaching the user Data")

        }


    }

    fun getStatusToday(activity: Activity) {
        mFireStore.collection(Constants.USERS).document(getCurrentUserId())
            .collection(Constants.HISTORY).
            document(TimeCurrent().currentYYYYMMDD()).get()
            .addOnSuccessListener { document ->
                val data = document.toObject(History::class.java)
                when(activity) {
                    is ConsumeMeal -> {
                        activity.currentStatus(data)
                    }
                }
            }.addOnFailureListener {
                when(activity) {
                    is ConsumeMeal -> {
                        activity.hideProgressDialog()
                        Log.e("Error :", "Unable to get Data")
                    }
                }
            }
    }

    fun updateHistory(activity: Activity, dataHashMap: HashMap<String, Any>) {
        mFireStore.collection(Constants.USERS).document(getCurrentUserId())
            .collection(Constants.HISTORY).
            document(TimeCurrent().currentYYYYMMDD()).update(dataHashMap)
            .addOnSuccessListener {
                when(activity){
                    is ConsumeMeal -> {
                        activity.setupVisibility()
                    }
                }
            }
    }

    fun initateReview(activity: MainActivity, star: StarSystem, mealtype: String) {
        val tt =TimeCurrent().currentYYYYMMDD()
        mFireStore.collection(mealtype).document(tt)
            .get().addOnSuccessListener {
                document ->
                val data = document.toObject(StarSystem::class.java)
                if (data == null) {
                    mFireStore.collection(mealtype)
                        .document(tt)
                        .set(star, SetOptions.merge())
                        .addOnSuccessListener {
//                            Move to the next activity upon the success
                            Log.i("HISTORY INFO", "Created a data set on this date")
                        }.addOnFailureListener {
                            Log.e("Error :" ,"Current not Added")
                        }
                }
                else {
                    Log.i("PRSENT ALREADY", "INFO IS PRESENT ALREADY")
                }
            }
            .addOnFailureListener {
                Log.e("Error : ", "Reaching the user Data")

            }
    }

    fun reviewChange(mealType: String, rating: Int) {
        val tt = TimeCurrent().currentYYYYMMDD()
        mFireStore.collection(mealType+Constants.REVIEW).document(tt)
            .get().addOnSuccessListener {
                    document ->

                var x = document.get("one")
                mFireStore.collection(mealType+Constants.REVIEW).document(tt)
                    .update(Constants.starIndex[rating]!!, x.toString().toInt() +1
                        ).addOnSuccessListener {
                            Log.i("Updated", "Count incremented for the Rating")
                    }

                }.addOnFailureListener {
                    Log.e("Error :", "Error Updating Rating Count")
            }
        }


}