package ramirez.ruben.closetvirtual.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ramirez.ruben.closetvirtual.R

//esta clase luego la tengo que poner en el main activity con un scaffold y asi para que se vea en todas las pantallas, lo hare cuando ya juntemos todo

@Composable
fun NavigationBottomPanel(navController: NavController) {
    // checa la seleccion del usuario del panel
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary
    ) {

        NavigationBarItem(
            icon = {
                Icon(painterResource(id = R.mipmap.closet_icon), contentDescription = "Clóset")
                   },

            label = {
                Text("Clóset")
                    },
            selected =
                currentRoute == "closet_route",

            onClick = {
                navController.navigate("closet_route")
            }
        )

        NavigationBarItem(
            icon = {
                Icon(painterResource(id = R.mipmap.main_icon), contentDescription = "Principal")
                   },

            label = {
                Text("Inicio")
                    },

            selected =
                currentRoute == "main_route",

            onClick = {
                navController.navigate("main_route")
            }
        )

        NavigationBarItem(
            icon = {
                Icon(painterResource(id = R.mipmap.calendario_icon), contentDescription = "Calendario")
                   },

            label = {
                Text("Calendario")
                    },

            selected =
                currentRoute == "calendario_route",

            onClick = {
                navController.navigate("calendario_route")
            }
        )

        NavigationBarItem(
            icon = {
                Icon(painterResource(id = R.mipmap.profile_icon), contentDescription = "Perfil"
                ) },

            label = {
                Text("Perfil")
                    },

            selected =
                currentRoute == "profile_route",

            onClick = {
                navController.navigate("profile_route")
            }
        )
    }
}