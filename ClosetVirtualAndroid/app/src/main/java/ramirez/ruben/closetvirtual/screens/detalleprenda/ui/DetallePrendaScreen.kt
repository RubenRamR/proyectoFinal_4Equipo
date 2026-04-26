package ramirez.ruben.closetvirtual.screens.detalleprenda.ui

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme


// Modelo de dotos
data class PrendaDetalleUiState(
    val nombre: String = "Oblivius Topless",
    val marca: String = "Nike",
    val usosTotales: Int = 30,
    val usosPorMes: Int = 4,
    val esEstampada: Boolean = true,
    val categoria: String = "Top",
    val temporada: String = "Otoño",
    val tags: List<String> = listOf(
        "Tag 1",
        "Tag 2",
        "Tag 3",
        "Tag 4",
        "Tag 5",
        "Tag 6",
        "Tag 6",
        "Tag 6",
        "Tag 6"
    ),
    val imageUri: Uri? = null
)

// PANTALLA PRINCIPAL
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallePrendaScreen(
    state: PrendaDetalleUiState = PrendaDetalleUiState(),
    onNavigateBack: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color(0xFF26657A)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
        ) {

            // 1. Foto, Nombre y Marca
            SeccionCabeceraDetalle(state)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // 2. Estadísticas y Estampado
            SeccionEstadisticas(state)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // 3. Atributos: Categoría y Temporada
            SeccionAtributosEstaticos(state)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // 4. Tags
            SeccionTagsLectura(state.tags)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // 5. Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onEditClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF26657A)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Editar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Button(
                    onClick = onDeleteClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Eliminar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SeccionCabeceraDetalle(state: PrendaDetalleUiState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (state.imageUri != null) {
                AsyncImage(
                    model = state.imageUri,
                    contentDescription = "Foto de la prenda",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Sin Foto",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Textos
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = state.nombre,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = state.marca,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun SeccionEstadisticas(state: PrendaDetalleUiState) {
    val isDark = isSystemInDarkTheme()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                "Usos",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "${state.usosTotales} usos totales",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "${state.usosPorMes} x mes, apróx.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Estampada", fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.width(8.dp))
            Checkbox(
                checked = state.esEstampada,
                onCheckedChange = {},
                colors = CheckboxDefaults.colors(
                    checkedColor = if (isDark) Color.White else Color(0xFF6750A4),
                    checkmarkColor = if (isDark) Color(0xFF5E9C94) else Color.White,
                    uncheckedColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}

@Composable
private fun SeccionAtributosEstaticos(state: PrendaDetalleUiState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                "Categoria",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(state.categoria, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                "Temporada",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(state.temporada, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
private fun SeccionTagsLectura(tags: List<String>) {
    val filaSuperior = tags.filterIndexed { index, _ -> index % 2 == 0 }
    val filaInferior = tags.filterIndexed { index, _ -> index % 2 != 0 }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

            // FILA 1
            if (filaSuperior.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    filaSuperior.forEach { tag ->
                        Surface(
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Filled.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.onTertiary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    tag,
                                    color = MaterialTheme.colorScheme.onTertiary,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            // FILA 2
            if (filaInferior.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    filaInferior.forEach { tag ->
                        Surface(
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Filled.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.onTertiary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    tag,
                                    color = MaterialTheme.colorScheme.onTertiary,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// PREVIEWS
@Preview(name = "1. Detalle (Claro)", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewDetalleClaro() {
    ClosetVirtualTheme(darkTheme = false) {
        DetallePrendaScreen()
    }
}

@Preview(
    name = "2. Detalle (Oscuro)",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewDetalleOscuro() {
    ClosetVirtualTheme(darkTheme = true) {
        DetallePrendaScreen()
    }
}