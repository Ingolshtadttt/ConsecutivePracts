package com.example.consecutivepracts.screens

import com.example.androidapp.R

sealed class Screen(
    val title: String,
    val icon: Int = -1
) {
    data object Home : Screen("Home", icon = R.drawable.home)
    data object List : Screen("List", icon = R.drawable.list)
    data object Settings : Screen("Settings", icon = R.drawable.settings)
    data object Detail : Screen("Anime", icon = -1)
}