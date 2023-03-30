package `in`.day1.mymessapp.Activity

import `in`.day1.mymessapp.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class SignUpActivity : AppCompatActivity() {

    val RC_SIGN_IN = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val button: SignInButton = findViewById(R.id.signUPgoogle)
            button.setOnClickListener {
                val signIntent = mGoogleSignInClient.signInIntent
                startActivityForResult(signIntent, RC_SIGN_IN)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_SIGN_IN) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val accountG = task.getResult(ApiException::class.java)
                    println(accountG.email)
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(accountG.email.toString(), "1212ankit").addOnCompleteListener{
                                task ->
                            if (task.isSuccessful) {
                                println("Autherized ")
//                                val fireBaseUser: FirebaseUser = task.result!!.user!!
//                                val registeredEmail = fireBaseUser.email!!
//                                val user = User(fireBaseUser.uid, name, registeredEmail)
//                                FirestoreClass().registerUser(this, user)

                            } else {
                                Log.e("Error loging in", "Firebase error")

                            }

                        }
//                    val idToken = accountG.idToken
//                    when {
//                        idToken != null -> {
//                            // Got an ID token from Google. Use it to authenticate
//                            // with Firebase.
//                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
//                            auth.signInWithCredential(firebaseCredential)
//                                .addOnCompleteListener(this) { task ->
//                                    if (task.isSuccessful) {
//                                        // Sign in success, update UI with the signed-in user's information
//                                        Log.d("LOGIN", "signInWithCredential:success")
//                                        val user = auth.currentUser
//
//                                    } else {
//                                        // If sign in fails, display a message to the user.
//                                        Log.w("ERROR", "signInWithCredential:failure", task.exception)   }
//                                }
//                                    }
//                        }

                }
                catch (e: Exception) {
                    Log.e("Error SIgning: ", e.message!!)
                }
            }
        }
    }


}