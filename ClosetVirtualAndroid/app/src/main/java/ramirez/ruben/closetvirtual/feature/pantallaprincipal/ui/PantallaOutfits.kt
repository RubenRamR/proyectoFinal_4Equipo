package ramirez.ruben.closetvirtual.feature.pantallaprincipal.ui

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import ramirez.ruben.closetvirtual.data.Prenda

@Preview(showBackground = true)
@Composable
fun OutfitsScreen() {
    val repository = remember { OutfitRepository() }
    val outfits = remember { repository.obtenerOutfits() }

    Scaffold(
        bottomBar = { BottomNavBar() },
        floatingActionButton = { AddFab() }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            BarraDeBusquedaOutfits()
            FiltrosOutfits()
            OutfitsGrid(outfits)
        }
    }
}

@Composable
fun BarraDeBusquedaOutfits() {
    OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text("Búsqueda de outfits") }, leadingIcon = { Icon(Icons.Default.Menu, contentDescription = null) }, trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) }, modifier = Modifier.fillMaxWidth().padding(12.dp), shape = RoundedCornerShape(24.dp))
}

@Composable
fun FiltrosOutfits() {
    val filters = listOf("Estilo", "Formalidad", "Temporada", "Color")
    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item { Icon(Icons.Default.Tune, contentDescription = null) }
        items(filters) { text ->
            AssistChip(onClick = {}, label = { Text(text) })
        }
    }
}

@Composable
fun OutfitsGrid(outfits: List<Outfit>) {
    // grid para ejecutar la card de cada outfit
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(outfits) { outfit ->
            OutfitCard(outfit)
        }
    }
}

@Composable
fun OutfitCard(outfit: Outfit) {
    // Card usa por defecto MaterialTheme.colorScheme.surface
    Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Column {
            // Header
            Column(modifier = Modifier.padding(8.dp)) {
                Text(outfit.nombre, fontWeight = FontWeight.Bold)
                Text(outfit.estilo.ifEmpty { "Sin estilo" }, style = MaterialTheme.typography.bodySmall)
            }

            // Imagen del outfit
            OutfitImage(outfit.prendas)

            // Info temporada y formalidad
            Column(modifier = Modifier.padding(8.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(outfit.temporada, fontSize = 12.sp)
                        Text(outfit.formalidad, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    }
                    // Icono de corazon
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // botón de detalle
                Button(onClick = {}, modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(vertical = 4.dp)) {
                    Text(text = "Detalle", fontSize = 13.sp)
                }
            }
        }
    }
}

//este metodo es para renderizar la imagen del outfit dependiendo de la cantidad de prnedas que tenga
@Composable
fun OutfitImage(prendas: List<Prenda>) {
    Box(
        modifier = Modifier.fillMaxWidth().height(140.dp).background(Color.LightGray), contentAlignment = Alignment.Center) {
        when (prendas.size) {
            0 -> {
                Icon(Icons.Default.Image, contentDescription = null, tint = Color.Gray)
            }
            1 -> {
                PrendaItemImage(prendas[0], Modifier.fillMaxSize())
            }
            2 -> {
                Row(Modifier.fillMaxSize()) {
                    PrendaItemImage(prendas[0], Modifier.weight(1f).fillMaxHeight())
                    VerticalDivider(color = Color.White, thickness = 1.dp)
                    PrendaItemImage(prendas[1], Modifier.weight(1f).fillMaxHeight())
                }
            }
            3 -> {
                Column(Modifier.fillMaxSize()) {
                    Row(Modifier.weight(1f)) {
                        PrendaItemImage(prendas[0], Modifier.weight(1f).fillMaxHeight())
                        VerticalDivider(color = Color.White, thickness = 1.dp)
                        PrendaItemImage(prendas[1], Modifier.weight(1f).fillMaxHeight())
                    }
                    HorizontalDivider(color = Color.White, thickness = 1.dp)
                    Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        PrendaItemImage(prendas[2], Modifier.fillMaxWidth(0.5f).fillMaxHeight())
                    }
                }
            }
            4 -> {
                Column(Modifier.fillMaxSize()) {
                    Row(Modifier.weight(1f)) {
                        PrendaItemImage(prendas[0], Modifier.weight(1f).fillMaxHeight())
                        VerticalDivider(color = Color.White, thickness = 1.dp)
                        PrendaItemImage(prendas[1], Modifier.weight(1f).fillMaxHeight())
                    }
                    HorizontalDivider(color = Color.White, thickness = 1.dp)
                    Row(Modifier.weight(1f)) {
                        PrendaItemImage(prendas[2], Modifier.weight(1f).fillMaxHeight())
                        VerticalDivider(color = Color.White, thickness = 1.dp)
                        PrendaItemImage(prendas[3], Modifier.weight(1f).fillMaxHeight())
                    }
                }
            }
            else -> { // 5 prendas o más
                Column(Modifier.fillMaxSize()) {
                    Row(Modifier.weight(1f)) {
                        PrendaItemImage(prendas[0], Modifier.weight(1f).fillMaxHeight())
                        VerticalDivider(color = Color.White, thickness = 1.dp)
                        PrendaItemImage(prendas[1], Modifier.weight(1f).fillMaxHeight())
                    }
                    HorizontalDivider(color = Color.White, thickness = 1.dp)
                    Row(Modifier.weight(1f)) {
                        PrendaItemImage(prendas[2], Modifier.weight(1f).fillMaxHeight())
                        VerticalDivider(color = Color.White, thickness = 1.dp)
                        PrendaItemImage(prendas[3], Modifier.weight(1f).fillMaxHeight())
                        VerticalDivider(color = Color.White, thickness = 1.dp)
                        PrendaItemImage(prendas[4], Modifier.weight(1f).fillMaxHeight())
                    }
                }
            }
        }
    }
}


@Composable
fun PrendaItemImage(prenda: Prenda, modifier: Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        AsyncImage(
            model = prenda.imagenUri,
            contentDescription = prenda.nombre,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
