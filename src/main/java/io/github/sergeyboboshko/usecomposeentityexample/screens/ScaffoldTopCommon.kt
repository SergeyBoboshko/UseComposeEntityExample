package io.github.sergeyboboshko.usecomposeentityexample.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import io.github.sergeyboboshko.composeentity.daemons.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldTopCommon() {
    val navController = GlobalContext.mainViewModel?.navController
    var viewModel = GlobalContext.mainViewModel
    TopAppBar(
        title = { Text(text = "Power Payment Book") },
        navigationIcon = {
            //------------------------- RETURN ICON ---------------------------------
            var showEscapeDialog by remember { mutableStateOf(false) }
            // Если есть предыдущий экран, покажем иконку назад
            IconButton(onClick = {
                if (navController?.previousBackStackEntry != null) {
                    if (!GlobalContext.isModified) {
                        navController.popBackStack(mainCustomStack)
                        viewModel?.clearAllOfSelectedLists()
                        NavigationTargets.postpone()
                    } else {
                        showEscapeDialog = true
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
            //**************** DIALOG STEP BACK (ESCAPE SCREEN) **********************
            if (showEscapeDialog) {
                SimpleAlertDialog(
                    onDismissRequest = {
                        showEscapeDialog = false
                    },
                    onConfirmation = {
                        navController?.popBackStack()
                        viewModel?.clearAllOfSelectedLists()
                        showEscapeDialog = false
                    },
                    dialogTitle = "Escape Screen",
                    dialogText = "Are your sure to escape this screen? All of the changed data will de lost!",
                    icon = Icons.Default.Delete,
                    applicationContext = GlobalContext.context,
                    id = 1, viewModel = viewModel, navController = navController
                )
            }

        },
        actions = {
//            // Элементы в верхней панели
//            IconButton(onClick = {
//                val entity = FreeEntityExt(FreeEntity(0, -1L))
//                GlobalContext.mainViewModel?.uniSelectionManager?.clearSelection()
//                GlobalContext.mainViewModel?.uniSelectionManager?.toggleSelection(entity)
//                navController.navigate(
//                    SelfNav.getMainScreen(), mainCustomStack,
//                    FilterRoutineUI(context)
//                )
//            }) {
//                if (GlobalContext?.mainViewModel?.globalConditions?.isEmpty() ?: true) {
//                    Icon(
//                        painter = painterResource(id = R.mipmap.filter_alt_off_24px),
//                        contentDescription = null
//                    )
//                } else {
//                    Icon(
//                        painter = painterResource(id = R.mipmap.filter_alt_24px),
//                        tint = Color.Red,
//                        contentDescription = "Filter Had Set"
//                    )
//                }
//            }
        }
    )

}