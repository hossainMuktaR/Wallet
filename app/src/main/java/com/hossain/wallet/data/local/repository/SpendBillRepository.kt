package com.hossain.wallet.data.local.repository

import com.hossain.wallet.data.local.dao.SpendBillDao
import com.hossain.wallet.data.model.SpendBill
import com.hossain.wallet.domain.model.Bill
import com.hossain.wallet.domain.model.BillType
import com.hossain.wallet.domain.repository.DefaultRepository
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

class SpendBillRepository(
    private val billDao: SpendBillDao
): DefaultRepository<SpendBill> {
    override suspend fun upsert(bill: SpendBill) {
        billDao.upsert(bill)
    }

    override suspend fun deleteBill(bill: SpendBill) {
        billDao.delete(bill)
    }

    override fun getAllByBillType(billType: BillType): Flow<List<SpendBill>> {
        if(billType == BillType.All){
            return billDao.getAll()
        }else {
            return billDao.getAllByBillType(billType.name)
        }
    }

    override fun getById(id: ObjectId): Flow<SpendBill> {
        return billDao.getById(id)
    }

    suspend fun getTotalAmountByType(billType: BillType): Int {
        if(billType == BillType.All){
            return billDao.getTotalSpendAmount()
        }else {
            return billDao.getTotalSpendAmountByType(billType.name)
        }
    }
}