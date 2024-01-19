package com.hossain.wallet.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hossain.wallet.domain.model.Bill

@Entity(
    tableName = "borrowbill"
)
data class BorrowBill(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Int,
    val lender: String,
    val date: Long,
    val note: String?
): Bill
