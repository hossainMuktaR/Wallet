package com.hossain.wallet.domain.usecases

import android.util.Log
import androidx.compose.runtime.collectAsState
import com.hossain.wallet.data.local.repository.ReceivedBillRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext

class GetTotalReceivedBillAmountUseCase(
    private val receivedBillRepository: ReceivedBillRepository
) {
    suspend operator fun invoke(scope: CoroutineScope): Int = getTotalBillAmount(scope)

    private suspend fun getTotalBillAmount(scope: CoroutineScope): Int {
        var totalBillAmount = 0
        val listOfBill = receivedBillRepository.getAll().stateIn(scope).value
        listOfBill.forEach {
            totalBillAmount += it.amount
        }
        return totalBillAmount
    }
}