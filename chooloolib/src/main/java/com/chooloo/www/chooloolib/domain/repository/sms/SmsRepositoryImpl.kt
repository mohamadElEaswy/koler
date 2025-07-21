package com.chooloo.www.chooloolib.domain.repository.sms

import android.content.ContentResolver
import android.content.ContentValues
import android.provider.Telephony
import com.chooloo.www.chooloolib.domain.contentresolver.SmsContentResolver
import com.chooloo.www.chooloolib.domain.model.record.SmsRecord
import com.chooloo.www.chooloolib.domain.repository.base.BaseRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmsRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver
) : BaseRepositoryImpl(), SmsRepository {

    override fun getSmsMessages(filter: String?): Flow<List<SmsRecord>> {
        return SmsContentResolver(filter, contentResolver).getItemsFlow()
    }

    override fun getConversationMessages(threadId: Long): Flow<List<SmsRecord>> {
        return SmsContentResolver(null, contentResolver, threadId).getItemsFlow()
    }

    override suspend fun markAsRead(smsId: Long) {
        val values = ContentValues().apply {
            put(Telephony.Sms.READ, 1)
        }
        contentResolver.update(
            Telephony.Sms.CONTENT_URI,
            values,
            "${Telephony.Sms._ID} = ?",
            arrayOf(smsId.toString())
        )
    }

    override suspend fun deleteSms(smsId: Long) {
        contentResolver.delete(
            Telephony.Sms.CONTENT_URI,
            "${Telephony.Sms._ID} = ?",
            arrayOf(smsId.toString())
        )
    }
}