package `in`.day1.mymessapp.Activity

import `in`.day1.mymessapp.Activity.Adapters.FoodItemAdapter
import `in`.day1.mymessapp.Activity.Adapters.WeekItemAdapter
import `in`.day1.mymessapp.Activity.Firebase.FireStoreClass
import `in`.day1.mymessapp.Activity.Models.Food
import `in`.day1.mymessapp.R
import `in`.day1.mymessapp.databinding.ActivityWeekMenuBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager


class WeekMenuActivity : BaseActivity() {

    private lateinit var binding: ActivityWeekMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        
        binding = ActivityWeekMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionBar()

        showProgressDialog("Please Wait  ")
        FireStoreClass().setWeekMenu(this)

    }
    private fun setUpActionBar() {
        val toolbar : Toolbar = binding.toolbarWeekmenuActivity
        toolbar.setTitle("WEEKLY MENU")
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
//            Drawer toggle
            onBackPressed()
        }
    }

    fun setUpDataToRecylclerView(itemList: ArrayList<HashMap<String, String>>
    ) {

        hideProgressDialog()
        if(itemList.isNotEmpty()) {
            val itemAdapter = WeekItemAdapter(itemList)
            binding.weekRecycleView.layoutManager = LinearLayoutManager(this@WeekMenuActivity)
            binding.weekRecycleView.adapter = itemAdapter
            binding.weekRecycleView.visibility = View.VISIBLE

        }
        else {
            binding.weekRecycleView.visibility = View.GONE

        }
    }
}