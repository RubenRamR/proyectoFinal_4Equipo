package ramirez.ruben.closetvirtual.screens

import android.content.res.Configuration
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ramirez.ruben.closetvirtual.R
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.draw.clip
import ramirez.ruben.closetvirtual.data.database.entity.PrendaEntity
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme
import ramirez.ruben.closetvirtual.ui.theme.Montserrat
import ramirez.ruben.closetvirtual.viewmodel.OutfitConPrendas
import ramirez.ruben.closetvirtual.viewmodel.OutfitsViewModel

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun OutfitsScreenPreview() {
    ClosetVirtualTheme {
        OutfitsScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun OutfitsScreenDarkPreview() {
    ClosetVirtualTheme {
        OutfitsScreen()
    }
}

@Composable
fun OutfitsScreen(
    onNavigateToRegistroDiario: () -> Unit = {},
    onNavigateToAgregarOutfit: () -> Unit = {},
    onNavigateToDetalleOutfit: (Int) -> Unit = {},
    viewModel: OutfitsViewModel = viewModel()
) {
    val outfitsConPrendas by viewModel.outfits.collectAsState()

    Scaffold(
        bottomBar = { },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FloatingActionButton(onClick = { onNavigateToRegistroDiario() }) {
                    Icon(
                        painter = painterResource(id = R.mipmap.streak_icon),
                        contentDescription = "Registro Diario",
                        tint = Color.Unspecified
                    )
                }

                AddFab(onClick = onNavigateToAgregarOutfit)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(12.dp).fillMaxSize()) {
            BarraDeBusquedaOutfits()
            FiltrosOutfits()

            Box(modifier = Modifier.weight(1f)) {
                OutfitsGrid(outfitsConPrendas, onNavigateToDetalleOutfit)
            }
        }
    }
}

@Composable
fun BarraDeBusquedaOutfits() {
    OutlinedTextField(value = "",
        onValueChange = {},
        placeholder = { Text("Búsqueda de outfits", fontFamily = Montserrat) },
        leadingIcon = { },
        trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        shape = RoundedCornerShape(24.dp))
}

@Composable
fun FiltrosOutfits() {
    val filters = listOf("Estilo", "Formalidad", "Temporada", "Mios")
    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(filters) { text ->
            AssistChip(onClick = {}, label = { Text(text, fontFamily = Montserrat) })
        }
    }
}

@Composable
fun OutfitsGrid(outfits: List<OutfitConPrendas>, onNavigateToDetalle: (Int) -> Unit = {}) {
    if (outfits.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No tienes outfits guardados", fontFamily = Montserrat)
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(outfits) { outfitConPrendas ->
                OutfitCard(outfitConPrendas, onNavigateToDetalle)
            }
        }
    }
}

@Composable
fun OutfitCard(outfitConPrendas: OutfitConPrendas, onNavigateToDetalle: (Int) -> Unit = {}) {
    val outfit = outfitConPrendas.outfit
    val prendas = outfitConPrendas.prendas

    Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Column {
            // Header
            Column(modifier = Modifier.padding(8.dp)) {
                Text(outfit.nombre, fontWeight = FontWeight.Bold, fontFamily = Montserrat, maxLines = 1)
                Text(outfit.estilo.ifEmpty { "Sin estilo" }, style = MaterialTheme.typography.bodySmall)
            }

            // Imagen del outfit (compuesta por sus prendas)
            OutfitImage(prendas)

            // Info temporada
            Column(modifier = Modifier.padding(8.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(outfit.temporada, fontSize = 12.sp, fontFamily = Montserrat)
                    }
                    // Icono de corazon
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // botón de detalle
                Button(
                    onClick = { onNavigateToDetalle(outfit.id) },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    Text(text = "Detalle", fontSize = 13.sp, fontFamily = Montserrat, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun OutfitImage(prendas: List<PrendaEntity>) {
    Box(
        modifier = Modifier.fillMaxWidth().height(140.dp).background(MaterialTheme.colorScheme.surfaceVariant), contentAlignment = Alignment.Center) {
        when (prendas.size) {
            0 -> {
                Icon(Icons.Default.Image, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            1 -> {
                PrendaItemImage(prendas[0], Modifier.fillMaxSize())
            }
            2 -> {
                Row(Modifier.fillMaxSize()) {
                    PrendaItemImage(prendas[0], Modifier.weight(1f).fillMaxHeight())
                    VerticalDivider(color = MaterialTheme.colorScheme.surface, thickness = 1.dp)
                    PrendaItemImage(prendas[1], Modifier.weight(1f).fillMaxHeight())
                }
            }
            3 -> {
                Column(Modifier.fillMaxSize()) {
                    Row(Modifier.weight(1f)) {
                        PrendaItemImage(prendas[0], Modifier.weight(1f).fillMaxHeight())
                        VerticalDivider(color = MaterialTheme.colorScheme.surface, thickness = 1.dp)
                        PrendaItemImage(prendas[1], Modifier.weight(1f).fillMaxHeight())
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.surface, thickness = 1.dp)
                    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        PrendaItemImage(prendas[2], Modifier.fillMaxWidth(0.5f).fillMaxHeight())
                    }
                }
            }
            4 -> {
                Column(Modifier.fillMaxSize()) {
                    Row(Modifier.weight(1f)) {
                        PrendaItemImage(prendas[0], Modifier.weight(1f).fillMaxHeight())
                        VerticalDivider(color = MaterialTheme.colorScheme.surface, thickness = 1.dp)
                        PrendaItemImage(prendas[1], Modifier.weight(1f).fillMaxHeight())
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.surface, thickness = 1.dp)
                    Row(Modifier.weight(1f)) {
                        PrendaItemImage(prendas[2], Modifier.weight(1f).fillMaxHeight())
                        VerticalDivider(color = MaterialTheme.colorScheme.surface, thickness = 1.dp)
                        PrendaItemImage(prendas[3], Modifier.weight(1f).fillMaxHeight())
                    }
                }
            }
            else -> { // 5 prendas
                Column(Modifier.fillMaxSize()) {
                    Row(Modifier.weight(1f)) {
                        PrendaItemImage(prendas[0], Modifier.weight(1f).fillMaxHeight())
                        VerticalDivider(color = MaterialTheme.colorScheme.surface, thickness = 1.dp)
                        PrendaItemImage(prendas[1], Modifier.weight(1f).fillMaxHeight())
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.surface, thickness = 1.dp)
                    Row(Modifier.weight(1f)) {
                        PrendaItemImage(prendas[2], Modifier.weight(1f).fillMaxHeight())
                        VerticalDivider(color = MaterialTheme.colorScheme.surface, thickness = 1.dp)
                        PrendaItemImage(prendas[3], Modifier.weight(1f).fillMaxHeight())
                        VerticalDivider(color = MaterialTheme.colorScheme.surface, thickness = 1.dp)
                        PrendaItemImage(prendas[4], Modifier.weight(1f).fillMaxHeight())
                    }
                }
            }
        }
    }
}

@Composable
fun PrendaItemImage(prenda: PrendaEntity, modifier: Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (prenda.imagen != null) {
            val bitmap = remember(prenda.imagen) {
                BitmapFactory.decodeByteArray(prenda.imagen, 0, prenda.imagen.size)
            }
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = prenda.nombre,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        } else {
            Icon(Icons.Default.Checkroom, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}