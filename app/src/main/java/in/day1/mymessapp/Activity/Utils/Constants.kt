package `in`.day1.mymessapp.Activity.Utils

object Constants {

    const val GOOGLE_SIGN_IN_REQUEST_CODE = 1000
    const val USERS = "USERS"

    const val READ_STORAGE_PERMISSION_CODE = 1
    const val PICK_IMAGE_REQUEST_CODE = 2
    const val MY_PROFILE_REQUEST_CODE = 11

    const val BREAKFAST = "BREAKFAST"
    const val LUNCH = "LUNCH"
    const val DINNER = "DINNER"
    const val SNACKS = "SNACKS"

    const val IMAGE = "image"
    const val NAME = "name"
    const val MOBILE = "mobile"

    const val HISTORY = "HISTORY"

    const val PASS_DAY = "WEEKDAY"

    const val REVIEW = "REVIEW"

    val ORDERDAY = arrayOf("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY",
        "SATURDAY")

    val DAY = hashMapOf<Int, String>(1 to "SUNDAY", 2 to "MONDAY", 3 to "TUESDAY", 4 to "WEDNESDAY",
    5 to "THURSDAY", 6 to "FRIDAY", 7 to "SATURDAY")

    val validTiming = hashMapOf(BREAKFAST to arrayOf(7, 9), LUNCH to arrayOf(12, 13)
        , SNACKS to arrayOf(17, 18), DINNER to arrayOf(20, 21))

    val starIndex = hashMapOf(1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five")
}