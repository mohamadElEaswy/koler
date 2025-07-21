package com.chooloo.www.koler.ui.compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chooloo.www.chooloolib.ui.view.ContactsView
import com.chooloo.www.chooloolib.ui.view.DialerView
import com.chooloo.www.chooloolib.ui.view.RecentsView
import com.chooloo.www.chooloolib.ui.view.SmsView
import com.chooloo.www.chooloolib.ui.viewmodel.sms.SmsViewModelImpl
import com.chooloo.www.koler.viewmodel.main.MainViewModelImpl

@Composable
fun DialerScreen(
    mainViewModel: MainViewModelImpl = hiltViewModel()
) {
    DialerView()
}

@Composable
fun ContactsScreen(
    mainViewModel: MainViewModelImpl = hiltViewModel()
) {
    val uiState by mainViewModel.uiState.collectAsState()
    
    ContactsView(
        filter = uiState.searchText,
        modifier = Modifier.fillMaxSize(),
        onItemClick = mainViewModel::onContactDataClick
    )
}

@Composable
fun RecentsScreen(
    mainViewModel: MainViewModelImpl = hiltViewModel()
) {
    val uiState by mainViewModel.uiState.collectAsState()
    
    RecentsView(
        filter = uiState.searchText,
        modifier = Modifier.fillMaxSize(),
        onItemClick = mainViewModel::onRecentDataClick,
        onItemLongClick = mainViewModel::onRecentDataLongClick
    )
}

@Composable
fun MessagesScreen(
    smsViewModel: SmsViewModelImpl = hiltViewModel()
) {
    SmsView(
        smsViewModel = smsViewModel
    )
}