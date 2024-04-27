package com.hossain.wallet.data.local.repository

import com.hossain.wallet.data.local.dao.OwedBillDao
import com.hossain.wallet.data.model.OwedBill
import com.hossain.wallet.domain.model.BillType
import com.hossain.wallet.domain.repository.DefaultRepository
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class OwedBillRepository @Inject constructor(
    private val billDao: OwedBillDao
): DefaultRepository<OwedBill> {
    override suspend fun upsert(bill: OwedBill) {
        billDao.upsert(bill)
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

    override fun getById(id: ObjectId): Flow<OwedBill> {
        return billDao.getById(id)
    }
}