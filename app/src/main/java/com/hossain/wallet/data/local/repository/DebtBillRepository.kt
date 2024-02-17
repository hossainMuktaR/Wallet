package com.hossain.wallet.data.local.repository

import com.hossain.wallet.data.local.dao.DebtBillDao
import com.hossain.wallet.data.model.DebtBill
import com.hossain.wallet.domain.model.BillType
import com.hossain.wallet.domain.repository.DefaultRepository
import kotlinx.coroutines.flow.Flow

class DebtBillRepository(
    private val billDao: DebtBillDao
): DefaultRepository<DebtBill> {

    override suspend fun upsert(bill: DebtBill) {
        billDao.upsert(bill)
    }

    override suspend fun deleteBill(bill: DebtBill) {
        billDao.delete(bill)
    }

    override fun getAllByBillType(billType: BillType): Flow<List<DebtBill>> {
        if(billType == BillType.All){
            return billDao.getAll()
        }else {
            return billDao.getAllByBillType(billType.name)
        }
    }

    override fun getById(id: Int): Flow<DebtBill> {
        return billDao.getById(id)
    }
}