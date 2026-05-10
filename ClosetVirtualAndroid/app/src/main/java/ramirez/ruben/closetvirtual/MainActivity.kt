package ramirez.ruben.closetvirtual

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import ramirez.ruben.closetvirtual.data.database.repository.UsuarioRepository
import ramirez.ruben.closetvirtual.data.datastore.DataStoreManager
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
import ramirez.ruben.closetvirtual.viewmodel.ClosetViewModel
import ramirez.ruben.closetvirtual.data.database.repository.OutfitRepository
import ramirez.ruben.closetvirtual.viewmodel.AgregarOutfitViewModel
import ramirez.ruben.closetvirtual.viewmodel.OutfitsViewModel
import ramirez.ruben.closetvirtual.utils.ImageExport
import ramirez.ruben.closetvirtual.viewmodel.UsuarioViewModel
import ramirez.ruben.closetvirtual.viewmodel.LoginViewModel

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // es para detectar la primera vez que corremos la app
        // y entonces exportar las imagenes que tenemos  en drawable al telefono
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        if (!prefs.getBoolean("images_exported", false)) {
            ImageExport.saveDrawablesToGallery(this)
            prefs.edit().putBoolean("images_exported", true).apply()
        }

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
    val scope = rememberCoroutineScope()

    // INICIALIZACIÓN DE LA BASE DE DATOS (Single Source of Truth)
    val database = remember { AppDatabase.getDatabase(context) }
    val prendaRepository = remember { PrendaRepository(database.prendaDao()) }
    val usuarioRepository = remember { UsuarioRepository(database.usuarioDao()) }
    val outfitRepository = remember { OutfitRepository(database.outfitDao()) }
    val dataStoreManager = remember { DataStoreManager(context) }

    //para el autologin en caso de que ya este logeado con el usuario
//    val userId by dataStoreManager.getUserId.collectAsState(initial = null)
//    LaunchedEffect(userId) {
//        if (userId != null) {
//            // si hay un usuario guardado, vamos directamente a la pantalla principal
//            // Usamos popUpTo para limpiar el stack y que no se pueda volver al login con atrás
//            navController.navigate("main_route") {
//                popUpTo("login_route") { inclusive = true }
//            }
//        }
//    }


    val usuarioViewModel: UsuarioViewModel = viewModel(
        factory = UsuarioViewModel.Factory(usuarioRepository)
    )

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModel.Factory(usuarioRepository, dataStoreManager)
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
                    viewModel = usuarioViewModel,
                    loginViewModel = loginViewModel
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
                    viewModel = usuarioViewModel,
                    loginViewModel = loginViewModel
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
                    viewModel = usuarioViewModel,
                    loginViewModel = loginViewModel
                )
            }

            // RUTAS DE ROOM

            // 1. Agregar Prenda
            composable("gestion_prenda_route") {
                val gestionViewModel: GestionPrendaViewModel = viewModel(
                    factory = GestionPrendaViewModel.Factory(prendaRepository, dataStoreManager, context)
                )

//                LaunchedEffect(Unit) {
//                    navController.navigate("detalle_prenda_route/cc7aab1c-3860-47a4-854d-4abf025fc0e3")
//                }

                GestionPrendaScreen(
                    viewModel = gestionViewModel,
                    isEditMode = false,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // 2. Editar Prenda
            composable(
                route = "gestion_prenda_route/{prendaId}",
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

            // 3. Detalle de Prenda
            composable(
                route = "detalle_prenda_route/{prendaId}",
                arguments = listOf(navArgument("prendaId") { type = NavType.IntType })
            ) { backStackEntry ->
                val prendaId = backStackEntry.arguments?.getInt("prendaId") ?: 0
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
                val agregarOutfitViewModel: AgregarOutfitViewModel = viewModel(
                    factory = AgregarOutfitViewModel.Factory(prendaRepository, outfitRepository, dataStoreManager)
                )
                AgregarOutfitScreen(
                    viewModel = agregarOutfitViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable("main_route") {
                val closetViewModel: ClosetViewModel = viewModel(
                    factory = ClosetViewModel.Factory(prendaRepository, dataStoreManager)
                )
                ClosetScreen(
                    viewModel = closetViewModel,
                    onNavigateToRegistroDiario = { navController.navigate("registro_diario_route") },
                    onNavigateToGestionPrenda = { navController.navigate("gestion_prenda_route") },
                    onNavigateToDetalle = { id -> navController.navigate("detalle_prenda_route/$id") }
                )
            }

            composable("closet_route") {
                val outfitsViewModel: OutfitsViewModel = viewModel(
                    factory = OutfitsViewModel.Factory(outfitRepository, dataStoreManager)
                )
                OutfitsScreen(
                    onNavigateToRegistroDiario = { navController.navigate("registro_diario_route") },
                    onNavigateToAgregarOutfit = { navController.navigate("agregar_outfit_route") },
                    viewModel = outfitsViewModel
                )
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