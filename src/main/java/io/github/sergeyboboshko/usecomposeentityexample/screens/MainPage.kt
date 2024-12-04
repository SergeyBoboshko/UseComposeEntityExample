package io.github.sergeyboboshko.usecomposeentityexample.screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.StyledButton
import io.github.sergeyboboshko.composeentity.daemons.navigate
import java.util.Locale


@Composable
fun MainPage (form:String){
    var counter = remember{ mutableStateOf(0) }
    LazyColumn(Modifier.padding(top = 1.dp, bottom = 1.dp)) {
        item {
            StyledButton(onClick = {
                GlobalContext.mainViewModel?.navController?.navigate("references_menu_screen")
            }) {
                Text(text = "Довідники. I tapped ${counter.value} times", fontSize = 19.sp)
            }
        }
        item {
            StyledButton(onClick = {
                counter.value++
                Log.d("STYLED_BUTTON", "ON_CLICK ${counter.value}")
            }) {
                Text(text = "Документи. I tapped ${counter.value} times", fontSize = 19.sp)
            }
        }
        item{
            val localLanguage = Locale.getDefault().language
            Text("local = [$localLanguage]")
        }
    }
}