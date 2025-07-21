package com.chooloo.www.koler.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chooloo.www.koler.R
import com.chooloo.www.koler.ui.compose.theme.AppColors
import com.chooloo.www.koler.ui.compose.theme.LinearColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomGradientAppBar(
    showLogo: Boolean = true,
    onSettingsClick: () -> Unit = {}
) {
    TopAppBar(
        title = { },
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = LinearColors.darkLinear,
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset(1000f, 1000f)
                )
            )
            .height(64.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = androidx.compose.ui.graphics.Color.Transparent
        ),
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_settings), // You'll need to add this drawable
                contentDescription = "Settings",
                modifier = Modifier
                    .size(22.dp)
                    .clickable { onSettingsClick() }
                    .padding(start = 16.dp),
                contentScale = ContentScale.Fit
            )
        },
        actions = {
            if (showLogo) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo), // You'll need to add this drawable
                    contentDescription = "Logo",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .height(32.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    )
}