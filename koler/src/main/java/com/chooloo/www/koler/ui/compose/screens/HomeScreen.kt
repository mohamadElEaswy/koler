package com.chooloo.www.koler.ui.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chooloo.www.koler.R
import com.chooloo.www.koler.ui.compose.components.AppSearchField
import com.chooloo.www.koler.ui.compose.components.CustomGradientAppBar
import com.chooloo.www.koler.ui.compose.theme.AppColors
import com.chooloo.www.koler.ui.compose.theme.AppSizes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var phoneNumber by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            CustomGradientAppBar(
                showLogo = false,
                onSettingsClick = { /* Handle settings click */ }
            )
        },
        containerColor = AppColors.white
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(AppSizes.padding32)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Arabic Logo
            Image(
                painter = painterResource(id = R.drawable.ic_logo_ar), // You'll need to add this drawable
                contentDescription = "Ranan Logo Arabic",
                modifier = Modifier.height(70.dp),
                contentScale = ContentScale.Fit
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // English Logo
            Image(
                painter = painterResource(id = R.drawable.ic_logo_en), // You'll need to add this drawable
                contentDescription = "Ranan Logo English",
                modifier = Modifier.height(20.dp),
                contentScale = ContentScale.Fit
            )
            
            Spacer(modifier = Modifier.height(64.dp))
            
            // Greeting Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "مرحبا", // "Hello" in Arabic
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 24.sp
                    ),
                    color = AppColors.black
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "عبد الرحمن", // Default user name
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp
                    ),
                    color = AppColors.black
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Image(
                    painter = painterResource(id = R.drawable.ic_waving_hand), // You'll need to add this drawable
                    contentDescription = "Waving Hand",
                    modifier = Modifier.size(30.dp),
                    contentScale = ContentScale.Fit
                )
            }
            
            Spacer(modifier = Modifier.height(50.dp))
            
            // Search Field with Hero Animation
            AppSearchField(
                enabled = false,
                onSearchClick = { /* Handle search click */ },
                onCountryClick = { /* Handle country click */ },
                onContactClick = { /* Handle contact click */ },
                phoneNumber = phoneNumber,
                onPhoneNumberChange = { phoneNumber = it }
            )
            
            Spacer(modifier = Modifier.height(96.dp))
        }
    }
}