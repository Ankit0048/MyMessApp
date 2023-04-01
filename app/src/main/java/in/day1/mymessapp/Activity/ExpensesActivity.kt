package `in`.day1.mymessapp.Activity

import `in`.day1.mymessapp.Activity.Adapters.ExpenseItemAdapter
import `in`.day1.mymessapp.Activity.Adapters.WeekItemAdapter
import `in`.day1.mymessapp.Activity.Firebase.FireStoreClass
import `in`.day1.mymessapp.Activity.Models.History
import `in`.day1.mymessapp.R
import `in`.day1.mymessapp.databinding.ActivityExpensesBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager

class ExpensesActivity : BaseActivity(){

    private lateinit var binding : ActivityExpensesBinding
    private var mSharedList = ArrayList<Pair<History, String>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = ActivityExpensesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionBar()

        showProgressDialog("Please Wait ")
        FireStoreClass().getThisMonthUserHistory(this, mSharedList, x = 31)
    }

    private fun setUpActionBar() {
        val toolbar : Toolbar = binding.toolbarExpensesActivity
        toolbar.setTitle("THIS MONTH'S EXPENSES")
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
//            Drawer toggle
            onBackPressed()
        }
    }

    fun setUpRecyclerView(itemList: ArrayList<Pair<History, String>>) {
        hideProgressDialog()
        if(itemList.isNotEmpty()) {
            val itemAdapter = ExpenseItemAdapter(itemList)
            binding.expensesRecylcer.layoutManager = LinearLayoutManager(this@ExpensesActivity)
            binding.expensesRecylcer.adapter = itemAdapter
            binding.expensesRecylcer.visibility = View.VISIBLE

        }
        else {
            binding.expensesRecylcer.visibility = View.GONE

        }
    }


}