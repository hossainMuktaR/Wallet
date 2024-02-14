package com.hossain.wallet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hossain.wallet.data.model.ReceivedBill
import com.hossain.wallet.data.model.SpendBill
import kotlinx.coroutines.flow.Flow

@Dao
interface SpendBillDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(spendBill: SpendBill)

    @Update
    suspend fun update(spendBill: SpendBill)

    @Delete
    suspend fun delete(spendBill: SpendBill)

    @Query("SELECT * FROM spendbill ORDER BY date DESC")
    fun getAll(): Flow<List<SpendBill>>

    @Query("SELECT * FROM spendbill WHERE type = :billType ORDER BY date DESC")
    fun getAllByBillType(billType: String): Flow<List<SpendBill>>

    @Query("SELECT * FROM spendbill WHERE id = :id")
    fun getById(id: Int): Flow<SpendBill>

    @Query("SELECT SUM(amount) FROM spendbill")
    suspend fun getTotalSpendAmount(): Int

    @Query("SELECT SUM(amount) FROM spendbill WHERE type = :billType")
    suspend fun getTotalSpendAmountByType(billType: String): Int
}