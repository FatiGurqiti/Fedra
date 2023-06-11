package com.fdev.fedra.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = dummyPhotos[index]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )

                GradientBackground()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(3f),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "username", color = Color.White, modifier = Modifier.padding(20.dp))
                        Text(text = "username", color = Color.White, modifier = Modifier.padding(20.dp))
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.1f)
                    )
                }
            }
        }
    }
}

@Composable
fun GradientBackground() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        val gradientBrush = Brush.verticalGradient(
            colors = listOf(Color(0x00000000), Color(0x99000000))
        )
        Box(
            modifier = Modifier
                .fillMaxHeight(.5f)
                .fillMaxWidth()
                .background(brush = gradientBrush)
                .zIndex(2f)
        )
    }
}