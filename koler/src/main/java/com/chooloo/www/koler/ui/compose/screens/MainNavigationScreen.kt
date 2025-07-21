package com.chooloo.www.koler.ui.compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import com.chooloo.www.chooloolib.ui.compose.BottomSheet
import com.chooloo.www.chooloolib.ui.compose.list.RecentsList
import com.chooloo.www.chooloolib.ui.view.CallerView
import com.chooloo.www.chooloolib.ui.viewmodel.preferences.ChoolooPreferencesViewModelImpl
import com.chooloo.www.koler.ui.compose.components.ConvexBottomNavigationBar
import com.chooloo.www.koler.ui.compose.navigation.bottomNavItems
import com.chooloo.www.koler.ui.view.KolerPreferencesView
import com.chooloo.www.koler.viewmodel.main.MainViewModelImpl
@Composable
fun MainNavigationScreen(
    mainViewModel: MainViewModelImpl = hiltViewModel(),
    settingsViewModel: ChoolooPreferencesViewModelImpl = hiltViewModel()
) {
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val uiState by mainViewModel.uiState.collectAsState()

    // Handle bottom sheets from original app
    BottomSheet(
        visible = uiState.isMenuVisible,
        onDismiss = { mainViewModel.onDismissMenu() }
    ) {
        KolerPreferencesView(settingsViewModel)
    }

    BottomSheet(
        visible = uiState.selectedRecentData != null,
        onDismiss = mainViewModel::onDismissSelectedRecentData
    ) {
        val recentItem = uiState.selectedRecentData!!
        if (recentItem.groupAccounts.isNotEmpty()) {
            RecentsList(
                items = recentItem.groupAccounts,
                onItemClick = mainViewModel::onRecentDataClick
            )
        } else {
            CallerView(recentId = recentItem.id)
        }
    }

    BottomSheet(
        visible = uiState.selectedContactData != null,
        onDismiss = mainViewModel::onDismissSelectedContactData
    ) {
        CallerView(contactId = uiState.selectedContactData?.id)
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            bottomBar = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    ConvexBottomNavigationBar(
                        selectedIndex = selectedItemIndex,
                        onItemSelected = { index ->
                            selectedItemIndex = index
                        }
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (selectedItemIndex) {
                    0 -> HomeScreen()
                    1 -> DialerScreen(mainViewModel)
                    2 -> ContactsScreen(mainViewModel)
                    3 -> RecentsScreen(mainViewModel)
                    4 -> MessagesScreen()
                }
            }
        }
    }
}