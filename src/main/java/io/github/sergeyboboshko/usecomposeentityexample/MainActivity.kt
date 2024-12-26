package io.github.sergeyboboshko.usecomposeentityexample

import android.content.Context
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
import androidx.compose.runtime.LaunchedEffect
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

import kotlin.getValue
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.GlobalState
import io.github.sergeyboboshko.composeentity.daemons.InitComposableEntityVariables
import io.github.sergeyboboshko.composeentity.daemons.InitComposeEntityColors
import io.github.sergeyboboshko.composeentity.daemons.SelfNavigation
import io.github.sergeyboboshko.composeentity.daemons.localization.LocalizationManager
import io.github.sergeyboboshko.composeentity.daemons.screens.BottomCommonBar
import io.github.sergeyboboshko.usecomposeentityexample.daemons.appGlobal
import io.github.sergeyboboshko.usecomposeentityexample.documents.DocList
import io.github.sergeyboboshko.usecomposeentityexample.documents.DocPaymentsInvoiceViewModel
import io.github.sergeyboboshko.usecomposeentityexample.references.RefAddressesViewModel
import io.github.sergeyboboshko.usecomposeentityexample.references.RefList
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMeterZoneViewModel
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersDetailsViewModel
import io.github.sergeyboboshko.usecomposeentityexample.screens.ScaffoldTopCommon
import io.github.sergeyboboshko.usecomposeentityexample.ui.theme.UseComposeEntityTheme
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersViewModel
import java.util.Locale
import io.github.sergeyboboshko.composeentity.daemons.LocaleHelper
import io.github.sergeyboboshko.usecomposeentityexample.details.RefAddressDetailsViewModel
import io.github.sergeyboboshko.usecomposeentityexample.references.RefUtilitiesViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()
    val refMeterZoneViewModel:RefMeterZoneViewModel by viewModels()
    val refMetersDetailsViewModel:RefMetersDetailsViewModel by viewModels()

    val refMetersViewModel:RefMetersViewModel by viewModels()
    val docPaymentsInvoiceViewModel:DocPaymentsInvoiceViewModel by viewModels()
    val refUtilitiesViewModel:RefUtilitiesViewModel  by viewModels()
    val refAddressDetailsViewModel:RefAddressDetailsViewModel  by viewModels()
    val refAddressesViewModel:RefAddressesViewModel  by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val updatedContext = LocaleHelper.setLocale(this, "en") // Змінюємо локаль

        GlobalContext.mainViewModel=viewModel
        appGlobal.refMeterZoneViewModel=refMeterZoneViewModel
        appGlobal.refMetersDetailsViewModel=refMetersDetailsViewModel
        appGlobal.refAddressesModel=refAddressesViewModel
        appGlobal.refUtilitiesViewModel=refUtilitiesViewModel
        appGlobal.refMetersViewModel=refMetersViewModel

        appGlobal.refAddressDetailsViewModel=refAddressDetailsViewModel


        appGlobal.docPaymentsInvoiceViewModel=docPaymentsInvoiceViewModel
        appGlobal.refMetersDetailsViewModel=refMetersDetailsViewModel


        enableEdgeToEdge()
        setContent {

            InitComposableEntityVariables()
            InitComposeEntityColors()
            var navController = rememberNavController()
            GlobalContext.mainViewModel?.navController = navController
            GlobalContext.context=this
//            LocalizationManager.currentLanguageCode="en"
            UseComposeEntityTheme() {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar={
                        ScaffoldTopCommon()
                    },
                    bottomBar = {BottomCommonBar ()}) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
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
                           }
                            // LISTS
                            ///******************* REferences list *******************
                            composable(route="references_menu_screen"){
                                //NavigationTargets.postpone()
                                GlobalState.hideAllBottomBarButtons()
                                RefList()
                            }
                            ///******************* REferences list *******************
                            composable(route="documents_menu_screen"){
                                //NavigationTargets.postpone()
                                GlobalState.hideAllBottomBarButtons()
                                DocList()
                            }
                        }
                    }
                }
            }
        }
    }

 //   override fun attachBaseContext(newBase: Context) {
//        val updatedContext = LocaleHelper.setLocale(newBase, "en") // Встановлення локалі
//        applyOverrideConfiguration(updatedContext.resources.configuration)
//        super.attachBaseContext(updatedContext)
 //   }
}