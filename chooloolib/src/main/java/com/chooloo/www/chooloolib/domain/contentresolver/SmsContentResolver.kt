package com.chooloo.www.chooloolib.domain.contentresolver

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony
import com.chooloo.www.chooloolib.domain.model.record.SmsRecord
import com.chooloo.www.chooloolib.utils.SelectionBuilder
import java.util.*

class SmsContentResolver(
    filter: String? = null,
    contentResolver: ContentResolver,
    private val threadId: Long? = null,
) : BaseContentResolver<SmsRecord>(filter, contentResolver) {

    override val uri: Uri = Telephony.Sms.CONTENT_URI
    override val filterUri: Uri? = null
    override val sortOrder: String = "${Telephony.Sms.DATE} DESC"
    override val selectionArgs: Array<String>? = null
    override val projection: Array<String> = arrayOf(
        Telephony.Sms._ID,
        Telephony.Sms.THREAD_ID,
        Telephony.Sms.ADDRESS,
        Telephony.Sms.BODY,
        Telephony.Sms.DATE,
        Telephony.Sms.TYPE,
        Telephony.Sms.READ,
        Telephony.Sms.PERSON
    )

    override val selection: String
        get() {
            val selection = SelectionBuilder().addSelection(Telephony.Sms.THREAD_ID, threadId)
            currentFilter?.let { 
                selection.addString("(${Telephony.Sms.ADDRESS} LIKE '%$it%' OR ${Telephony.Sms.BODY} LIKE '%$it%')") 
            }
            return selection.build()
        }

    @SuppressLint("Range")
    override fun convertCursorToItem(cursor: Cursor): SmsRecord {
        return SmsRecord(
            id = cursor.getLong(cursor.getColumnIndex(Telephony.Sms._ID)),
            threadId = cursor.getLong(cursor.getColumnIndex(Telephony.Sms.THREAD_ID)),
            address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS)) ?: "",
            body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY)) ?: "",
            date = Date(cursor.getLong(cursor.getColumnIndex(Telephony.Sms.DATE))),
            type = cursor.getInt(cursor.getColumnIndex(Telephony.Sms.TYPE)),
            read = cursor.getInt(cursor.getColumnIndex(Telephony.Sms.READ)) == 1,
            person = cursor.getString(cursor.getColumnIndex(Telephony.Sms.PERSON))
        )
    }
}