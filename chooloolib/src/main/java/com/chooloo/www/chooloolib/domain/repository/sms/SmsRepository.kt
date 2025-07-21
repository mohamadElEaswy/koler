package com.chooloo.www.chooloolib.domain.repository.sms

import com.chooloo.www.chooloolib.domain.model.record.SmsRecord
import kotlinx.coroutines.flow.Flow

interface SmsRepository {
    fun getSmsMessages(filter: String? = null): Flow<List<SmsRecord>>
    fun getConversationMessages(threadId: Long): Flow<List<SmsRecord>>
    suspend fun markAsRead(smsId: Long)
    suspend fun deleteSms(smsId: Long)
}