package com.chooloo.www.chooloolib.domain.model.record

import java.util.*

data class SmsRecord(
    val id: Long,
    val threadId: Long,
    val address: String,
    val body: String,
    val date: Date,
    val type: Int, // 1 = received, 2 = sent
    val read: Boolean,
    val person: String? = null
) {
    companion object {
        const val TYPE_INBOX = 1
        const val TYPE_SENT = 2
        const val TYPE_DRAFT = 3
        const val TYPE_OUTBOX = 4
        const val TYPE_FAILED = 5
        const val TYPE_QUEUED = 6
    }
    
    val isIncoming: Boolean get() = type == TYPE_INBOX
    val isOutgoing: Boolean get() = type == TYPE_SENT
}