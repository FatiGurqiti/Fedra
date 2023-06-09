package com.fdev.fedra.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.fdev.fedra.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val dummyPhotos = arrayListOf(R.drawable.foto_0, R.drawable.foto_1, R.drawable.foto_2)
    val pagerState = rememberPagerState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        VerticalPager(
            pageCount = dummyPhotos.size,
            state = pagerState,
            key = { dummyPhotos[it] }
        ) { index ->
            Image(
                painter = painterResource(id = dummyPhotos[index]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}