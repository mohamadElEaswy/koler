package com.chooloo.www.koler.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chooloo.www.koler.R
import com.chooloo.www.koler.ui.compose.theme.AppColors
import com.chooloo.www.koler.ui.compose.theme.AppSizes

@Composable
fun AppSearchField(
    enabled: Boolean = true,
    onSearchClick: () -> Unit = {},
    onCountryClick: () -> Unit = {},
    onContactClick: () -> Unit = {},
    phoneNumber: String = "",
    countryCode: String = "+966",
    countryFlag: Int = R.drawable.ic_flag_sa, // You'll need to add flag drawables
    onPhoneNumberChange: (String) -> Unit = {}
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppSizes.inputHeight)
                .clip(RoundedCornerShape(AppSizes.borderRadius))
                .background(AppColors.white)
                .border(
                    width = 1.dp,
                    color = AppColors.lightGrey,
                    shape = RoundedCornerShape(AppSizes.borderRadius)
                )
                .clickable(enabled = !enabled) { onSearchClick() }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Search Icon
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = AppColors.grey,
                    modifier = Modifier.size(16.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Phone Number Input
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    BasicTextField(
                        value = phoneNumber,
                        onValueChange = onPhoneNumberChange,
                        enabled = enabled,
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start,
                            textDirection = TextDirection.Ltr
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.weight(1f),
                        decorationBox = { innerTextField ->
                            if (phoneNumber.isEmpty()) {
                                Text(
                                    text = "ابحث بالرقم ..",
                                    color = AppColors.grey,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                }
                
                // Divider
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(24.dp)
                        .background(AppColors.lightGrey)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Contact Button
                IconButton(
                    onClick = onContactClick,
                    modifier = Modifier.size(28.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_contact), // You'll need to add this drawable
                        contentDescription = "Contacts",
                        modifier = Modifier.size(26.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Country Selection
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onCountryClick() }
                ) {
                    // Country Flag
                    Image(
                        painter = painterResource(id = countryFlag),
                        contentDescription = "Country Flag",
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    // Country Code
                    Text(
                        text = "$countryCode +",
                        style = MaterialTheme.typography.titleSmall,
                        color = AppColors.black
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    // Chevron
                    Image(
                        painter = painterResource(id = R.drawable.ic_chevron), // You'll need to add this drawable
                        contentDescription = "Expand",
                        modifier = Modifier.size(12.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}