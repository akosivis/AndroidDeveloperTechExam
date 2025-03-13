package com.akosivis.androiddevelopertechnicalchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.akosivis.androiddevelopertechnicalchallenge.application.ProductApplication
import com.akosivis.androiddevelopertechnicalchallenge.network.DummyJsonApiService
import com.akosivis.androiddevelopertechnicalchallenge.network.RetrofitHelper
import com.akosivis.androiddevelopertechnicalchallenge.ui.nav.AppDestinations
import com.akosivis.androiddevelopertechnicalchallenge.ui.nav.AppNavGraph
import com.akosivis.androiddevelopertechnicalchallenge.ui.nav.AppNavigationActions
import com.akosivis.androiddevelopertechnicalchallenge.ui.theme.AndroidDeveloperTechnicalChallengeTheme
import com.akosivis.androiddevelopertechnicalchallenge.viewmodel.ProductListViewModel
import com.akosivis.androiddevelopertechnicalchallenge.viewmodel.ProductListViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val application: ProductApplication = (application as ProductApplication)
        val productsApi = RetrofitHelper.getInstance().create(DummyJsonApiService::class.java)

        enableEdgeToEdge()
        setContent {
            AndroidDeveloperTechnicalChallengeTheme {
                ProductApp(
                    app = application,
                    productsApi = productsApi
                )
            }
        }
    }
}

@Composable
fun ProductApp(
    app: ProductApplication,
    productsApi: DummyJsonApiService
) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        AppNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: AppDestinations.PRODUCT_LIST_ROUTE

    ProductAppScreen(
        app,
        navController,
        navigationActions,
        navBackStackEntry,
        currentRoute
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAppScreen(
    app: ProductApplication,
    navController: NavHostController,
    navigationActions: AppNavigationActions,
    navBackStackEntry: NavBackStackEntry?,
    currentRoute: String,
) {
    Surface (
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                if (currentRoute.contains(AppDestinations.PRODUCT_ITEM_ROUTE)) {
                    TopAppBar(
                        title = {},
                        navigationIcon = {
                            IconButton (
                                onClick = { navigationActions.navigateToProductList() }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            AppNavGraph(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                productRepo = app.productRepo,
                navigateToProductItem = navigationActions.navigateToProductItem
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidDeveloperTechnicalChallengeTheme {
        Greeting("Android")
    }
}