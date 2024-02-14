package com.hossain.wallet.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun getRemainAmountPercentage(total: Int, spend: Int): Float? {
    if(total == 0 && spend == 0) return null
    if(spend == 0 && total > 0) return 1f
    return 1f-(spend.toFloat()/total.toFloat())
}