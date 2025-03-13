package com.akosivis.androiddevelopertechnicalchallenge.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.akosivis.androiddevelopertechnicalchallenge.repository.ProductRepositoryImpl
import com.akosivis.androiddevelopertechnicalchallenge.ui.pages.ProductItemScreen
import com.akosivis.androiddevelopertechnicalchallenge.ui.pages.ProductListScreen
import com.akosivis.androiddevelopertechnicalchallenge.viewmodel.ProductItemViewModel
import com.akosivis.androiddevelopertechnicalchallenge.viewmodel.ProductItemViewModelFactory
import com.akosivis.androiddevelopertechnicalchallenge.viewmodel.ProductListViewModel
import com.akosivis.androiddevelopertechnicalchallenge.viewmodel.ProductListViewModelFactory

@Composable
fun AppNavGraph(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    startNavigation: String = AppDestinations.PRODUCT_LIST_ROUTE,
    productRepo: ProductRepositoryImpl,
    navigateToProductItem: (String) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startNavigation
    ) {
        composable(AppDestinations.PRODUCT_LIST_ROUTE) {
            val viewModel: ProductListViewModel = viewModel(
                factory = ProductListViewModelFactory(productRepo)
            )
            ProductListScreen(
                viewModel = viewModel,
                navToProductItem = navigateToProductItem
            )
        }
        composable(
            "${AppDestinations.PRODUCT_ITEM_ROUTE}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel: ProductItemViewModel = viewModel(
                factory = ProductItemViewModelFactory()
            )
            backStackEntry.arguments?.getString("id")?.let {
                ProductItemScreen(
                    viewModel = viewModel,
                    productItemId = it
                )
            }
        }
    }
}