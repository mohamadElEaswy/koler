package com.chooloo.www.koler.ui.activity

import android.content.Intent
import android.util.Log
import android.view.MotionEvent
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        activityRepository.ignoreEditTextFocus(this, event)
        return super.dispatchTouchEvent(event)
    }
}