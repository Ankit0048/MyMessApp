package `in`.day1.mymessapp.Activity

import `in`.day1.mymessapp.Activity.Adapters.FoodItemAdapter
import `in`.day1.mymessapp.Activity.Firebase.FireStoreClass
import `in`.day1.mymessapp.Activity.Models.Food
import `in`.day1.mymessapp.Activity.Utils.Constants
import `in`.day1.mymessapp.R
import `in`.day1.mymessapp.databinding.ActivityConsumeMealBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager

class ConsumeMeal : BaseActivity(){

    private lateinit var binding: ActivityConsumeMealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConsumeMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setUpActionBar()

        val MealType: String = intent.getStringExtra(Constants.PASS_DAY).toString()
        showProgressDialog("Loading Items ")

        FireStoreClass().getFoodItems(this, MealType)


    }

    private fun setUpActionBar() {
        val toolbar : Toolbar = binding.toolbarConsumeActivity
        toolbar.title = "Item List"
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    fun setUpDataToRecylclerView(food: Food
                                         ) {
        val price = food.price
        val taskList: ArrayList<String> = ArrayList<String>(food.FoodItems.split(","))
        hideProgressDialog()
        if(taskList.isNotEmpty()) {
            val itemAdapter = FoodItemAdapter(taskList)
            binding.recyclerItems.layoutManager = LinearLayoutManager(this@ConsumeMeal)
            binding.recyclerItems.adapter = itemAdapter
            binding.recyclerItems.visibility = View.VISIBLE

        }
        else {
            binding.recyclerItems.visibility = View.GONE

        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}