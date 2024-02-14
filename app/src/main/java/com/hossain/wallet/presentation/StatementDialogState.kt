package com.hossain.wallet.presentation

import com.hossain.wallet.domain.model.BillCategory
import com.hossain.wallet.domain.model.BillType

data class StatementDialogState(
    val billType: BillType,
    val billCategory: BillCategory,
    val amount: Int = 0,
    val note: String? = null,
    val heroMessage: String = "",
)
