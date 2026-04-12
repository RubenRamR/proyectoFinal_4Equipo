package ramirez.ruben.closetvirtual.feature.pantallaprincipal.ui

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ramirez.ruben.closetvirtual.data.Prenda

@Preview(showBackground = true)
@Composable
fun PantallaDetalleOutfit() {
    val outfit = OutfitRepository.todosLosOutfits.firstOrNull()
    val prendas = outfit?.prendas ?: emptyList()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                }
                Text(
                    text = outfit?.nombre ?: "Detalle de Outfit",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

            Box(modifier = Modifier.weight(1f)) {
                PrendasGridOutfit(prendas)
            }
            
        }
        

    }
}

@Composable
fun PrendasGridOutfit(prendas: List<Prenda>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(prendas) { item ->
            PrendaOutfitCard(item)
        }
    }
}

@Composable
fun PrendaOutfitCard(prenda: Prenda) {
    Card(
        shape = RoundedCornerShape(16.dp), 
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            // Header
            Column(modifier = Modifier.padding(8.dp)) {
                Text(prenda.nombre, fontWeight = FontWeight.Bold)
                Text(prenda.marca ?: "Sin marca", style = MaterialTheme.typography.bodySmall)
            }

            // Imagen de la prenda
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = prenda.imagenUri,
                    contentDescription = prenda.nombre,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // info temporada y categoria
            Column(modifier = Modifier.padding(8.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(prenda.temporada)
                        Text(prenda.categoria, style = MaterialTheme.typography.bodySmall)
                    }
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {}, modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(horizontal = 4.dp)) {
                    Text(text = "Detalle", fontSize = 11.sp, maxLines = 1)
                }
            }
        }
    }
}


