package com.chooloo.www.koler.ui.activity

import android.app.role.RoleManager
import android.content.Intent
import android.os.Build
import android.telecom.TelecomManager
import android.util.Log
import android.view.MotionEvent
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chooloo.www.chooloolib.domain.repository.activity.ActivityRepository
import com.chooloo.www.chooloolib.ui.activity.BaseActivity
import com.chooloo.www.koler.ui.compose.screens.MainNavigationScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    @Inject lateinit var activityRepository: ActivityRepository
    
    private var hasRequestedDefaultDialer = false

    private val requestDefaultDialerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result if needed
        Log.i("MainActivity", "Default dialer request result: ${result.resultCode}")
        hasRequestedDefaultDialer = true
    }

    override fun onSetup() {
        activityRepository.disableKeyboard(this)

        if (intent.action in arrayOf(Intent.ACTION_DIAL, Intent.ACTION_VIEW)) {
            Log.i("MainActivity", "onSetup ACTION_DIAL or ACTION_VIEW")
//            viewState.onViewIntent(intent)
        }

        setContent {
            // Launch the integrated navigation directly
            MainNavigationScreen()
        }
        
        // Check and request default phone app permission after content is set
        checkAndRequestDefaultDialerPermission()
    }

    private fun checkAndRequestDefaultDialerPermission() {
        Log.i("MainActivity", "Checking default dialer permission...")
        
        if (hasRequestedDefaultDialer) {
            Log.i("MainActivity", "Already requested default dialer permission in this session")
            return
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Use RoleManager for Android 10+
            try {
                val roleManager = getSystemService(RoleManager::class.java)
                val isDefaultDialer = roleManager.isRoleHeld(RoleManager.ROLE_DIALER)
                
                Log.i("MainActivity", "Using RoleManager - Is default dialer: $isDefaultDialer")
                
                if (!isDefaultDialer) {
                    Log.i("MainActivity", "Requesting dialer role via RoleManager...")
                    val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER)
                    requestDefaultDialerLauncher.launch(intent)
                    Log.i("MainActivity", "RoleManager dialer role request launched")
                } else {
                    Log.i("MainActivity", "We are already the default dialer (RoleManager)")
                    hasRequestedDefaultDialer = true
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error with RoleManager", e)
                hasRequestedDefaultDialer = true
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Use TelecomManager for Android 6-9
            try {
                val telecomManager = getSystemService(TELECOM_SERVICE) as TelecomManager
                val currentDefaultDialer = telecomManager.getDefaultDialerPackage()
                val ourPackageName = packageName
                
                Log.i("MainActivity", "Using TelecomManager - Current default dialer: $currentDefaultDialer")
                Log.i("MainActivity", "Our package name: $ourPackageName")
                
                val isDefaultDialer = currentDefaultDialer == ourPackageName
                
                if (!isDefaultDialer) {
                    Log.i("MainActivity", "Requesting dialer permission via TelecomManager...")
                    val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                    intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, ourPackageName)
                    
                    try {
                        requestDefaultDialerLauncher.launch(intent)
                        Log.i("MainActivity", "TelecomManager dialer permission request launched")
                    } catch (e: Exception) {
                        Log.e("MainActivity", "Failed to launch TelecomManager dialer request", e)
                        hasRequestedDefaultDialer = true
                    }
                } else {
                    Log.i("MainActivity", "We are already the default dialer (TelecomManager)")
                    hasRequestedDefaultDialer = true
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error with TelecomManager", e)
                hasRequestedDefaultDialer = true
            }
        } else {
            Log.i("MainActivity", "Android version < M, default dialer not supported")
            hasRequestedDefaultDialer = true
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        activityRepository.ignoreEditTextFocus(this, event)
        return super.dispatchTouchEvent(event)
    }
}