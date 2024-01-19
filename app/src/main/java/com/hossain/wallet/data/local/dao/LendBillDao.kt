package com.hossain.wallet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hossain.wallet.data.model.BorrowBill
import com.hossain.wallet.data.model.LendBill
import kotlinx.coroutines.flow.Flow

@Dao
interface LendBillDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(lendBill: LendBill)

    @Update
    suspend fun update(lendBill: LendBill)

    @Delete
    suspend fun delete(lendBill: LendBill)

    @Query("SELECT * FROM lendbill ORDER BY date DESC")
    fun getAll(): Flow<List<LendBill>>

    @Query("SELECT * FROM lendbill WHERE id = :id")
    fun getById(id: Int): Flow<LendBill>
}