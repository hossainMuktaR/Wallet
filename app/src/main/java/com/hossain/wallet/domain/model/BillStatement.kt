package com.hossain.wallet.domain.model

data class BillStatement(
    val amount: Int,
    val date: Long,
    val title: String,
    val type: BillType,
    val note: String?
): Bill