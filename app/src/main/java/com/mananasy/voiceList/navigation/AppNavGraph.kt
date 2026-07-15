package com.mananasy.voiceList.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mananasy.voiceList.core.ui.BottomNavItem
import com.mananasy.voiceList.feature.history.ui.HistoryScreen
import com.mananasy.voiceList.feature.favorites.ui.FavoritesScreen
import com.mananasy.voiceList.feature.singer.ui.SingerListScreen
import com.mananasy.voiceList.feature.singer.ui.SingerDetailScreen

@Composable
fun AppNavGraph(navController: NavHostController, modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.MyList.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.History.route) { HistoryScreen(navController) }
        composable(BottomNavItem.Favorites.route) { FavoritesScreen(navController) }
        composable(BottomNavItem.MyList.route) { SingerListScreen(navController) }

        composable(
            route = "singer_detail/{singerId}",
            arguments = listOf(navArgument("singerId") { type = NavType.IntType })
        ) { backStackEntry ->
            val singerId = backStackEntry.arguments?.getInt("singerId") ?: return@composable
            SingerDetailScreen(singerId = singerId, navController = navController)
        }
    }
}