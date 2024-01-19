package com.hossain.wallet.data.local.repository

import com.hossain.wallet.data.local.dao.ReceivedBillDao
import com.hossain.wallet.data.model.ReceivedBill
import com.hossain.wallet.domain.repository.DefaultRepository
import kotlinx.coroutines.flow.Flow

class ReceivedBillRepository(
    private val billDao: ReceivedBillDao
): DefaultRepository<ReceivedBill> {
    override suspend fun insertBill(bill: ReceivedBill) {
        billDao.insert(bill)
    }

    override suspend fun updateBill(bill: ReceivedBill) {
        billDao.update(bill)
    }

    override suspend fun deleteBill(bill: ReceivedBill) {
        billDao.delete(bill)
    }

    override fun getAll(): Flow<List<ReceivedBill>> {
        return billDao.getAll()
    }

    override fun getById(id: Int): Flow<ReceivedBill> {
        return billDao.getById(id)
    }
}