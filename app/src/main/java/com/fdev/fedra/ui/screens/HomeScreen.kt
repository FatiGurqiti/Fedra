package com.fdev.fedra.ui.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.fdev.fedra.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
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
            var isLiked by remember { mutableStateOf(false) }

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
                        .fillMaxHeight()
                        .fillMaxWidth(0.8f)
                        .zIndex(3f),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {

                        Spacer(modifier = Modifier.fillMaxSize(0.05f))

                        Image(
                            painter = painterResource(id = dummyPhotos[index]),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth(0.21f)
                                .fillMaxHeight(0.08f)
                                .clip(CircleShape)
                                .border(2.dp, Color.Green, CircleShape)
                        )

                        Text(
                            text = "$index Username",
                            color = Color.White,
                            modifier = Modifier.padding(PaddingValues(20.dp, 20.dp, 0.dp, 0.dp)),
                            fontSize = 18.sp
                        )

                    }
                    Text(
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                        color = Color.White,
                        modifier = Modifier.padding(10.dp),
                        fontSize = 14.sp
                    )

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            tint = Color.White,
                            contentDescription = null,
                            modifier = Modifier.padding(PaddingValues(10.dp, 0.dp, 10.dp, 0.dp)),
                        )

                        Text(
                            text = "Kurila, Prizren",
                            color = Color.White,
                            fontSize = 16.sp
                        )

                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.1f)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .zIndex(3f),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {

                    IconButton(onClick = {
                        isLiked = !isLiked
                    }) {
                        Icon(
                            imageVector = if (isLiked) Icons.Filled.Favorite
                            else Icons.Filled.FavoriteBorder,
                            tint = if (isLiked) Color.Red else Color.White,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(0.1f)
                                .padding(PaddingValues(0.dp, 0.dp, 10.dp, 0.dp))
                        )
                    }

                    IconButton(onClick = {
                        Toast.makeText(context,"You opened comments",Toast.LENGTH_SHORT).show()
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.comment),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(0.1f)
                                .padding(PaddingValues(0.dp, 0.dp, 10.dp, 0.dp))
                        )
                    }

                    IconButton(onClick = {
                        Toast.makeText(context,"99 peple has seen this",Toast.LENGTH_SHORT).show()
                    },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            tint = Color.White,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(0.1f)
                                .padding(PaddingValues(0.dp, 0.dp, 10.dp, 0.dp))
                        )
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