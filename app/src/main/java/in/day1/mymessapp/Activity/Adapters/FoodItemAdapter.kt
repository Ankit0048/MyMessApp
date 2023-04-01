package `in`.day1.mymessapp.Activity.Adapters

import `in`.day1.mymessapp.databinding.ItemRowBinding
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FoodItemAdapter (private val items: ArrayList<String>)
    :RecyclerView.Adapter<FoodItemAdapter.ViewHolder>(){

        class ViewHolder(binding: ItemRowBinding):RecyclerView.ViewHolder(binding.root) {
            val foodItemText = binding.mealItemText
            val imageItem = binding.mealItemImage
            val llItem = binding.mealItemLL
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.foodItemText.text = items[position]

        if(position %2 == 0) {
            holder.llItem.setBackgroundColor(Color.parseColor("#C1C1C1"))
        }
        else {
            holder.llItem.setBackgroundColor(Color.parseColor("#EBEBEB"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}