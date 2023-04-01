package `in`.day1.mymessapp.Activity

import `in`.day1.mymessapp.Activity.Firebase.FireStoreClass
import `in`.day1.mymessapp.R
import `in`.day1.mymessapp.databinding.ActivityBalanceBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class BalanceActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBalanceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBalanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionBar()
        FireStoreClass().loginUser(this)

    }
    private fun setUpActionBar() {
        val toolbar : Toolbar = binding.toolbarBalanceActivity
        toolbar.setTitle("YOUR BALANCE")
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
//            Drawer toggle
            onBackPressed()
        }

    }

    fun getBalance(balance: Int) {
        binding.balanceText.text = balance.toString()
    }
}