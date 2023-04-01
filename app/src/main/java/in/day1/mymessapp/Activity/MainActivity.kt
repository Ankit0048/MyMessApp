package `in`.day1.mymessapp.Activity

import `in`.day1.mymessapp.Activity.Firebase.FireStoreClass
import `in`.day1.mymessapp.Activity.Models.History
import `in`.day1.mymessapp.Activity.Models.StarSystem
import `in`.day1.mymessapp.Activity.Models.User
import `in`.day1.mymessapp.Activity.TimeCurrent.TimeCurrent
import `in`.day1.mymessapp.Activity.Utils.Constants
import `in`.day1.mymessapp.R
import `in`.day1.mymessapp.databinding.ActivityExpensesBinding
import `in`.day1.mymessapp.databinding.ActivityMainBinding
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, OnClickListener {


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

//        Setting the action bar
        setUpActionBar()

//        Using the Firestore class to set the info
        FireStoreClass().loginUser(this@MainActivity)
        binding.navView.setNavigationItemSelectedListener(this)

//        Firestore collection Creation for storage of History
        FireStoreClass().intiateHistoryToday(this@MainActivity, History())

//        Firestore collection Setting the Star System
        FireStoreClass().initateReview(this, StarSystem(), Constants.BREAKFAST+"REVIEW")
        FireStoreClass().initateReview(this, StarSystem(), Constants.LUNCH+"REVIEW")
        FireStoreClass().initateReview(this, StarSystem(), Constants.DINNER+"REVIEW")
        FireStoreClass().initateReview(this, StarSystem(), Constants.SNACKS+"REVIEW")

//        Attaching the on click listeners to the button
        binding.appBar.mainContentView.breakfastButton.setOnClickListener(this)
        binding.appBar.mainContentView.SnackButton.setOnClickListener(this)
        binding.appBar.mainContentView.LunchButton.setOnClickListener(this)
        binding.appBar.mainContentView.DinnerButton.setOnClickListener(this)
        binding.appBar.mainContentView.PaidButton.setOnClickListener(this)


    }
    private fun setUpActionBar() {
        val toolbar : Toolbar = binding.appBar.toolbarMainActivity
        toolbar.setTitle(R.string.app_name)
        toolbar.setNavigationIcon(R.drawable.ic_nav_drawer)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
//            Drawer toggle
            toggleDrawer()
        }

    }

    //    Function to change the drawer open or close depending on current status
    private fun toggleDrawer() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    //    Override the backpressing function
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else {
            doubleBackToExit()
        }
        super.onBackPressed()
    }

    //    Setting the options of the navigation button workspace
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_my_profile -> {
                val intent = Intent(this@MainActivity, MyProfileActivity::class.java)
                startActivityForResult(intent, Constants.MY_PROFILE_REQUEST_CODE)
            }
            R.id.nav_sign_out -> {
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(this, IntroActivity::class.java)
//                All the activities on top of the stack are removed and if the intent activity is
//                already present it will just load it from there
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            R.id.balance -> {
                val intent = Intent(this, BalanceActivity::class.java)
                startActivity(intent)
            }
            R.id.week_menu -> {
                val intent = Intent(this, WeekMenuActivity::class.java)
                startActivity(intent)
            }
            R.id.history -> {
                val intent = Intent(this, ExpensesActivity::class.java)
                startActivity(intent)
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    //    Set the NavigationUser Details according to the User
    fun updateNavigationUserDetails(loggedUser: User) {
        Glide
            .with(this)
            .load(loggedUser.image)
            .centerCrop()
            .placeholder(R.drawable.img)
            .into((binding.navView.getHeaderView(0))
                .findViewById(R.id.nav_user_image))

        (binding.navView.getHeaderView(0))
            .findViewById<TextView>(R.id.nav_userName).text = loggedUser.name
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && Constants.MY_PROFILE_REQUEST_CODE == requestCode) {
            FireStoreClass().loginUser(this)
        }
        else {
            Log.e("Cancelled", "Denied Update")
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id) {
                R.id.breakfastButton -> {
                    val intent = Intent(this, ConsumeMeal::class.java)
                    intent.putExtra(Constants.PASS_DAY, Constants.BREAKFAST)
                    startActivity(intent)
                }
                R.id.SnackButton -> {
                    val intent = Intent(this, ConsumeMeal::class.java)
                    intent.putExtra(Constants.PASS_DAY, Constants.SNACKS)
                    startActivity(intent)
                }
                R.id.LunchButton -> {
                    val intent = Intent(this, ConsumeMeal::class.java)
                    intent.putExtra(Constants.PASS_DAY, Constants.LUNCH)
                    startActivity(intent)
                }
                R.id.DinnerButton -> {
                    val intent = Intent(this, ConsumeMeal::class.java)
                    intent.putExtra(Constants.PASS_DAY, Constants.DINNER)
                    startActivity(intent)
                }
                R.id.PaidButton -> {
                    val intent = Intent(this, PaidAcitivity::class.java)
                    startActivity(intent)
                }

            }
        }
    }
}