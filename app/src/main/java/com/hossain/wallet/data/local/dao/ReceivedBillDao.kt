package com.hossain.wallet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hossain.wallet.data.model.BorrowBill
import com.hossain.wallet.data.model.ReceivedBill
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceivedBillDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(receivedBill: ReceivedBill)

    @Update
    suspend fun update(receivedBill: ReceivedBill)

    @Delete
    suspend fun delete(receivedBill: ReceivedBill)

    @Query("SELECT * FROM receivedbill ORDER BY date DESC")
    fun getAll(): Flow<List<ReceivedBill>>

    @Query("SELECT * FROM receivedbill WHERE id = :id")
    fun getById(id: Int): Flow<ReceivedBill>
}