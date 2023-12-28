package dem.llc.exitconfirmationsandroid.utils.date

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
val dateFormatter = SimpleDateFormat("dd.MM.yyyy")

@SuppressLint("SimpleDateFormat")
val timeFormatter = SimpleDateFormat("HH:mm")

@SuppressLint("SimpleDateFormat")
val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")

fun Date.getDateString() : String{
    return dateFormatter.format(this)
}

fun Date.getTimeString() : String{
    return timeFormatter.format(this)
}

fun Date.toSimpleString() : String{
    return formatter.format(this)
}

fun String.toDate() : Date? {
    return formatter.parse(this)
}