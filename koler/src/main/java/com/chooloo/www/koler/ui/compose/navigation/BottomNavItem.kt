package com.chooloo.www.koler.ui.compose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.annotation.DrawableRes
import com.chooloo.www.koler.R

sealed class BottomNavItem(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int,
    @DrawableRes val activeIcon: Int? = null
) {
    object Home : BottomNavItem(
        route = "home",
        title = "الرئيسية", // Home in Arabic
        icon = R.drawable.ic_logo,
        activeIcon = R.drawable.ic_logo
    )
    
    object Dialer : BottomNavItem(
        route = "dialer",
        title = "الاتصال", // Dialer in Arabic
        icon = R.drawable.ic_call,
        activeIcon = R.drawable.ic_call_active
    )
    
    object Contacts : BottomNavItem(
        route = "contacts",
        title = "جهات الاتصال", // Contacts in Arabic
        icon = R.drawable.ic_user,
        activeIcon = R.drawable.ic_user_active
    )
    
    object Recents : BottomNavItem(
        route = "recents",
        title = "السجل", // Recents in Arabic
        icon = R.drawable.ic_call,
        activeIcon = R.drawable.ic_call_active
    )
    
    object Messages : BottomNavItem(
        route = "messages",
        title = "الرسائل", // Messages in Arabic
        icon = R.drawable.ic_message,
        activeIcon = R.drawable.ic_message_active
    )
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Dialer,
    BottomNavItem.Contacts,
    BottomNavItem.Recents,
    BottomNavItem.Messages
)