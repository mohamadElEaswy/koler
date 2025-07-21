package com.chooloo.www.chooloolib.ui.viewmodel.sms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chooloo.www.chooloolib.domain.model.ConversationData
import com.chooloo.www.chooloolib.domain.repository.phone.PhoneRepository
import com.chooloo.www.chooloolib.domain.repository.sms.SmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmsViewModelImpl @Inject constructor(
    private val smsRepository: SmsRepository,
    private val phoneRepository: PhoneRepository
) : ViewModel(), SmsViewModel {

    private val _uiState = MutableStateFlow(SmsUiState())
    override val uiState: StateFlow<SmsUiState> = _uiState.asStateFlow()

    init {
        loadConversations()
    }

    override fun loadConversations() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                smsRepository.getSmsMessages(_uiState.value.searchQuery)
                    .collect { messages ->
                        // Group messages by thread ID
                        val conversationsMap = messages.groupBy { it.threadId }
                        val conversations = mutableListOf<ConversationData>()
                        
                        for ((threadId, threadMessages) in conversationsMap) {
                            val address = threadMessages.first().address
                            val contact = phoneRepository.lookupAccount(address)
                            val conversationData = ConversationData.fromSmsRecords(threadMessages, contact?.name)
                            conversationData?.let { conversations.add(it) }
                        }
                        
                        _uiState.value = _uiState.value.copy(
                            conversations = conversations.sortedByDescending { it.lastMessageDate },
                            isLoading = false,
                            error = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    override fun loadMessages(threadId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(selectedThreadId = threadId, isLoading = true)
            
            try {
                smsRepository.getConversationMessages(threadId)
                    .collect { messages ->
                        _uiState.value = _uiState.value.copy(
                            messages = messages,
                            isLoading = false,
                            error = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    override fun markAsRead(smsId: Long) {
        viewModelScope.launch {
            try {
                smsRepository.markAsRead(smsId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    override fun deleteSms(smsId: Long) {
        viewModelScope.launch {
            try {
                smsRepository.deleteSms(smsId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    override fun searchConversations(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        loadConversations()
    }
}