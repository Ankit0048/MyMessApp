package `in`.day1.mymessapp.Activity

import `in`.day1.mymessapp.R
import `in`.day1.mymessapp.databinding.ActivityBaseBinding
import `in`.day1.mymessapp.databinding.ProgressDialogBinding
import android.app.Dialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.concurrent.schedule

open class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding
    private var doubleBackToExitPressed = false

    private lateinit var mProgressDialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Setting the content view of the activity
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    //    Setting the dialog window using the dialog function
    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(this)
//    Attaching the binding to the different
        val dialogBinding = ProgressDialogBinding.inflate(
            LayoutInflater.from(
            applicationContext
        ))
        mProgressDialog.setContentView(dialogBinding.root)
        dialogBinding.tvText.text = text
        mProgressDialog.show()

    }

    //    Function to close the custom dialog
    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    //    Get the user ID from the getCurrent User ID for the user
    fun getCurrentUserID(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    //  Pressing the doubleBack it exits
    fun doubleBackToExit() {
        if(doubleBackToExitPressed) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressed = true
        Toast.makeText(this, "To Exit press Again", Toast.LENGTH_SHORT).show()

        Timer().schedule(2000) {
            doubleBackToExitPressed = false
        }
    }

    //    To show the error we will be using the snack bar
    fun showErrorSnackBar(message: String) {
        val snackBar = Snackbar.make(findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT)

        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(Color.parseColor("#F11100"))
        snackBar.show()
    }
}