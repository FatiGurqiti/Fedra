package com.fdev.fedra.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fdev.fedra.domain.models.BottomNavItem

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier,
    onClickEvent: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        elevation = 5.dp
    ) {
        items.forEach {
            val selected = it.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                modifier = Modifier.background(MaterialTheme.colors.background),
                selected = selected,
                selectedContentColor = MaterialTheme.colors.surface,
                unselectedContentColor = Color.Gray,
                onClick = { onClickEvent(it) },
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (it.badgeCount > 0) {
                            BadgedBox(badge = { Badge { Text(it.badgeCount.toString()) } }) {
                                Icon(
                                    it.icon,
                                    contentDescription = it.name
                                )
                            }
                        } else {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = it.name
                            )
                        }
                        if (selected) {
                            Text(
                                text = it.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}