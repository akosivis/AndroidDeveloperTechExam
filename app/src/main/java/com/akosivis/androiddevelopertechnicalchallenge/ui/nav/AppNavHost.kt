package com.akosivis.androiddevelopertechnicalchallenge.ui.nav

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object AppDestinations {
    const val PRODUCT_LIST_ROUTE = "product_list"
    const val PRODUCT_ITEM_ROUTE = "product_item"
}

class AppNavigationActions(navController: NavHostController) {
    val navigateToProductItem: (String) -> Unit = { productId ->
        navController.navigate("${AppDestinations.PRODUCT_ITEM_ROUTE}/${productId}") {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
    val navigateToProductList: () -> Unit = {
        navController.navigate(AppDestinations.PRODUCT_LIST_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}