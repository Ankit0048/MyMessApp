package `in`.day1.mymessapp.Activity


import `in`.day1.mymessapp.Activity.Firebase.FireStoreClass
import `in`.day1.mymessapp.databinding.ActivitySplashBinding
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Setting the binding of the Application
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

//         To set the window as full screen modernly deprecated
        window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )

        val typeface : Typeface = Typeface.createFromAsset(assets, "CARBON-DROID.ttf")
        binding.tvAppName.typeface = typeface

        Timer().schedule(2000) {
            if (FireStoreClass().getCurrentUserId() != "") {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
            }
            else {
                val intent: Intent = Intent(this@SplashActivity, IntroActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

    }
}