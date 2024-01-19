package com.hossain.wallet.data.local.repository

import com.hossain.wallet.data.local.dao.BorrowBillDao
import com.hossain.wallet.data.model.BorrowBill
import com.hossain.wallet.domain.model.Bill
import com.hossain.wallet.domain.repository.DefaultRepository
import kotlinx.coroutines.flow.Flow

class BorrowBillRepository(
    private val billDao: BorrowBillDao
): DefaultRepository<BorrowBill> {
    override suspend fun insertBill(bill: BorrowBill) {
        billDao.insert(bill)
    }

    override suspend fun updateBill(bill: BorrowBill) {
        billDao.update(bill)
    }

    override suspend fun deleteBill(bill: BorrowBill) {
        billDao.delete(bill)
    }

    override fun getAll(): Flow<List<BorrowBill>> {
        return billDao.getAll()
    }

    override fun getById(id: Int): Flow<BorrowBill> {
        return billDao.getById(id)
    }
}