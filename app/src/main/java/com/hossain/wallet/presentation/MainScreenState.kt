package com.hossain.wallet.presentation

import com.hossain.wallet.domain.model.BillCategory
import com.hossain.wallet.domain.model.BillStatement

data class MainScreenState(
    val totalAmount: Int = 0,
    val spendAmount: Int = 0,
    val remainPercentage: Float? = null,
    val statementList: List<BillStatement> = emptyList(),
    val billCategory: BillCategory = BillCategory.SPEND,
    val showDialog: Boolean = false,
    val showStatementDialog: Boolean = false
)
