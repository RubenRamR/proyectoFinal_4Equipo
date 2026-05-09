package ramirez.ruben.closetvirtual.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ramirez.ruben.closetvirtual.data.database.AppDatabase
import ramirez.ruben.closetvirtual.screens.DetallePrendaScreen
import ramirez.ruben.closetvirtual.screens.GestionPrendaScreen
import ramirez.ruben.closetvirtual.viewmodel.DetallePrendaViewModel
import ramirez.ruben.closetvirtual.viewmodel.GestionPrendaViewModel
import ramirez.ruben.closetvirtual.data.database.repository.PrendaRepository

@Composable
fun ClosetVirtualNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val database = remember { AppDatabase.getDatabase(context) }
    val repository = remember { PrendaRepository(database.prendaDao()) }

    NavHost(
        navController = navController,
        startDestination = "gestion_prenda" // Pantalla principal a iniciar
    ) {

        // --- RUTA 1: GESTIÓN DE PRENDAS (MODO REGISTRO NUEVO) ---
        composable("gestion_prenda") {
            val gestionViewModel: GestionPrendaViewModel = viewModel(
                factory = GestionPrendaViewModel.Factory(repository, context)
            )

            GestionPrendaScreen(
                viewModel = gestionViewModel,
                isEditMode = false,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // --- RUTA 2: GESTIÓN DE PRENDAS (MODO EDICIÓN) ---
        composable(
            route = "gestion_prenda/{prendaId}",
            arguments = listOf(navArgument("prendaId") { type = NavType.StringType })
        ) { backStackEntry ->
            val prendaId = backStackEntry.arguments?.getString("prendaId")

            val gestionViewModel: GestionPrendaViewModel = viewModel(
                factory = GestionPrendaViewModel.Factory(repository, context)
            )

            LaunchedEffect(prendaId) {
                prendaId?.let { gestionViewModel.cargarPrendaParaEdicion(it) }
            }

            GestionPrendaScreen(
                viewModel = gestionViewModel,
                isEditMode = true,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // --- RUTA 3: DETALLE DE PRENDA ---
        composable("detalle_prenda/{prendaId}") { backStackEntry ->
            val prendaId = backStackEntry.arguments?.getString("prendaId") ?: return@composable

            val detalleViewModel: DetallePrendaViewModel = viewModel(
                factory = DetallePrendaViewModel.Factory(repository)
            )

            DetallePrendaScreen(
                prendaId = prendaId,
                viewModel = detalleViewModel,
                onNavigateBack = { navController.popBackStack() },
                onEditClick = { id ->
                    navController.navigate("gestion_prenda/$id")
                }
            )
        }

        // Resto de pantallas...
    }
}