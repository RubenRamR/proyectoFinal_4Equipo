package ramirez.ruben.closetvirtual.feature.registrodiario.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramirez.ruben.closetvirtual.R
import ramirez.ruben.closetvirtual.data.PrendaMock
import ramirez.ruben.closetvirtual.data.prendasMock
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme

@Composable
fun RegistroDiarioScreen(onNavigateBack: () -> Unit = {}) {
    var busqueda by remember { mutableStateOf("") }
    // Estado para guardar las prendas (mock) que el usuario seleccione
    val prendasSeleccionadas = remember { mutableStateListOf<Int>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color(0xFF26657A)
                )
            }
        }

        Text(
            text = "Registro Diario",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Rachita
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.streak_icon),
                contentDescription = "Racha activa",
                modifier = Modifier.size(40.dp)
            )
            Image(
                painter = painterResource(id = R.mipmap.coldstreak_icon),
                contentDescription = "Racha inactiva",
                modifier = Modifier.size(40.dp)
            )
            Image(
                painter = painterResource(id = R.mipmap.coldstreak_icon),
                contentDescription = "Racha inactiva",
                modifier = Modifier.size(40.dp)
            )
            Image(
                painter = painterResource(id = R.mipmap.coldstreak_icon),
                contentDescription = "Racha inactiva",
                modifier = Modifier.size(40.dp)
            )
            Image(
                painter = painterResource(id = R.mipmap.coldstreak_icon),
                contentDescription = "Racha inactiva",
                modifier = Modifier.size(40.dp)
            )
        }


    }
}



@Preview(name = "Modo Claro", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewModoClaro() {
    ClosetVirtualTheme(darkTheme = false) {
        RegistroDiarioScreen()
    }
}

@Preview(
    name = "Modo Oscuro",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewModoOscuro() {
    ClosetVirtualTheme(darkTheme = true) {
        RegistroDiarioScreen()
    }
}