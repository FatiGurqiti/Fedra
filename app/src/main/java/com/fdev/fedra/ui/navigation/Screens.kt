package com.fdev.fedra.ui.navigation

sealed class Screens(var route: String) {
    object HomeScreen : Screens("home_screen")
    object PostScreen : Screens("post_screen")
    object ProfileScreen : Screens("profile_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}
