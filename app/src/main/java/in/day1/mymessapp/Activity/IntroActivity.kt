package `in`.day1.mymessapp.Activity

import `in`.day1.mymessapp.Activity.Utils.Constants
import `in`.day1.mymessapp.databinding.ActivityIntroBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class IntroActivity : BaseActivity() {
    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding.introAdminBtn.setOnClickListener {
            val intent = Intent(this, AdminAcitivity::class.java)
            startActivity(intent)
        }

//        Move to the Login Activity from the intro activity

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().requestIdToken("977117427653-pfnabvkg01t2qag2q1d41mnkjgiqb1h8.apps.googleusercontent.com").build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.introLoginBtn.setOnClickListener {
            val signIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signIntent, Constants.GOOGLE_SIGN_IN_REQUEST_CODE)
        }
        mGoogleSignInClient.signOut()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.GOOGLE_SIGN_IN_REQUEST_CODE) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                if (task.isSuccessful ) {
                    Log.i("GOOGLE SIGNED IN : ", "SUCCESS")
                    try {
                        val accountG = task.getResult(ApiException::class.java)
                        if(accountG.email.toString() != FirebaseAuth.getInstance().currentUser?.email.toString()
                            && accountG.email.toString().endsWith("@iitbbs.ac.in"))  {
                            FirebaseAuth.getInstance()
                                .signInWithCredential(GoogleAuthProvider.getCredential(accountG.idToken,
                                        null
                                    )
                                ).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.i("User Authenticated: ", "Verified as part of the IIT BBS" +
                                                "Institutions")

                                    } else {
                                        Log.e("Logging in Failed", "Email Can't be verified")
                                    }
                                }
                        }
                        else {
                            Log.e("Institute : " , "Institute is Not same")
                            showErrorSnackBar("Don't Belong to this institute")

                        }
                    } catch (e: Exception) {
                        Log.e("Failure ", e.message!!)
                    }
                }
            }
        }

    }
}