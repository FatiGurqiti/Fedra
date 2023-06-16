package com.fdev.fedra.ui.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.fdev.fedra.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val textFieldCharacterLimit = 256

    val dummyPhotos = arrayListOf(
        R.drawable.foto_0, R.drawable.foto_1, R.drawable.foto_2
    )
    val pagerState = rememberPagerState()

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        sheetPeekHeight = 0.dp,
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetGesturesEnabled = true,
        scaffoldState = scaffoldState,
        drawerGesturesEnabled = true,
        sheetContent = {

            Row(modifier = Modifier.fillMaxWidth()) {

                Spacer(modifier = Modifier.fillMaxWidth(0.2f))

                Text(
                    color = MaterialTheme.colors.secondary,
                    text = "${pagerState.currentPage} Comment",
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )

                IconButton(modifier = Modifier.weight(0.2f),
                    onClick = { scope.launch { sheetState.collapse() } }) {
                    Icon(
                        imageVector = Icons.TwoTone.Close,
                        tint = MaterialTheme.colors.secondary,
                        contentDescription = null,
                    )
                }

            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.8f)
                    .padding(5.dp)
            ) {
                items(3) {
                    ListItem(
                        text = { Text(text = "Item $it", color = MaterialTheme.colors.secondary) },
                        icon = {
                            Icon(
                                Icons.Default.AccountBox,
                                contentDescription = "Localized description",
                                tint = MaterialTheme.colors.secondary
                            )
                        })
                }
            }

            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var commentText by remember { mutableStateOf(TextFieldValue("")) }
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.secondary,
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = MaterialTheme.colors.secondary,
                        unfocusedIndicatorColor = MaterialTheme.colors.secondary
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            Toast.makeText(context, "${commentText.text}", Toast.LENGTH_SHORT)
                                .show()
                        }) {
                            Icon(Icons.Filled.Send, "", tint = MaterialTheme.colors.secondary)
                        }
                    },
                    value = commentText,
                    onValueChange = { newText ->
                        if (newText.text.length <= textFieldCharacterLimit) commentText = newText
                    },
                    placeholder = { Text(text = "Enter your comment") },

                    )
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.8f))
        },
    ) {
        VerticalPager(userScrollEnabled = sheetState.isCollapsed,
            pageCount = dummyPhotos.size,
            state = pagerState,
            key = { dummyPhotos[it] }) { index ->
            Box(modifier = Modifier.fillMaxSize()) {

                Image(
                    painter = painterResource(id = dummyPhotos[index]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                PostDetails(index, dummyPhotos)
                InteractionButtons(scope, sheetState)
            }
        }
    }
}

@Composable
fun PostDetails(index: Int, dummyPhotos: ArrayList<Int>) {
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
                    .zIndex(3f)
                    .clip(CircleShape)
                    .border(2.dp, Color.Green, CircleShape)
            )

            Spacer(modifier = Modifier.fillMaxWidth(0.05f))

            Text(
                text = "$index Username", color = Color.White, fontSize = 18.sp
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
                imageVector = Icons.Filled.LocationOn, tint = Color.White, contentDescription = null
            )

            Text(
                text = "Kurila, Prizren", color = Color.White, fontSize = 16.sp
            )

        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.1f)
        )
    }
}


@Composable
fun GradientBackground() {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom
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
fun InteractionButtons(scope: CoroutineScope, sheetState: BottomSheetState) {
    var isLiked by remember { mutableStateOf(false) }
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
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite
                    else Icons.Filled.FavoriteBorder,
                    tint = if (isLiked) Color.Red else Color.White,
                    contentDescription = null,
                )
                Text(text = "555", color = Color.White)
            }
        }

        IconButton(onClick = {
            scope.launch { sheetState.expand() }
        }) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Filled.Star, tint = Color.White, contentDescription = null
                )
                Text(text = "444", color = Color.White)
            }
        }

        IconButton(onClick = {

        }) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Filled.Person, tint = Color.White, contentDescription = null
                )
                Text(text = "333", color = Color.White)
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.1f)
        )
    }
}