package ramirez.ruben.closetvirtual.screens.registrodiario.ui

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
import androidx.compose.ui.graphics.ColorFilter
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
    // Usare prendasSeleccionadas para guardar las prendas como mock (duh) que el usuario seleccione
    val prendasSeleccionadas = remember { mutableStateListOf<Int>() }

    // Estructura principal de registro
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

            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.offset(x = (-24).dp)
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.arrow_back),
                    contentDescription = "Icono de atrás",
                    tint = Color(0xFF26657A),
                    modifier = Modifier.size(22.dp)
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

        // Rachita, la del inicio esta "encendida" como ejemplo (mockeado)
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
                modifier = Modifier.size(40.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
            Image(
                painter = painterResource(id = R.mipmap.coldstreak_icon),
                contentDescription = "Racha inactiva",
                modifier = Modifier.size(40.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
            Image(
                painter = painterResource(id = R.mipmap.coldstreak_icon),
                contentDescription = "Racha inactiva",
                modifier = Modifier.size(40.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
            Image(
                painter = painterResource(id = R.mipmap.coldstreak_icon),
                contentDescription = "Racha inactiva",
                modifier = Modifier.size(40.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        }

        Text(
            text = "¡Registra tu outfit dirariamente para continuar con tu racha!",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Image(
            painter = painterResource(id = R.mipmap.dress_icon),
            contentDescription = "Icono de vestido",
            modifier = Modifier
                .size(64.dp)
                .padding(bottom = 16.dp)
        )

        // Prendas de PrendaMock
        Surface(
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "SELECCIONA TUS PRENDAS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Mostrar las prendas en dos columnas juntas cuadricula
                val filas = prendasMock.chunked(2)
                filas.forEach { fila ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Llama a la prendamockitem que es la que dibuja los cuadritos/tarjetitas de las prendas
                        fila.forEach { prenda ->
                            PrendaMockItem(
                                prenda = prenda,
                                isSelected = prendasSeleccionadas.contains(prenda.id),
                                onClick = {
                                    if (prendasSeleccionadas.contains(prenda.id)) {
                                        prendasSeleccionadas.remove(prenda.id)
                                    } else {
                                        prendasSeleccionadas.add(prenda.id)
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (fila.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Boton para guardar el outfit/prendas del dia (mock)
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF264653)
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                "GUARDAR REGISTRO",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// Componentes visuales de las prendas en columnas (Crea las tarjetitas de las prendas)
@Composable
fun PrendaMockItem(
    prenda: PrendaMock,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    // Colorsitos
    val highlightColor = Color(0xFF26657A)
    val bgColor = if (isSelected) highlightColor.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
    val borderColor = if (isSelected) highlightColor else Color.Transparent

    // Mock de prendas favoritas
    var isFavorite by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .background(bgColor, RoundedCornerShape(12.dp))
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(12.dp),
        contentAlignment = Alignment.TopStart
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {

            // Textos superiores
            Text(
                text = prenda.nombre,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(end = 24.dp)
            )

            Text(
                text = prenda.marca,
                fontSize = 10.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.mipmap.no_image),
                contentDescription = "Imagen de la prenda",
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = prenda.temporada,
                        fontSize = 10.sp,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = prenda.categoria,
                        fontSize = 10.sp,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Boton de fav (mock)
                Image(
                    painter = painterResource(
                        id = if (isFavorite) R.mipmap.favorite_icon else R.mipmap.unfavorite_icon
                    ),
                    contentDescription = "Favorito",
                    modifier = Modifier
                        .size(12.dp)
                        .clickable { isFavorite = !isFavorite }
                )
            }
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