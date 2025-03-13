package com.akosivis.androiddevelopertechnicalchallenge.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akosivis.androiddevelopertechnicalchallenge.viewmodel.ProductItemUiState
import com.akosivis.androiddevelopertechnicalchallenge.viewmodel.ProductItemViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProductItemScreen(
    viewModel: ProductItemViewModel,
    productItemId: String
) {
    // viewModel
    val uiState = viewModel.productItemUiState

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        when (uiState) {
                is ProductItemUiState.Loading -> {
                    productItemId.let {
                        viewModel.fetchProductItem(it)
                    }

                    Column (
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                        )
                        Text(
                            text = "Loading",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
                is ProductItemUiState.Success -> {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(8.dp)
                    ) {
                        // Picture
                        GlideImage(
                            model = uiState.item.thumbnail.toString(),
                            contentDescription = uiState.item.description
                        )

                        // Title
                        Text(
                            text = uiState.item.title.toString(),
                            style = MaterialTheme.typography.headlineMedium
                        )

                        // Price
                        Text(
                            text = "₱ ${uiState.item.price.toString()}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        // Description
                        Text(
                            text = uiState.item.description.toString(),
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        // Category
                        Text(
                            text = "Category",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        BaseChip(uiState.item.category.toString())
                        Spacer(modifier = Modifier.height(10.dp))
                        // Brand
                        Text(
                            text = "Brand",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        BaseChip(uiState.item.brand.toString())


                    }
                }
                is ProductItemUiState.Error -> {
                    Column (
                        modifier = Modifier.fillMaxSize().padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Cannot load the item! Please try again!",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseChip (
    text: String,
) {
    InputChip (
        onClick = {},
        selected = true,
        label = {
            Text (
                text = text,
                fontSize = 16.sp
            )
        }
    )
}