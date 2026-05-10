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
import ramirez.ruben.closetvirtual.data.database.repository.UsuarioRepository
import ramirez.ruben.closetvirtual.screens.LoginScreen
import ramirez.ruben.closetvirtual.screens.PerfilScreen
import ramirez.ruben.closetvirtual.screens.RegisterScreen
import ramirez.ruben.closetvirtual.viewmodel.UsuarioViewModel

@Composable
fun ClosetVirtualNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val database = remember { AppDatabase.getDatabase(context) }
    val prendaRepository = remember { PrendaRepository(database.prendaDao()) }
    val usuarioRepository = remember { UsuarioRepository(database.usuarioDao()) }

    val usuarioViewModel: UsuarioViewModel = viewModel(
        factory = UsuarioViewModel.Factory(usuarioRepository)
    )

    NavHost(
        navController = navController,
        startDestination = "login" // empezamos  en login
    ) {

        // rutas del usuario

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
                factory = GestionPrendaViewModel.Factory(prendaRepository, context)
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
            arguments = listOf(navArgument("prendaId") { type = NavType.StringType })
        ) { backStackEntry ->
            val prendaId = backStackEntry.arguments?.getString("prendaId")

            val gestionViewModel: GestionPrendaViewModel = viewModel(
                factory = GestionPrendaViewModel.Factory(prendaRepository, context)
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

        // DETALLE DE PRENDA
        composable("detalle_prenda/{prendaId}") { backStackEntry ->
            val prendaId = backStackEntry.arguments?.getString("prendaId") ?: return@composable

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

    }
}