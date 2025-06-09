package com.srj.productapp.presentation.ui.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.srj.productapp.data.model.Product
import com.srj.productapp.getScreenHeight
import com.srj.productapp.getScreenWidth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailContent(
    product: Product,
    onImageClick: (images: List<String>) -> Unit,
    onBackClick: () -> Unit,
) {

    val screenWidth = getScreenWidth()
    val screenHeight = getScreenHeight()
    val platformDensity = LocalDensity.current
    platformDensity.apply {
        screenWidth.toPx().toDp()
        screenHeight.toPx().toDp()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product.title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(product.images.size) { index ->
                    AsyncImage(
                        model = product.images[index],
                        contentDescription = product.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth(.85f)
                            .padding(horizontal = 5.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .aspectRatio(16 / 9f)
                            .clickable {
                                onImageClick(product.images)
                            }

                    )
                }
            }
            Text(text = product.title, style = MaterialTheme.typography.headlineMedium)
            Text(text = product.subtitle, style = MaterialTheme.typography.titleMedium)
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface)
            Text(text = product.description, style = MaterialTheme.typography.bodyLarge)
        }
    }
}