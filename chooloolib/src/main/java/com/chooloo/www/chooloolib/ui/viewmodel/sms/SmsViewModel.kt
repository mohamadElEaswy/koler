package com.chooloo.www.chooloolib.ui.viewmodel.sms

import com.chooloo.www.chooloolib.domain.model.ConversationData
import com.chooloo.www.chooloolib.domain.model.record.SmsRecord
import kotlinx.coroutines.flow.StateFlow

interface SmsViewModel {
    val uiState: StateFlow<SmsUiState>
    
    fun loadConversations()
    fun loadMessages(threadId: Long)
    fun markAsRead(smsId: Long)
    fun deleteSms(smsId: Long)
    fun searchConversations(query: String)
}

data class SmsUiState(
    val conversations: List<ConversationData> = emptyList(),
    val messages: List<SmsRecord> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedThreadId: Long? = null,
    val error: String? = null
)