package ramirez.ruben.closetvirtual

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ramirez.ruben.closetvirtual.components.NavigationBottomPanel
import ramirez.ruben.closetvirtual.data.database.AppDatabase
import ramirez.ruben.closetvirtual.data.database.repository.PrendaRepository
import ramirez.ruben.closetvirtual.screens.CalendarioScreen
import ramirez.ruben.closetvirtual.screens.DetallePrendaScreen
import ramirez.ruben.closetvirtual.screens.GestionPrendaScreen
import ramirez.ruben.closetvirtual.screens.LoginScreen
import ramirez.ruben.closetvirtual.screens.AgregarOutfitScreen
import ramirez.ruben.closetvirtual.screens.ClosetScreen
import ramirez.ruben.closetvirtual.screens.OutfitsScreen
import ramirez.ruben.closetvirtual.screens.PasswordRecoveryScreen
import ramirez.ruben.closetvirtual.screens.PerfilScreen
import ramirez.ruben.closetvirtual.screens.RegistroDiarioScreen
import ramirez.ruben.closetvirtual.screens.RegisterScreen
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme
import ramirez.ruben.closetvirtual.viewmodel.DetallePrendaViewModel
import ramirez.ruben.closetvirtual.viewmodel.GestionPrendaViewModel
import ramirez.ruben.closetvirtual.data.database.repository.UsuarioRepository
import ramirez.ruben.closetvirtual.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClosetVirtualTheme {
                MainAppScreen()
            }
        }
    }
}

@Composable
fun MainAppScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // INICIALIZACIÓN DE LA BASE DE DATOS (Single Source of Truth)
    val database = remember { AppDatabase.getDatabase(context) }

    val prendaRepository = remember { PrendaRepository(database.prendaDao()) }
    val usuarioRepository = remember { UsuarioRepository(database.usuarioDao()) }

    val usuarioViewModel: UsuarioViewModel = viewModel(
        factory = UsuarioViewModel.Factory(usuarioRepository)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val screensWithoutBottomBar = listOf(
        "login_route", "register_route", "recovery_route",
        "gestion_prenda_route", "gestion_prenda_route/{prendaId}", "detalle_prenda_route/{prendaId}"
    )

    // El panel sale si la ruta actual NO está en la lista de arriba
    val showBottomBar = screensWithoutBottomBar.none { currentRoute?.startsWith(it.substringBefore("/")) == true }

    // Scaffold para estructurar la pantalla
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBottomPanel(navController = navController)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "login_route", //ruta inicial
            modifier = Modifier.padding(innerPadding)
        ) {

            // RUTAS DE USUARIO

            composable("login_route") {
                LoginScreen(
                    onNavigateToRegister = { navController.navigate("register_route") },
                    onLoginSuccess = {
                        navController.navigate("main_route") {
                            popUpTo("login_route") { inclusive = true } // Limpiamos el registro del historial
                        }
                    },
                    viewModel = usuarioViewModel
                )
            }

            composable("register_route") {
                RegisterScreen(
                    onNavigateToLogin = { navController.popBackStack() }, // Regresa atras al login
                    onRegisterSuccess = {
                        navController.navigate("main_route") {
                            popUpTo("register_route") { inclusive = true } // Limpiamos el registro del historial
                        }
                    },
                    viewModel = usuarioViewModel
                )
            }

            composable("recovery_route") {
                PasswordRecoveryScreen(
                    viewModel = usuarioViewModel
                )
            }

            composable("perfil_route") {
                PerfilScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onLogoutClick = {
                        // Limpiamos la sesión en el ViewModel
                        usuarioViewModel.logout()
                        navController.navigate("login_route") {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    },
                    viewModel = usuarioViewModel
                )
            }

            // RUTAS DE ROOM

            // 1. Agregar Prenda
            composable("gestion_prenda_route") {
                val gestionViewModel: GestionPrendaViewModel = viewModel(
                    factory = GestionPrendaViewModel.Factory(prendaRepository, context)
                )

                LaunchedEffect(Unit) {
                    navController.navigate("detalle_prenda_route/cc7aab1c-3860-47a4-854d-4abf025fc0e3")
                }

                GestionPrendaScreen(
                    viewModel = gestionViewModel,
                    isEditMode = false,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // 2. Editar Prenda
            composable(
                route = "gestion_prenda_route/{prendaId}",
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

            // 3. Detalle de Prenda
            composable(
                route = "detalle_prenda_route/{prendaId}",
                arguments = listOf(navArgument("prendaId") { type = NavType.StringType })
            ) { backStackEntry ->
                val prendaId = backStackEntry.arguments?.getString("prendaId") ?: return@composable
                val detalleViewModel: DetallePrendaViewModel = viewModel(
                    factory = DetallePrendaViewModel.Factory(prendaRepository)
                )

                DetallePrendaScreen(
                    prendaId = prendaId,
                    viewModel = detalleViewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onEditClick = { id ->
                        navController.navigate("gestion_prenda_route/$id")
                    }
                )
            }

            // OTRAS PANTALLAS

            composable("agregar_outfit_route") {
                AgregarOutfitScreen()
            }

            composable("main_route") {
                ClosetScreen(
                    onNavigateToRegistroDiario = { navController.navigate("registro_diario_route") }
                )
            }

            composable("closet_route") {
                OutfitsScreen()
            }

            composable("calendario_route") {
                CalendarioScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable("registro_diario_route") {
                RegistroDiarioScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}