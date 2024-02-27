package eu.tutorials.myrecipeapp2

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()

    NavHost(navController, startDestination = "categoryScreen") {
        composable("categoryScreen") {
            RecipeScreen { category ->
                navController.navigate("categoryDetail/${category.idCategory}")
            }
        }
        composable(
            "categoryDetail/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            val category = categoryId?.let { viewModel.getCategoryById(it) }
            if (category != null) {
                CategoryDetailScreen(category = category) {
                    navController.popBackStack()
                }
            } else {
                // Handle category not found error
            }
        }
    }
}
