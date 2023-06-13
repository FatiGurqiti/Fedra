package com.fdev.fedra.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.fdev.fedra.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
@Preview
fun HomeScreen() {
    val dummyPhotos = arrayListOf(
        R.drawable.foto_0,
        R.drawable.foto_1,
        R.drawable.foto_2,
        R.drawable.foto_0,
        R.drawable.foto_1,
        R.drawable.foto_2,
        R.drawable.foto_0,
        R.drawable.foto_1,
        R.drawable.foto_2
    )
    val pagerState = rememberPagerState()
    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        VerticalPager(
            userScrollEnabled = drawerState.isClosed,
            pageCount = dummyPhotos.size,
            state = pagerState,
            key = { dummyPhotos[it] }
        ) { index ->
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = dummyPhotos[index]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                PostDetails(drawerState, index, dummyPhotos)
                InteractionButtons(drawerState)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostDetails(drawerState: BottomDrawerState, index: Int, dummyPhotos: ArrayList<Int>) {
    if (drawerState.isClosed) {
        GradientBackground()
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .zIndex(3f)
                .fillMaxWidth(0.8f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(15.dp, 5.dp, 0.dp, 0.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = dummyPhotos[index]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(0.158f)
                        .fillMaxHeight(0.06f)
                        .clip(CircleShape)
                        .border(2.dp, Color.Green, CircleShape)
                )

                Spacer(modifier = Modifier.fillMaxWidth(0.05f))

                Text(
                    text = "$index Username",
                    color = Color.White,
                    fontSize = 18.sp
                )

            }
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                color = Color.White,
                modifier = Modifier.padding(PaddingValues(20.dp, 10.dp, 0.dp, 0.dp)),
                fontSize = 14.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(15.dp, 5.dp, 5.dp, 0.dp)),
                verticalAlignment = Alignment.Bottom
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    tint = Color.White,
                    contentDescription = null
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InteractionButtons(drawerState: BottomDrawerState) {
    var isLiked by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    BottomDrawer(
        gesturesEnabled = drawerState.isOpen,
        drawerState = drawerState,
        drawerContent = {
                IconButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { scope.launch { drawerState.close() } }
                )
                {
                    Icon(
                        imageVector = Icons.TwoTone.Close,
                        tint = Color.Black,
                        contentDescription = null,
                    )
                }
                LazyColumn() {
                    items(25) {
                        ListItem(
                            text = { Text("Item $it") },
                            icon = {
                                Icon(
                                    Icons.Default.AccountBox,
                                    contentDescription = "Localized description"
                                )
                            }
                        )
                    }
                }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PaddingValues(7.dp, 0.dp)),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                IconButton(
                    onClick = {
                        isLiked = !isLiked
                    },
                )
                {
                    Icon(
                        modifier = Modifier.fillMaxSize(0.1f),
                        imageVector = if (isLiked) Icons.Filled.Favorite
                        else Icons.Filled.FavoriteBorder,
                        tint = if (isLiked) Color.Red else Color.White,
                        contentDescription = null,
                    )
                }

                IconButton(
                    onClick = {
                        scope.launch { drawerState.open() }
                    }) {
                    Icon(
                        modifier = Modifier.fillMaxSize(0.1f),
                        imageVector = Icons.Filled.Star,
                        tint = Color.White,
                        contentDescription = null
                    )
                }

                IconButton(
                    onClick = {

                    }) {
                    Icon(
                        modifier = Modifier.fillMaxSize(0.1f),
                        imageVector = Icons.Filled.Person,
                        tint = Color.White,
                        contentDescription = null
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.1f)
                )
            }
        }
    )
}