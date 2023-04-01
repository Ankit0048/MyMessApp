package `in`.day1.mymessapp.Activity

import `in`.day1.mymessapp.databinding.ActivityWeekMenuBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class WeekMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeekMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWeekMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        
    }
}