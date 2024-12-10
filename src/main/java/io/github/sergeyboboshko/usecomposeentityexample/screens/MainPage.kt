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
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext.darkMode
import io.github.sergeyboboshko.composeentity.daemons.StyledButton
import io.github.sergeyboboshko.composeentity.daemons.localization.LocalizationManager
import io.github.sergeyboboshko.composeentity.daemons.navigate
import java.util.Locale


@Composable
fun MainPage (form:String){
    val references = LocalizationManager.getTranslation("references")
    val documents = LocalizationManager.getTranslation("documents")

    LazyColumn(Modifier.padding(all=2.dp)) {
        item {
            StyledButton(onClick = {
                GlobalContext.mainViewModel?.navController?.navigate("references_menu_screen")
            }) {
                Text(text = references, fontSize = 19.sp)
            }
        }
        item {
            StyledButton(onClick = {
                GlobalContext.mainViewModel?.navController?.navigate("documents_menu_screen")

            }) {
                Text(text = documents, fontSize = 19.sp)
            }
        }
        item{
            val localLanguage = Locale.getDefault().language
            Text("local = [$localLanguage]")
        }
        item{
            val darkModeIn = darkMode
            Text("darkModeIn = [$darkModeIn]")
        }
    }
}