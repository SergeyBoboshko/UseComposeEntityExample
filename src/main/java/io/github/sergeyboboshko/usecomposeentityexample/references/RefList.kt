package io.github.sergeyboboshko.usecomposeentityexample.references

import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sergeyboboshko.composeentity.R
import io.github.sergeyboboshko.composeentity.daemons.BaseUI
import io.github.sergeyboboshko.composeentity.daemons.StyledButton
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.SelfNav
import io.github.sergeyboboshko.composeentity.daemons.mainCustomStack
import io.github.sergeyboboshko.composeentity.references.base.*
import io.github.sergeyboboshko.usecomposeentityexample.RefMeterZonesUI

import androidx.compose.material3.Divider
import io.github.sergeyboboshko.composeentity.daemons.GlobalColors
import io.github.sergeyboboshko.composeentity.daemons.navigate

@Composable
fun RefList (){
    Column(Modifier.padding(all = 4.dp)) {
        Text(
            text = "References",
            modifier = Modifier
                .fillMaxWidth()
                .background(GlobalColors.currentPalette.captionFonColor),
            fontSize = 20.sp
        )
        StyledButton(onClick = {
            //GlobalContext.mainViewModel?.anyUI = RefMeterZonesUI(GlobalContext.context) as BaseUI
            GlobalContext.mainViewModel?.navController?.navigate(
                SelfNav.getMainScreen(),
                RefMeterZonesUI()
            )
        }) {
            Column/*(modifier = Modifier.width(IntrinsicSize.Max)) */{
                Text(
                    "Meter Zones",
                    style = MaterialTheme.typography.titleLarge, modifier = Modifier.background(color= GlobalColors.currentPalette.text)
                )
                Divider(color = Color.Gray, thickness = 1.dp)
                Text(
                    text = "Zones like day/night",
                    style = MaterialTheme.typography.titleSmall, modifier = Modifier.background(color= GlobalColors.currentPalette.text)
                )
            }
        }
    }
}