package ramirez.ruben.closetvirtual.screens

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ramirez.ruben.closetvirtual.data.mocks.Prenda
// TODO (Compañero): Comenté esta importación porque el repositorio cambió su estructura para usar Room.
// import ramirez.ruben.closetvirtual.data.database.repository.PrendaRepository
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AgregarOutfitScreen() {
    var nombre by remember { mutableStateOf("") }
    var isEstampada by remember { mutableStateOf(false) }
    var tagText by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    val tags = remember { mutableStateListOf("Casual", "Verano", "Favorito") }

    // TODO (Compañero): PrendaRepository ya no es un objeto estático (Singleton).
    // Ahora devuelve un Flow<List<PrendaEntity>> desde SQLite.
    // Necesitas crear un ViewModel para esta pantalla (ej. OutfitViewModel),
    // inyectarle el PrendaRepository y recolectar el Flow usando collectAsState().
    // val todasLasPrendas = PrendaRepository.todasLasPrendas

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atras")
            }
            Text(
                text = "Nuevo Outfit",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Button(
                onClick = { },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Guardar", fontWeight = FontWeight.Bold)
            }
        }

        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // detalles del outfit
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Detalles del Outfit", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre del outfit") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        leadingIcon = { Icon(Icons.AutoMirrored.Filled.Label, contentDescription = null) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
                            Checkbox(checked = isEstampada, onCheckedChange = { isEstampada = it })
                        }
                        Text("¿Es estampada?", modifier = Modifier.padding(start = 12.dp), style = MaterialTheme.typography.bodyMedium)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        DropdownField("Color", Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(12.dp))
                        DropdownField("Categoria", Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        DropdownField("Talla", Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(12.dp))
                        DropdownField("Temporada", Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        DropdownField("Estilo", Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // tags
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Etiquetas", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = tagText,
                            onValueChange = { tagText = it },
                            label = { Text("Nuevo tag") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        FilledIconButton(
                            onClick = { if (tagText.isNotBlank()) { tags.add(tagText); tagText = "" } },
                            modifier = Modifier.size(52.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Agregar")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        tags.forEach { tag ->
                            SuggestionChip(
                                onClick = { tags.remove(tag) },
                                label = { Text(tag) },
                                icon = { Icon(Icons.Default.Close, modifier = Modifier.size(16.dp), contentDescription = null) },
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // seleccion prendas
            Text(text = "Seleccionar Prendas", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(text = "Elige la ropa para este outfit", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar por nombre") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            /* TODO (Compañero): Comenté el bloque que renderiza las cartas porque depende
               de la lista 'todasLasPrendas' que ya no es estática.
               Además, tu función 'PrendaSelectionCard' debe actualizarse para recibir un 'PrendaEntity'
               en lugar de la clase antigua 'Prenda'.

            // Lista completa de prendas
            todasLasPrendas.chunked(2).forEach { par ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    PrendaSelectionCard(par[0], isSelected = false, onToggle = {}, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(12.dp))
                    if (par.size > 1) {
                        PrendaSelectionCard(par[1], isSelected = false, onToggle = {}, modifier = Modifier.weight(1f))
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            */

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(label: String, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(),
            shape = RoundedCornerShape(12.dp),
            textStyle = MaterialTheme.typography.bodySmall
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOf("Opción A", "Opción B", "Opción C").forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption = option
                        expanded = false
                    }
                )
            }
        }
    }
}

// TODO (Compañero): Cambiar el parámetro de tipo 'Prenda' a 'PrendaEntity'
@Composable
fun PrendaSelectionCard(
    prenda: Prenda,
    isSelected: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onToggle() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        ),
        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                if (prenda.imagenUri.isNotEmpty()) {
                    AsyncImage(
                        model = prenda.imagenUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        Icons.Default.Checkroom,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.size(48.dp)
                    )
                }

                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                    )
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .background(Color.White, CircleShape)
                    )
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    prenda.nombre,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )
                Text(
                    prenda.marca ?: "Sin marca",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun AgregarOutfitScreenPreview() {
    ClosetVirtualTheme {
        AgregarOutfitScreen()
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun AgregarOutfitScreenDarkPreview() {
    ClosetVirtualTheme {
        AgregarOutfitScreen()
    }
}