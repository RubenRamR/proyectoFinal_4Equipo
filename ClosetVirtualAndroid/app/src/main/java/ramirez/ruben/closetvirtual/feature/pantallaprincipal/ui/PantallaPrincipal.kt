package ramirez.ruben.closetvirtual.feature.pantallaprincipal.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ClosetScreen() {
    Scaffold(
        bottomBar = { BottomNavBar() },
        floatingActionButton = { AddFab() }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            SearchBar()
            FilterChips()
            ClothesGrid()
        }
    }
}

@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Hinted search text") },
        leadingIcon = {
            Icon(Icons.Default.Menu, contentDescription = null)
        },
        trailingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(24.dp)
    )
}

@Composable
fun FilterChips() {
    val filters = listOf("Marca", "Categoria", "Temporada", "Usos", "Label")

    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Icon(Icons.Default.Tune, contentDescription = null)
        }
        items(filters) { text ->
            AssistChip(
                onClick = {},
                label = { Text(text) }
            )
        }
    }
}

data class ClothingItem(
    val title: String,
    val brand: String,
    val season: String,
    val type: String
)

@Composable
fun ClothesGrid() {
    val items = List(6) {
        ClothingItem("Venum top", "Nike", "Otoño", "Tops")
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            ClothingCard(item)
        }
    }
}

@Composable
fun ClothingCard(item: ClothingItem) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            // Header
            Column(modifier = Modifier.padding(8.dp)) {
                Text(item.title, fontWeight = FontWeight.Bold)
                Text(item.brand, style = MaterialTheme.typography.bodySmall)
            }

            // Imagen placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Image, contentDescription = null)
            }

            // Info
            Column(modifier = Modifier.padding(8.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(item.season)
                        Text(item.type, style = MaterialTheme.typography.bodySmall)
                    }
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = {}) {
                        Text("Hoy la usé")
                    }
                    Button(onClick = {}) {
                        Text("Detalle")
                    }
                }
            }
        }
    }
}

@Composable
fun AddFab() {
    FloatingActionButton(onClick = {}) {
        Icon(Icons.Default.Add, contentDescription = null)
    }
}

@Composable
fun BottomNavBar() {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Default.Checkroom, null) }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.AutoAwesome, null) }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.CalendarMonth, null) }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.Person, null) }
        )
    }
}
