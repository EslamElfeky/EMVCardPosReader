package com.app.emvcard.domain.port

import com.app.emvcard.domain.model.TransactionRecord
import kotlinx.coroutines.flow.Flow
interface TransactionHistoryRepository {
    suspend fun save(record: TransactionRecord)
    fun observeAll(): Flow<List<TransactionRecord>>
}