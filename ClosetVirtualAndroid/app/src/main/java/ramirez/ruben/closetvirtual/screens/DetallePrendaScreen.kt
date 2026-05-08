package ramirez.ruben.closetvirtual.screens

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ramirez.ruben.closetvirtual.R
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme

// Modelo de datos (Se mantiene igual, la lógica cambia en el receptor)
data class PrendaDetalleUiState(
    val nombre: String = "Oblivius Topless",
    val marca: String = "Nike",
    val usosTotales: Int = 30,
    val usosPorMes: Int = 4,
    val esEstampada: Boolean = true,
    val categoria: String = "Top",
    val temporada: String = "Otoño",
    val tags: List<String> = listOf("Vintage", "Favorito", "Casual"),
    val imageUri: Uri? = null
)

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
                            contentDescription = stringResource(R.string.cd_back),
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
            SeccionCabeceraDetalle(state)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            SeccionEstadisticas(state)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            SeccionAtributosEstaticos(state)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            SeccionTagsLectura(state.tags)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // Botones con Estilos Globales
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
                        text = stringResource(R.string.btn_edit),
                        style = MaterialTheme.typography.labelLarge,
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
                        text = stringResource(R.string.btn_delete),
                        style = MaterialTheme.typography.labelLarge,
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
                    contentDescription = stringResource(R.string.cd_prenda_photo),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = stringResource(R.string.cd_no_photo),
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = state.nombre,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = state.marca,
                style = MaterialTheme.typography.bodyLarge,
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
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(R.string.label_uses),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            // Uso de strings con formato dinámico
            Text(
                text = stringResource(R.string.uses_total_count, state.usosTotales),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.uses_per_month_approx, state.usosPorMes),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.label_printed),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = if (state.esEstampada) Icons.Default.Check else Icons.Default.Close,
                contentDescription = if (state.esEstampada) stringResource(R.string.cd_is_printed)
                else stringResource(R.string.cd_not_printed),
                tint = if (state.esEstampada) {
                    if (isDark) Color(0xFF5E9C94) else Color(0xFF2D4B55)
                } else {
                    MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
                }
            )
        }
    }
}

@Composable
private fun SeccionAtributosEstaticos(state: PrendaDetalleUiState) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.hint_category),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(text = state.categoria, style = MaterialTheme.typography.bodyMedium)
        }
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.hint_season),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(text = state.temporada, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun SeccionTagsLectura(tags: List<String>) {
    val filaSuperior = tags.filterIndexed { index, _ -> index % 2 == 0 }
    val filaInferior = tags.filterIndexed { index, _ -> index % 2 != 0 }

    Row(modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState())) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(filaSuperior, filaInferior).forEach { fila ->
                if (fila.isNotEmpty()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        fila.forEach { tag ->
                            Surface(
                                color = MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 6.dp
                                    ),
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
                                        text = tag,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onTertiary
                                    )
                                }
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