package com.hossain.wallet.domain.repository

import com.hossain.wallet.data.model.ReceivedBill
import com.hossain.wallet.domain.model.Bill
import com.hossain.wallet.domain.model.BillType
import kotlinx.coroutines.flow.Flow

interface DefaultRepository<T> {

    suspend fun upsert(bill: T)
    suspend fun deleteBill(bill: T)

    fun getAllByBillType(billType: BillType = BillType.All): Flow<List<T>>

    fun getById(id: Int): Flow<T>
}