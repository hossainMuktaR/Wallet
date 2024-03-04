package com.hossain.wallet.data.local.dao

import com.hossain.wallet.data.model.ReceivedBill
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId


interface ReceivedBillDao{
    suspend fun upsert(receivedBill: ReceivedBill)

    suspend fun delete(receivedBill: ReceivedBill)

    fun getAll(): Flow<List<ReceivedBill>>

    fun getAllByBillType(billType: String): Flow<List<ReceivedBill>>

    fun getById(id: ObjectId): Flow<ReceivedBill>

    suspend fun getTotalReceivedAmount(): Int

    suspend fun getTotalReceivedAmountByType(billType: String): Int
}