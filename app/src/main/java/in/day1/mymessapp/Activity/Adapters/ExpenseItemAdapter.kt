package `in`.day1.mymessapp.Activity.Adapters

import `in`.day1.mymessapp.Activity.Models.History
import `in`.day1.mymessapp.Activity.Utils.Constants
import `in`.day1.mymessapp.databinding.ExpensemonthRowBinding
import `in`.day1.mymessapp.databinding.WeekmenuRowBinding
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ExpenseItemAdapter(private val item: ArrayList<Pair<History, String>>)
    : RecyclerView.Adapter<ExpenseItemAdapter.ViewHolder>(){

    class ViewHolder(binding: ExpensemonthRowBinding): RecyclerView.ViewHolder(binding.root) {
        val day = binding.day
        val card = binding.card
        val breakfastCost = binding.BREAKFAST
        val lunchCost = binding.LUNCH
        val snackCost = binding.SNACKS
        val dinnerCost = binding.DINNER
        val paidCost = binding.paidfood
        val totalCost = binding.totalCost

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ExpensemonthRowBinding.inflate(
                LayoutInflater.from(parent.context), parent,
            false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var x = item[position].first
        holder.day.text = "DATE - " + item[position].second
        holder.breakfastCost.text ="RS "+ x.breakfast.toString()
        holder.lunchCost.text ="RS " + x.lunch.toString()
        holder.snackCost.text = "RS " + x.snacks.toString()
        holder.dinnerCost.text ="RS " + x.dinner.toString()
        holder.paidCost.text = "PAID COST: Rs " + x.paid.toString()
        holder.totalCost.text = "TOTAL : Rs " + (x.breakfast + x.lunch + x.snacks + x.dinner +x.paid)
            .toString()

    }

    override fun getItemCount(): Int {
        return item.size
    }




}