package com.akosivis.androiddevelopertechnicalchallenge.ui.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.akosivis.androiddevelopertechnicalchallenge.database.Product
import com.akosivis.androiddevelopertechnicalchallenge.viewmodel.ProductListUiState
import com.akosivis.androiddevelopertechnicalchallenge.viewmodel.ProductListViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    navToProductItem: (String) -> Unit
) {
    val uiState = viewModel.productListUiState
    val listState = rememberLazyListState()
    // Check if the user has scrolled to the bottom of the list
    val reachedBottomOfList by remember {
        derivedStateOf {
            listState.reachedBottom() // Custom extension function to check if the user has reached the bottom
        }
    }

    LaunchedEffect(reachedBottomOfList) {
        Log.d("LOADMORE", "REACHED BOTTOM")
        if (uiState is ProductListUiState.Success) {
            // viewModel.loadMoreItems()
            Log.d("LOADMORE", "BOTTOM")
            if (uiState.list.isNotEmpty()) {
                // load more items here
                Log.d("LOADMORE", "ProductListScreen: LOAD MORE not empty")
                viewModel.isListLoading = true
                viewModel.loadMoreItems()
            } else {
                Log.d("LOADMORE", "ProductListScreen: LOAD MORE empty")
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            Box(
                modifier = Modifier.height(180.dp)
            ) {
                Column {
                    Row (modifier = Modifier.fillMaxWidth()){
                        Text(
                            text = "Skip: ",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        TextField(
                            value = viewModel.skipVal,
                            onValueChange = { if (it.isDigitsOnly()) viewModel.setSkip(it) }
                        )
                    }

                    Row (modifier = Modifier.fillMaxWidth()){
                        Text(
                            text = "Limit: ",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        TextField(
                            value = viewModel.limitVal,
                            onValueChange = { if (it.isDigitsOnly()) viewModel.setLimit(it) }
                        )
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        shape = RoundedCornerShape(45.dp),
                        onClick = {
                            viewModel.fetchProducts()
                        }
                    ) {
                        Text(
                            text = "Get products"
                        )
                    }
                }
            }
            when (uiState) {
                is ProductListUiState.Success -> {
                    LazyColumn (
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp)
                    ) {
                        items(uiState.list){ product ->
                            ProductListItem(product, navToProductItem)
                        }

                        // Show loading indicator at the end of the list when loading more movies
                        item {
                            if (viewModel.isListLoading) {
                                ProductListItemLoading()
                            }
                        }
                    }
                }
                is ProductListUiState.Loading -> {
                    Column (modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Loading",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
                is ProductListUiState.Error -> {
                    Column (modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = uiState.message,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductListItem(
    product: Product,
    navToProductItem: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navToProductItem(product.id.toString())
            },
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            // Picture
            GlideImage(
                model = product.thumbnail.toString(),
                contentDescription = product.description
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = product.title.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductListItemLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(min = 20.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
    }
}



fun LazyListState.reachedBottom(): Boolean {
//    return layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
//    val visibleItemsInfo = layoutInfo.visibleItemsInfo
//    return if (layoutInfo.totalItemsCount == 0) {
//        false
//    } else {
//        val lastVisibleItem = visibleItemsInfo.last()
//        val viewportHeight = layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset
//        (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount && lastVisibleItem.offset + lastVisibleItem.size <= viewportHeight)
//    }
    val lastItem = layoutInfo.visibleItemsInfo.lastOrNull()
    return lastItem == null || (lastItem.index == layoutInfo.totalItemsCount - 1 && lastItem.size + lastItem.offset <= layoutInfo.viewportEndOffset)
}