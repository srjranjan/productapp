package com.srj.productapp.presentation.ui.product

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageGalleryScreen(
    imageUrls: List<String>,
    isProfileImage: Boolean,
    onNavigateBack: () -> Unit,
) {
    if (imageUrls.isEmpty()) {
        Text("No images to display.")
        return
    }

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { imageUrls.size }
    )
    val currentPage by remember { derivedStateOf { pagerState.currentPage } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Back") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        ImagePager(
            pagerState = pagerState,
            imageUrls = imageUrls,
            scope = scope,
            paddingValues = paddingValues,
            currentPage = currentPage,
            isProfileImage = isProfileImage
        )
    }
}

@Composable
fun ImagePager(
    pagerState: PagerState,
    imageUrls: List<String>,
    scope: CoroutineScope,
    paddingValues: PaddingValues,
    currentPage: Int,
    isProfileImage: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            key = { index -> imageUrls.getOrNull(index) ?: index }
        ) { page ->
            AsyncImage(
                model = imageUrls.getOrNull(page),
                contentDescription = "Image $page",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        //small previews
        if (isProfileImage.not())
            ImagePreviews(pagerState, imageUrls, currentPage, scope)

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ImagePreviews(pagerState: PagerState, imageUrls: List<String>, currentPage: Int, scope: CoroutineScope) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(imageUrls) { index, imageUrl ->
            val borderWidth = if (index == currentPage) 4.dp else 0.dp
            AsyncImage(
                model = imageUrl,
                contentDescription = "Preview of image $index",
                modifier = Modifier
                    .size(60.dp)
                    .padding(horizontal = 3.dp)
                    .clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }.border(
                        width = borderWidth,
                        MaterialTheme.colorScheme.primary
                    ),
                contentScale = ContentScale.Crop
            )
        }
    }

}