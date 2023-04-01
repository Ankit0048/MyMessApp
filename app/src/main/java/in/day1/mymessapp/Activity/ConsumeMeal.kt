package `in`.day1.mymessapp.Activity

import `in`.day1.mymessapp.Activity.Adapters.FoodItemAdapter
import `in`.day1.mymessapp.Activity.Firebase.FireStoreClass
import `in`.day1.mymessapp.Activity.Models.Food
import `in`.day1.mymessapp.Activity.Models.History
import `in`.day1.mymessapp.Activity.TimeCurrent.TimeCurrent
import `in`.day1.mymessapp.Activity.Utils.Constants
import `in`.day1.mymessapp.R
import `in`.day1.mymessapp.databinding.ActivityConsumeMealBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager

class ConsumeMeal : BaseActivity(){

    private lateinit var binding: ActivityConsumeMealBinding
    private lateinit var MealType: String
    private lateinit var mPresentState: History
    var reviewed: Int = 0
    var consumed: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConsumeMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setUpActionBar()

        MealType = intent.getStringExtra(Constants.PASS_DAY).toString()
        showProgressDialog("Loading Items ")

//        Using Firestore to get the data
        FireStoreClass().getFoodItems(this, MealType)
        FireStoreClass().getStatusToday(this)

        binding.ConsumeBtn.setOnClickListener {

        }

        binding.reviewSubmit.setOnClickListener {
            if (binding.reviewText.text.toString() != "") {

            }else {
                showErrorSnackBar("Enter A Review ")
            }
        }

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
        binding.priceMeal.text = "Rs $price"
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
    fun setupVisibility() {

        if (TimeCurrent().currentHour() >= Constants.validTiming[MealType]!![0] &&
            TimeCurrent().currentHour() <= Constants.validTiming[MealType]!![1] && consumed==0) {
            binding.ConsumeBtn.visibility = View.VISIBLE
        }
        if (consumed==1) {
            binding.ResultLayout.visibility = View.VISIBLE
        }
        if (consumed ==1 && reviewed == 0) {
            binding.ReviewLayout.visibility = View.VISIBLE
        }
    }


    override fun onDestroy() {
        super.onDestroy()

    }

//    Getting the current User Status
    fun currentStatus(data: History?) {
        hideProgressDialog()
        Log.i("Data Extracted", "Found Today's data")
        mPresentState = data ?: History()
        when(MealType) {
            Constants.BREAKFAST -> {
                reviewed = mPresentState.reviewedBREAKFAST
                consumed = mPresentState.tookBREAKFAST
            }
            Constants.LUNCH -> {
                reviewed = mPresentState.reviewedLUNCH
                consumed = mPresentState.tookLUNCH
            }
            Constants.SNACKS -> {
                reviewed = mPresentState.reviewedSNACKS
                consumed = mPresentState.tookSNACKS
            }
            Constants.DINNER -> {
                reviewed = mPresentState.reviewedDINNER
                consumed = mPresentState.tookDINNER
            }
        }
        setupVisibility()
    }

}