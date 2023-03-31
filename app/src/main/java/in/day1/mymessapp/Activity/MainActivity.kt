package `in`.day1.mymessapp.Activity

import `in`.day1.mymessapp.Activity.Firebase.FireStoreClass
import `in`.day1.mymessapp.Activity.Models.User
import `in`.day1.mymessapp.R
import `in`.day1.mymessapp.databinding.ActivityMainBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {


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
                startActivity(intent)
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
}