package `in`.day1.mymessapp.Activity.TimeCurrent

import java.util.*

class TimeCurrent {
    val myCalender = Calendar.getInstance()
    val year = myCalender.get(Calendar.YEAR)
    val month = myCalender.get(Calendar.MONTH)
    val day = myCalender.get(Calendar.DAY_OF_WEEK)
    val date = myCalender.get(Calendar.DATE)
    val hour = myCalender.get(Calendar.HOUR_OF_DAY)

//Get the current date in format of string
    fun currentYYYYMMDD(): String {
        return "${year}_${month}_${date}"
    }

//    Get the current day of the week
    fun getDayOfWeek():Int {
        return day
    }

//    Get the current hour
    fun currentHour():Int {
        return hour
    }

}