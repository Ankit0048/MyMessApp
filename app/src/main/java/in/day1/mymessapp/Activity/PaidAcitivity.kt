package `in`.day1.mymessapp.Activity

import `in`.day1.mymessapp.Activity.Firebase.FireStoreClass
import `in`.day1.mymessapp.Activity.Models.User
import `in`.day1.mymessapp.R
import `in`.day1.mymessapp.databinding.ActivityPaidAcitivityBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar

class PaidAcitivity : BaseActivity() {
    private lateinit var binding: ActivityPaidAcitivityBinding
    var mUserBalance: Int = 0
    var currPaid: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaidAcitivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setUpActionBar()
        FireStoreClass().loginUser(this)
        FireStoreClass().getStatusToday(this)

        binding.paymentButton.setOnClickListener {
            if (binding.paymentButton.text == "REFRESH") {
                refreshed()
            }
            else if (binding.paymentText.text.toString() != "" &&
                binding.paymentText.text.toString().toInt() > 0)
            {
                val value = binding.paymentText.text.toString().toInt()
                mUserBalance -= value
                currPaid += value
                showProgressDialog("Please Wait")
                FireStoreClass().updateUserProfileData(this,
                    hashMapOf("balance" to mUserBalance))
                FireStoreClass().updateHistory(this, hashMapOf("paid" to currPaid))
            }
            else {
                showErrorSnackBar("ENTER VALID AMOUNT")
            }
        }

    }

    private fun setUpActionBar() {
        val toolbar : Toolbar = binding.toolbarPaymentActivity
        toolbar.setTitle("PAY")
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
//            Drawer toggle
            onBackPressed()
        }
    }

    fun setBalance(currBalance: Int) {
        mUserBalance = currBalance

    }

    fun paidBalanceToday(paid: Int) {
        currPaid = paid
    }

    fun setUpVisibilty() {
        hideProgressDialog()
        binding.payableText.text = "PAID"
        binding.paymentText.visibility = View.INVISIBLE
        binding.paymentButton.text = "REFRESH"
        binding.paymentImage.setImageResource(R.drawable.tickimage_bg)

    }

    private fun refreshed() {
        binding.paymentImage.setImageResource(R.drawable.payment_bga)
        binding.payableText.text = "PAYABLE AMOUNT"
        binding.paymentText.visibility = View.VISIBLE
        binding.paymentButton.text = "PAY"
    }
}