package ramirez.ruben.closetvirtual

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ramirez.ruben.closetvirtual.components.NavigationBottomPanel
import ramirez.ruben.closetvirtual.feature.calendario.ui.CalendarioScreen
import ramirez.ruben.closetvirtual.feature.registrodiario.ui.RegistroDiarioScreen
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClosetVirtualTheme {
                MockMainScreen()
            }
        }
    }
}

@Composable
fun MockMainScreen() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBottomPanel(navController = navController)
        }
    ) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {

            NavHost(
                navController = navController,
                startDestination = "calendario_route"
            ) {

                composable("calendario_route") {
                    CalendarioScreen(
                        onNavigateBack = { navController.navigate("registro_diario_route") }
                    )
                }

                composable("registro_diario_route") {
                    RegistroDiarioScreen(
                        onNavigateBack = { navController.popBackStack() }
                    )
                }

                composable("closet_route") { /* Pantalla mock */ }
                composable("main_route") { /* Pantalla mock */ }
                composable("profile_route") { /* Pantalla mock */ }
            }
        }
    }
}