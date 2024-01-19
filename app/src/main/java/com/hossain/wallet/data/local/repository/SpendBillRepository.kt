package com.hossain.wallet.data.local.repository

import com.hossain.wallet.data.local.dao.SpendBillDao
import com.hossain.wallet.data.model.SpendBill
import com.hossain.wallet.domain.model.Bill
import com.hossain.wallet.domain.repository.DefaultRepository
import kotlinx.coroutines.flow.Flow

class SpendBillRepository(
    private val billDao: SpendBillDao
): DefaultRepository<SpendBill> {
    override suspend fun insertBill(bill: SpendBill) {
        billDao.insert(bill)
    }

    override suspend fun updateBill(bill: SpendBill) {
        billDao.update(bill)
    }

    override suspend fun deleteBill(bill: SpendBill) {
        billDao.delete(bill)
    }

    override fun getAll(): Flow<List<SpendBill>> {
        return billDao.getAll()
    }

    override fun getById(id: Int): Flow<SpendBill> {
        return billDao.getById(id)
    }
}