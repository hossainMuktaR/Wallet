package com.hossain.wallet.domain.repository

import com.hossain.wallet.data.model.ReceivedBill
import com.hossain.wallet.domain.model.Bill
import kotlinx.coroutines.flow.Flow

interface DefaultRepository<T> {

    suspend fun insertBill(bill: T)

    suspend fun updateBill(bill: T)

    suspend fun deleteBill(bill: T)

    fun getAll(): Flow<List<T>>

    fun getById(id: Int): Flow<T>
}