package ramirez.ruben.closetvirtual.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import ramirez.ruben.closetvirtual.data.Prenda
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme
import androidx.compose.material3.FabPosition
import androidx.compose.ui.res.painterResource
import ramirez.ruben.closetvirtual.R
import ramirez.ruben.closetvirtual.data.PrendaRepository

@Preview(showBackground = true)
@Composable
fun ClosetScreenPreview() {
    ClosetVirtualTheme {
        ClosetScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ClosetScreenDarkPreview() {
    ClosetVirtualTheme {
        ClosetScreen()
    }
}

@Composable
fun ClosetScreen(
    onNavigateToRegistroDiario: () -> Unit = {}
) {
    val prendas = PrendaRepository.todasLasPrendas

    Scaffold(
        bottomBar = { },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FloatingActionButton(onClick = { onNavigateToRegistroDiario() }) {
                    Icon(
                        painter = painterResource(id = R.mipmap.streak_icon),
                        contentDescription = "Registro Diario",
                        tint = Color.Unspecified
                    )
                }

                AddFab()
            }
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            BarraDeBusqueda()
            Filtros()
            Box(modifier = Modifier.weight(1f)) {
                PrendasGrid(prendas)
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun BarraDeBusqueda() {
    OutlinedTextField(value = "", onValueChange = {}, placeholder = { Text("Busqueda de prendas") }, leadingIcon = {
            Icon(Icons.Default.Menu, contentDescription = null)
        },
        trailingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        modifier = Modifier.fillMaxWidth().padding(12.dp), shape = RoundedCornerShape(24.dp))
}

//@Preview(showBackground = true)
@Composable
fun Filtros() {
    val filters = listOf("Marca", "Categoria", "Temporada", "Usos", "Label")
    LazyRow(contentPadding = PaddingValues(horizontal = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            Icon(Icons.Default.Tune, contentDescription = null)
        }
        items(filters) { text ->
            AssistChip(onClick = {}, label = { Text(text) })
        }
    }
}

@Composable
fun PrendasGrid(prendas: List<Prenda>) {
    // grid para ejecutar le card d cada prenda
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(prendas) { item ->
            PrendasCard(item)
        }
    }
}

@Composable
fun PrendasCard(prenda: Prenda) {
    // Card usa por defecto MaterialTheme.colorScheme.surface
    Card(
        shape = RoundedCornerShape(16.dp), 
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            // Header
            Column(modifier = Modifier.padding(8.dp)) {
                Text(prenda.nombre, fontWeight = FontWeight.Bold)
                Text(prenda.marca ?: "nada", style = MaterialTheme.typography.bodySmall)
            }

            // Imagen de la prenda
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = prenda.imagenUri,
                    contentDescription = prenda.nombre,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    error = null // Aquí podrías poner un icono de error si gustas
                )
            }

            // Info temporada y categoria
            Column(modifier = Modifier.padding(8.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(prenda.temporada)
                        Text(prenda.categoria, style = MaterialTheme.typography.bodySmall)
                    }
                    //Icono de corazon
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // botones
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(onClick = {}, modifier = Modifier.weight(1f), contentPadding = PaddingValues(horizontal = 4.dp)) {
                        Text("Hoy la usé", fontSize = 11.sp, maxLines = 1)
                    }
                    Button(onClick = {}, modifier = Modifier.weight(1f), contentPadding = PaddingValues(horizontal = 4.dp)) {
                        Text(text = "Detalle", fontSize = 11.sp, maxLines = 1)
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)

@Composable
fun AddFab() { //botoncito de + para agregar prendas
    FloatingActionButton(onClick = {}) {
        Icon(Icons.Default.Add, contentDescription = null)
    }
}


