package io.github.sergeyboboshko.usecomposeentityexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import io.github.sergeyboboshko.composeentity.daemons.MainViewModel
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.usecomposeentityexample.screens.MainPage
import io.github.sergeyboboshko.usecomposeentityexample.ui.theme.ComposeEntityTheme
import kotlin.getValue
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.GlobalState
import io.github.sergeyboboshko.composeentity.daemons.SelfNavigation
import io.github.sergeyboboshko.composeentity.daemons.screens.BottomCommonBar
import io.github.sergeyboboshko.usecomposeentityexample.daemons.appGlobal
import io.github.sergeyboboshko.usecomposeentityexample.references.RefList
import io.github.sergeyboboshko.usecomposeentityexample.screens.ScaffoldTopCommon

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()
    val refMeterZoneViewModel:RefMeterZoneViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalContext.mainViewModel=viewModel
        appGlobal.refMeterZoneViewModel=refMeterZoneViewModel
        enableEdgeToEdge()
        setContent {
            var navController = rememberNavController()
            GlobalContext.mainViewModel?.navController = navController
            GlobalContext.context=this
            ComposeEntityTheme() {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar={
                        ScaffoldTopCommon()
                    },
                    bottomBar = {BottomCommonBar ()}) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = Color.White
                    ) {
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") {
                                MainPage("MainList")
                            }

                            composable (route="selfNav/{form}",
                                arguments=listOf(
                                    navArgument("form"){type= NavType.StringType}
                                )){
                                //NavigationTargets.current = "selfNav"
                                val form =
                                    navController.currentBackStackEntry?.arguments?.getString("form")
                                SelfNavigation(form?:"")
//                                //var currentUI = viewModel.anyUI
//                                var currentUI = mainCustomStack.peek()
//                                Log.d("TURN_SCREEN","mainCustomStack.elements.size = ${mainCustomStack.size()}")
//                                if (currentUI!=null) {
//                                    when (form) {
//                                        "ADD" -> {
//                                            GlobalState.hideAllBottomBarButtons()
//                                            currentUI?.AddEditScreen(true, 0)
//                                        }
//
//                                        "EDIT" -> {
//                                            GlobalState.hideAllBottomBarButtons()
//                                            currentUI?.AddEditScreen(false, 0)
//                                        }
//
//                                        "MAIN_SCREEN" -> {
//                                            GlobalState.showAllBottomBarButtons()
//                                            Log.d("NAV","IN Main Activity currentUI= $currentUI")
//                                            currentUI?.MainScreenList()
//                                        }
//                                        "VIEW_SCREEN" -> {
//                                            GlobalState.showAllBottomBarButtons()
//                                            currentUI?.ViewScreen(0L)
//                                        }
//
//                                        "DETAILS_SCREEN" -> {
//                                            GlobalState.showAllBottomBarButtons()
//                                            currentUI?.ViewDetailsScreen(0L)
//                                        }
//
//                                        "MOVIES_SCREEN" -> {
//                                            GlobalState.hideAllBottomBarButtons()
//                                            (currentUI as DocUI).showMovements()
//                                        }
//                                    }
//                                }
//                                else{
//                                    Text("No UIs in main custom stack")
//                                }
                            }
                            // LISTS
                            ///******************* REferences list *******************
                            composable(route="references_menu_screen"){
                                //NavigationTargets.postpone()
                                GlobalState.hideAllBottomBarButtons()
                                RefList()
                            }
                        }
                    }
                }
            }
        }
    }
}