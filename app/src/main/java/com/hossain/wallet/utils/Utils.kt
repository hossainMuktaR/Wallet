package com.hossain.wallet.utils

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun getRemainAmountPercentage(total: Int, spend: Int): Float? {
    if(total == 0 || spend == 0) return null
    return 1f-(spend.toFloat()/total.toFloat())
}


fun Long.toStringDate(): String = DateFormat.format("dd/MM/yyyy", Date(this)).toString()