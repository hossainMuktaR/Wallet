package com.hossain.wallet.data.local.repository

import com.hossain.wallet.data.local.dao.OwedBillDao
import com.hossain.wallet.data.model.OwedBill
import com.hossain.wallet.domain.model.BillType
import com.hossain.wallet.domain.repository.DefaultRepository
import kotlinx.coroutines.flow.Flow

class OwedBillRepository(
    private val billDao: OwedBillDao
): DefaultRepository<OwedBill> {
    override suspend fun insertBill(bill: OwedBill) {
        billDao.insert(bill)
    }

    override suspend fun updateBill(bill: OwedBill) {
        billDao.update(bill)
    }

    override suspend fun deleteBill(bill: OwedBill) {
        billDao.delete(bill)
    }

    override fun getAllByBillType(billType: BillType): Flow<List<OwedBill>> {
        if(billType == BillType.All){
            return billDao.getAll()
        }else {
            return billDao.getAllByBillType(billType.name)
        }
    }

    override fun getById(id: Int): Flow<OwedBill> {
        return billDao.getById(id)
    }
}