package com.hossain.wallet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.hossain.wallet.data.model.ReceivedBill
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceivedBillDao{
    @Upsert
    suspend fun upsert(receivedBill: ReceivedBill)

    @Delete
    suspend fun delete(receivedBill: ReceivedBill)

    @Query("SELECT * FROM receivedbill ORDER BY date DESC")
    fun getAll(): Flow<List<ReceivedBill>>

    @Query("SELECT * FROM receivedbill WHERE type = :billType ORDER BY date DESC")
    fun getAllByBillType(billType: String): Flow<List<ReceivedBill>>

    @Query("SELECT * FROM receivedbill WHERE id = :id")
    fun getById(id: Int): Flow<ReceivedBill>
    @Query("SELECT SUM(amount) FROM receivedbill")
    suspend fun getTotalReceivedAmount(): Int
    @Query("SELECT SUM(amount) FROM receivedbill WHERE type = :billType")
    suspend fun getTotalReceivedAmountByType(billType: String): Int
}