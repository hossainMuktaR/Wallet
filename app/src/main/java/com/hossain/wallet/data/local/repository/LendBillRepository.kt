package com.hossain.wallet.data.local.repository

import com.hossain.wallet.data.local.dao.LendBillDao
import com.hossain.wallet.data.model.LendBill
import com.hossain.wallet.domain.model.Bill
import com.hossain.wallet.domain.repository.DefaultRepository
import kotlinx.coroutines.flow.Flow

class LendBillRepository(
    private val billDao: LendBillDao
): DefaultRepository<LendBill> {
    override suspend fun insertBill(bill: LendBill) {
        billDao.insert(bill)
    }

    override suspend fun updateBill(bill: LendBill) {
        billDao.update(bill)
    }

    override suspend fun deleteBill(bill: LendBill) {
        billDao.delete(bill)
    }

    override fun getAll(): Flow<List<LendBill>> {
        return billDao.getAll()
    }

    override fun getById(id: Int): Flow<LendBill> {
        return billDao.getById(id)
    }
}