package `in`.day1.mymessapp.Activity.Adapters

import `in`.day1.mymessapp.Activity.Utils.Constants
import `in`.day1.mymessapp.databinding.ItemRowBinding
import `in`.day1.mymessapp.databinding.WeekmenuRowBinding
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WeekItemAdapter(private val item: ArrayList<HashMap<String, String>>)
    :RecyclerView.Adapter<WeekItemAdapter.ViewHolder>(){

    class ViewHolder(binding: WeekmenuRowBinding):RecyclerView.ViewHolder(binding.root) {
        val day = binding.day
        val card = binding.card
        val breakfastItems = binding.BREAKFAST
        val lunchItems = binding.LUNCH
        val snackItems = binding.SNACKS
        val dinnerItems = binding.DINNER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(WeekmenuRowBinding.inflate(LayoutInflater.from(parent.context), parent,
        false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.day.text = Constants.ORDERDAY[position]
        holder.breakfastItems.text = item[position][Constants.BREAKFAST]
        holder.lunchItems.text = item[position][Constants.LUNCH]
        holder.snackItems.text = item[position][Constants.SNACKS]
        holder.dinnerItems.text = item[position][Constants.DINNER]

        if(position %2 == 0) {
            holder.card.setCardBackgroundColor(Color.parseColor("#C1C1C1"))
        }
        else {
            holder.card.setCardBackgroundColor(Color.parseColor("#EBEBEB"))
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }


}