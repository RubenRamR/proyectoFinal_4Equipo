package ramirez.ruben.closetvirtual.screens

import android.content.res.Configuration
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.flowOf
import ramirez.ruben.closetvirtual.R
import ramirez.ruben.closetvirtual.data.database.dao.PrendaDao
import ramirez.ruben.closetvirtual.data.database.entity.PrendaEntity
import ramirez.ruben.closetvirtual.data.database.repository.PrendaRepository
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme
import ramirez.ruben.closetvirtual.viewmodel.DetallePrendaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallePrendaScreen(
    prendaId: Int,
    viewModel: DetallePrendaViewModel,
    onNavigateBack: () -> Unit = {},
    onEditClick: (Int) -> Unit = {}
) {
    LaunchedEffect(prendaId) {
        viewModel.cargarPrenda(prendaId)
    }

    val prenda by viewModel.prenda.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.offset(x = (-12).dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.mipmap.left),
                            contentDescription = "Icono de atrás",
                            tint = Color(0xFF26657A),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { paddingValues ->
        if (prenda == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val prendaActual = prenda!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                SeccionCabeceraDetalle(prendaActual)

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 24.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                SeccionEstadisticas(prendaActual)

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 24.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                SeccionAtributosEstaticos(prendaActual)

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 24.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                SeccionTagsLectura(prendaActual.tags)

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 24.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { onEditClick(prendaActual.id) },
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
                        onClick = { viewModel.eliminarPrendaActual(onSuccess = onNavigateBack) },
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
}

@Composable
private fun SeccionCabeceraDetalle(prenda: PrendaEntity) {
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
            if (prenda.imagen != null) {
                AsyncImage(
                    model = prenda.imagen,
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
                text = prenda.nombre,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            if (!prenda.marca.isNullOrBlank()) {
                Text(
                    text = prenda.marca,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
private fun SeccionEstadisticas(prenda: PrendaEntity) {
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
            Text(
                text = stringResource(R.string.uses_total_count, 0),
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
                imageVector = if (prenda.estampada) Icons.Default.Check else Icons.Default.Close,
                contentDescription = if (prenda.estampada) stringResource(R.string.cd_is_printed)
                else stringResource(R.string.cd_not_printed),
                tint = if (prenda.estampada) {
                    if (isDark) Color(0xFF5E9C94) else Color(0xFF2D4B55)
                } else {
                    MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
                }
            )
        }
    }
}

@Composable
private fun SeccionAtributosEstaticos(prenda: PrendaEntity) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.hint_category),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(text = prenda.categoria, style = MaterialTheme.typography.bodyMedium)
        }
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.hint_season),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(text = prenda.temporada, style = MaterialTheme.typography.bodyMedium)
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

// FUNCIONES AUXILIARES PARA PREVIEWS
//private fun provideDummyDetalleViewModel(): DetallePrendaViewModel {
//    val mockDao = object : PrendaDao {
//        override suspend fun insertarPrenda(prenda: PrendaEntity) = 0L
//        override suspend fun actualizarPrenda(prenda: PrendaEntity) = 0
//        override suspend fun eliminarPrenda(prenda: PrendaEntity) = 0
//        override fun obtenerTodasLasPrendas() = flowOf(emptyList<PrendaEntity>())
//        override fun obtenerPrendasPorUsuario(idUsuario: Int) = flowOf(emptyList<PrendaEntity>())
//
//        override suspend fun obtenerPrendaPorId(id: Int) = PrendaEntity(
//            id = 1,
//            idUsuario = 1,
//            nombre = "Camisa Vintage",
//            marca = "Levis",
//            imagen = null,
//            categoria = "Top",
//            color = "Azul",
//            estampada = false,
//            talla = "M",
//            temporada = "Primavera",
//            formalidad = "Casual",
//            tags = listOf("Vintage", "Favorito", "Uso Diario")
//        )
//    }
//    val repository = PrendaRepository(mockDao)
//    return DetallePrendaViewModel(repository)
//}
//
//// PREVIEWS
//@Preview(name = "1. Detalle (Claro)", showBackground = true, showSystemUi = true)
//@Composable
//private fun PreviewDetalleClaro() {
//    ClosetVirtualTheme(darkTheme = false) {
//        DetallePrendaScreen(
//            prendaId = 1,
//            viewModel = provideDummyDetalleViewModel()
//        )
//    }
//}
//
//@Preview(
//    name = "2. Detalle (Oscuro)",
//    showBackground = true,
//    showSystemUi = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES
//)
//@Composable
//private fun PreviewDetalleOscuro() {
//    ClosetVirtualTheme(darkTheme = true) {
//        DetallePrendaScreen(
//            prendaId = 1,
//            viewModel = provideDummyDetalleViewModel()
//        )
//    }
//}