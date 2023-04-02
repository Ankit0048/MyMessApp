package `in`.day1.mymessapp.Activity

import `in`.day1.mymessapp.Activity.Adapters.FoodItemAdapter
import `in`.day1.mymessapp.Activity.Firebase.FireStoreClass
import `in`.day1.mymessapp.Activity.Models.Food
import `in`.day1.mymessapp.Activity.Models.History
import `in`.day1.mymessapp.Activity.TimeCurrent.TimeCurrent
import `in`.day1.mymessapp.Activity.Utils.Constants
import `in`.day1.mymessapp.R
import `in`.day1.mymessapp.databinding.ActivityConsumeMealBinding
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ConsumeMeal : BaseActivity(){

    private lateinit var binding: ActivityConsumeMealBinding
    private lateinit var MealType: String
    private lateinit var mPresentState: History
    private var reviewed: Int = 0
    private var consumed: Int = 0
    private var currentBalance: Int = 0
    private var FoodPrice: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConsumeMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        MealType = intent.getStringExtra(Constants.PASS_DAY).toString()
        showProgressDialog("Loading Items ")
        setUpActionBar()
//        Using Firestore to get the data
        FireStoreClass().getFoodItems(this, MealType)
        FireStoreClass().getStatusToday(this)

        binding.ConsumeBtn.setOnClickListener {
            consumed = 1
            FireStoreClass().loginUser(this)

        }

        binding.reviewSubmit.setOnClickListener {
            if (binding.ratingbar.rating.toInt() != 0) {
                reviewed = binding.ratingbar.rating.toInt()
                val reviewMap = getChangesHistory()
                FireStoreClass().updateHistory(this, reviewMap)
                FireStoreClass().reviewChange(MealType, reviewed)

            }else {
                showErrorSnackBar("Enter A Review Rating")
            }
        }

    }

    private fun setUpActionBar() {
        val toolbar : Toolbar = binding.toolbarConsumeActivity
        toolbar.setTitle(MealType)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    fun setUpDataToRecylclerView(food: Food
                                         ) {
        FoodPrice = food.price
        val taskList = ArrayList<String>(food.FoodItems.split(","))
        binding.priceMeal.text = "$MealType PRICE Rs- $FoodPrice"
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
            binding.ConsumeBtn.visibility = View.GONE
            binding.detailConsume.text = binding.detailConsume.text.toString()+TimeCurrent().currentYYYYMMDD()
            binding.ResultLayout.visibility = View.VISIBLE
        }
        if (consumed == 1 && reviewed == 0) {
            binding.ReviewLayout.visibility = View.VISIBLE
        }
        if (consumed == 1 && reviewed != 0) {
            binding.ReviewLayout.visibility = View.GONE
        }
    }

    private fun getChangesHistory(): HashMap<String, Any> {
        val mapHash = HashMap<String, Any>()

        when(MealType) {
            Constants.BREAKFAST -> {
                if (mPresentState.reviewedBREAKFAST != reviewed) {
                    mapHash["reviewedBREAKFAST"] = reviewed

                }
                if (mPresentState.tookBREAKFAST != consumed) {
                    mapHash["tookBREAKFAST"] = consumed
                    mapHash[Constants.BREAKFAST.lowercase()] = FoodPrice
                    mPresentState.tookBREAKFAST = consumed

                }
            }
            Constants.LUNCH -> {
                if (mPresentState.reviewedLUNCH != reviewed) {
                    mapHash["reviewedLUNCH"] = reviewed
                }
                if (mPresentState.tookLUNCH != consumed) {
                    mapHash["tookLUNCH"] = consumed
                    mapHash[Constants.LUNCH.lowercase()] = FoodPrice
                    mPresentState.tookLUNCH = consumed

                }
            }
            Constants.SNACKS -> {
                if (mPresentState.reviewedSNACKS!= reviewed) {
                    mapHash["reviewedSNACKS"] = reviewed
                }
                if (mPresentState.tookSNACKS != consumed) {
                    mapHash["tookSNACKS"] = consumed
                    mapHash[Constants.SNACKS.lowercase()] = FoodPrice
                    mPresentState.tookSNACKS = consumed
                }
            }
            Constants.DINNER -> {
                if (mPresentState.reviewedDINNER != reviewed) {
                    mapHash["reviewedDINNER"] = reviewed

                }
                if (mPresentState.tookDINNER != consumed) {
                    mapHash["tookDINNER"] = consumed
                    mapHash[Constants.DINNER.lowercase()] = FoodPrice
                    mPresentState.tookDINNER = consumed

                }
            }
        }
        return mapHash
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

    fun profileUpdateSuccess() {
        hideProgressDialog()
        val historyHashMap = getChangesHistory()
        if (historyHashMap.isNotEmpty())
            FireStoreClass().updateHistory(this, historyHashMap)

    }

    fun getBalance(balance: Int) {
        currentBalance = balance
        val balanceChange = hashMapOf<String, Any>("balance" to currentBalance-FoodPrice)
        FireStoreClass().updateUserProfileData(this, balanceChange)
    }
}