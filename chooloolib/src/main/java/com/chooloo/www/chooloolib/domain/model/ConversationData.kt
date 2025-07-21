package com.chooloo.www.chooloolib.domain.model

import com.chooloo.www.chooloolib.domain.model.record.SmsRecord
import java.util.*

data class ConversationData(
    val threadId: Long,
    val address: String,
    val contactName: String?,
    val lastMessage: String,
    val lastMessageDate: Date,
    val unreadCount: Int,
    val messageCount: Int,
    val isLastMessageIncoming: Boolean
) {
    companion object {
        fun fromSmsRecords(messages: List<SmsRecord>, contactName: String? = null): ConversationData? {
            if (messages.isEmpty()) return null
            
            val lastMessage = messages.first() // Assuming messages are sorted by date DESC
            val unreadCount = messages.count { !it.read && it.isIncoming }
            
            return ConversationData(
                threadId = lastMessage.threadId,
                address = lastMessage.address,
                contactName = contactName,
                lastMessage = lastMessage.body,
                lastMessageDate = lastMessage.date,
                unreadCount = unreadCount,
                messageCount = messages.size,
                isLastMessageIncoming = lastMessage.isIncoming
            )
        }
    }
}