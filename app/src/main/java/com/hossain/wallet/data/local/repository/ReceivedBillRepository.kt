package com.hossain.wallet.data.local.repository

import com.hossain.wallet.data.local.dao.ReceivedBillDao
import com.hossain.wallet.data.model.ReceivedBill
import com.hossain.wallet.domain.model.BillType
import com.hossain.wallet.domain.repository.DefaultRepository
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class ReceivedBillRepository @Inject constructor(
    private val billDao: ReceivedBillDao
): DefaultRepository<ReceivedBill> {
    override suspend fun upsert(bill: ReceivedBill) {
        billDao.upsert(bill)
    }

    override suspend fun deleteBill(bill: ReceivedBill) {
        billDao.delete(bill)
    }

    override fun getAllByBillType(billType: BillType): Flow<List<ReceivedBill>> {
        if(billType == BillType.All){
            return billDao.getAll()
        }else {
            return billDao.getAllByBillType(billType.name)
        }
    }

    override fun getById(id: ObjectId): Flow<ReceivedBill> {
        return billDao.getById(id)
    }

    suspend fun getTotalAmountByType(billType: BillType): Int {
        if(billType == BillType.All){
            return billDao.getTotalReceivedAmount()
        }else {
            return billDao.getTotalReceivedAmountByType(billType.name)
        }
    }
}