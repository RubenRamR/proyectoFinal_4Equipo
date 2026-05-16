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
import ramirez.ruben.closetvirtual.data.database.repository.OutfitRepository
import ramirez.ruben.closetvirtual.screens.DetallePrendaScreen
import ramirez.ruben.closetvirtual.screens.GestionPrendaScreen
import ramirez.ruben.closetvirtual.viewmodel.DetallePrendaViewModel
import ramirez.ruben.closetvirtual.viewmodel.GestionPrendaViewModel
import ramirez.ruben.closetvirtual.data.database.repository.PrendaRepository
import ramirez.ruben.closetvirtual.data.database.repository.UsuarioRepository
import ramirez.ruben.closetvirtual.data.datastore.DataStoreManager
import ramirez.ruben.closetvirtual.screens.CalendarioScreen
import ramirez.ruben.closetvirtual.screens.LoginScreen
import ramirez.ruben.closetvirtual.screens.PantallaDetalleOutfit
import ramirez.ruben.closetvirtual.screens.PerfilScreen
import ramirez.ruben.closetvirtual.screens.RegisterScreen
import ramirez.ruben.closetvirtual.viewmodel.CalendarioViewModel
import ramirez.ruben.closetvirtual.viewmodel.DetalleOutfitViewModel
import ramirez.ruben.closetvirtual.viewmodel.UsuarioViewModel

@Composable
fun ClosetVirtualNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val database = remember { AppDatabase.getDatabase(context) }
    val dataStoreManager = remember { DataStoreManager(context) }

    // Repositorios
    val prendaRepository = remember { PrendaRepository(database.prendaDao()) }
    val usuarioRepository = remember { UsuarioRepository(database.usuarioDao()) }

    val outfitRepository = remember { OutfitRepository(database.outfitDao()) }

    val usuarioViewModel: UsuarioViewModel = viewModel(
        factory = UsuarioViewModel.Factory(usuarioRepository)
    )

    NavHost(
        navController = navController,
        startDestination = "login" // Empezamos en el login
    ) {

        // Rutas del usuario
        composable("login") {
            LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onLoginSuccess = { navController.navigate("gestion_prenda") },
                viewModel = usuarioViewModel
            )
        }

        composable("register") {
            RegisterScreen(
                onNavigateToLogin = { navController.popBackStack() },
                onRegisterSuccess = { navController.navigate("gestion_prenda") },
                viewModel = usuarioViewModel
            )
        }

        composable("perfil") {
            PerfilScreen(
                onNavigateBack = { navController.popBackStack() },
                onLogoutClick = {
                    usuarioViewModel.logout()
                    navController.navigate("login") {
                        popUpTo(0) // Limpia el backstack al cerrar sesión
                    }
                },
                viewModel = usuarioViewModel
            )
        }

        // GESTIÓN DE PRENDAS - MODO REGISTRO NUEVO
        composable("gestion_prenda") {
            val gestionViewModel: GestionPrendaViewModel = viewModel(
                factory = GestionPrendaViewModel.Factory(prendaRepository, dataStoreManager, context)
            )

            GestionPrendaScreen(
                viewModel = gestionViewModel,
                isEditMode = false,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // GESTIÓN DE PRENDAS - MODO EDICION
        composable(
            route = "gestion_prenda/{prendaId}",
            arguments = listOf(navArgument("prendaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val prendaId = backStackEntry.arguments?.getInt("prendaId") ?: 0

            val gestionViewModel: GestionPrendaViewModel = viewModel(
                factory = GestionPrendaViewModel.Factory(prendaRepository, dataStoreManager, context)
            )

            LaunchedEffect(prendaId) {
                if (prendaId != 0) {
                    gestionViewModel.cargarPrendaParaEdicion(prendaId)
                }
            }

            GestionPrendaScreen(
                viewModel = gestionViewModel,
                isEditMode = true,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // DETALLE DE PRENDA
        composable(
            route = "detalle_prenda/{prendaId}",
            arguments = listOf(navArgument("prendaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val prendaId = backStackEntry.arguments?.getInt("prendaId") ?: return@composable

            val detalleViewModel: DetallePrendaViewModel = viewModel(
                factory = DetallePrendaViewModel.Factory(prendaRepository)
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

        // Detalle calendario
        composable("calendario_route") {
            // Nota: Asegúrate de crear el Factory para CalendarioViewModel de forma similar a los demás
            val calendarioViewModel: CalendarioViewModel = viewModel(
                factory = CalendarioViewModel.Factory(outfitRepository, dataStoreManager)
            )

            CalendarioScreen(
                viewModel = calendarioViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetalleOutfit = { outfitId ->
                    navController.navigate("detalle_outfit/$outfitId")
                }
            )
        }

        // Detalle outfit
        composable(
            route = "detalle_outfit/{outfitId}",
            arguments = listOf(navArgument("outfitId") { type = NavType.IntType })
        ) { backStackEntry ->
            val outfitId = backStackEntry.arguments?.getInt("outfitId") ?: return@composable

            val detalleOutfitViewModel: DetalleOutfitViewModel = viewModel(
                factory = DetalleOutfitViewModel.Factory(outfitRepository, outfitId)
            )

            PantallaDetalleOutfit(
                viewModel = detalleOutfitViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetallePrenda = { prendaId ->
                    navController.navigate("detalle_prenda/$prendaId")
                }
            )
        }

    }
}