package com.hossain.wallet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hossain.wallet.data.model.BorrowBill
import kotlinx.coroutines.flow.Flow

@Dao
interface BorrowBillDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(borrowBill: BorrowBill)

    @Update
    suspend fun update(borrowBill: BorrowBill)

    @Delete
    suspend fun delete(borrowBill: BorrowBill)

    @Query("SELECT * FROM borrowbill ORDER BY date DESC")
    fun getAll(): Flow<List<BorrowBill>>

    @Query("SELECT * FROM borrowbill WHERE id = :id")
    fun getById(id: Int): Flow<BorrowBill>
}