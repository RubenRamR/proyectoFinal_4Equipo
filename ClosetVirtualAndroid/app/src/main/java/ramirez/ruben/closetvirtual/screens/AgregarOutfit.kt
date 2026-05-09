package ramirez.ruben.closetvirtual.screens

import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.asImageBitmap
import android.graphics.BitmapFactory
import ramirez.ruben.closetvirtual.R
import ramirez.ruben.closetvirtual.data.database.entity.PrendaEntity
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme
import ramirez.ruben.closetvirtual.ui.theme.Montserrat
import ramirez.ruben.closetvirtual.viewmodel.AgregarOutfitViewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AgregarOutfitScreen(
    viewModel: AgregarOutfitViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val prendasDisponibles by viewModel.prendasDisponibles.collectAsState()
    val prendasSeleccionadas by viewModel.prendasSeleccionadas.collectAsState()
    val mensajeError by viewModel.mensajeError.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var estilo by remember { mutableStateOf("") }
    var temporada by remember { mutableStateOf("Primavera") }
    var tagText by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    val tags = remember { mutableStateListOf<String>() }

    val context = LocalContext.current

    LaunchedEffect(mensajeError) {
        mensajeError?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.limpiarError()
        }
    }

    val prendasFiltradas = if (searchQuery.isBlank()) {
        prendasDisponibles
    } else {
        prendasDisponibles.filter { it.nombre.contains(searchQuery, ignoreCase = true) }
    }

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
            IconButton(onClick = onNavigateBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Atras",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                text = "Nuevo Outfit",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = Montserrat
            )
            Button(
                onClick = {
                    viewModel.guardarOutfit(nombre, estilo, temporada, tags.toList()) {
                        Toast.makeText(context, "Outfit guardado con éxito", Toast.LENGTH_SHORT).show()
                        onNavigateBack()
                    }
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Guardar", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = Montserrat)
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
                    Text("Detalles del Outfit", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, fontFamily = Montserrat)
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre del outfit", fontFamily = Montserrat) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        leadingIcon = { Icon(Icons.AutoMirrored.Filled.Label, contentDescription = null) },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = estilo,
                            onValueChange = { estilo = it },
                            label = { Text("Estilo", fontFamily = Montserrat) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = MaterialTheme.typography.bodySmall.copy(fontFamily = Montserrat),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        DropdownField(
                            label = "Temporada",
                            options = listOf("Primavera", "Verano", "Otoño", "Invierno"),
                            selectedOption = temporada,
                            onSelectionChange = { temporada = it },
                            modifier = Modifier.weight(1f)
                        )
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
                    Text("Etiquetas", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, fontFamily = Montserrat)
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = tagText,
                            onValueChange = { tagText = it },
                            label = { Text("Nuevo tag", fontFamily = Montserrat) },
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
                            Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        tags.forEach { tag ->
                            SuggestionChip(
                                onClick = { tags.remove(tag) },
                                label = { Text(tag, fontFamily = Montserrat) },
                                icon = { Icon(Icons.Default.Close, modifier = Modifier.size(16.dp), contentDescription = null) },
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // seleccion prendas
            Text(text = "Seleccionar Prendas (${prendasSeleccionadas.size}/5)", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground, fontFamily = Montserrat)
            Text(text = "Elige la ropa para este outfit (Máximo 5)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontFamily = Montserrat)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar por nombre", fontFamily = Montserrat) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Lista de prendas disponibles para seleccionar
            prendasFiltradas.chunked(2).forEach { par ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    PrendaSelectionCard(
                        prenda = par[0],
                        isSelected = prendasSeleccionadas.contains(par[0].id),
                        onToggle = { viewModel.toggleSeleccionPrenda(par[0].id) },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    if (par.size > 1) {
                        PrendaSelectionCard(
                            prenda = par[1],
                            isSelected = prendasSeleccionadas.contains(par[1].id),
                            onToggle = { viewModel.toggleSeleccionPrenda(par[1].id) },
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (prendasFiltradas.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    Text("No se encontraron prendas", fontFamily = Montserrat, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onSelectionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label, maxLines = 1, fontFamily = Montserrat) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(),
            shape = RoundedCornerShape(12.dp),
            textStyle = MaterialTheme.typography.bodySmall.copy(fontFamily = Montserrat),
            singleLine = true
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, fontFamily = Montserrat) },
                    onClick = {
                        onSelectionChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun PrendaSelectionCard(
    prenda: PrendaEntity,
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
            Box(modifier = Modifier.fillMaxWidth().height(140.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                if (prenda.imagen != null) {
                    val bitmap = remember(prenda.imagen) {
                        BitmapFactory.decodeByteArray(prenda.imagen, 0, prenda.imagen.size)
                    }
                    if (bitmap != null) {
                        androidx.compose.foundation.Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                } else {
                    Icon(
                        Icons.Default.Checkroom,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.size(48.dp)
                    )
                }

                if (isSelected) {
                    Box(modifier = Modifier.fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                    )
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).background(Color.White, CircleShape)
                    )
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    prenda.nombre,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    fontFamily = Montserrat
                )
                Text(
                    prenda.marca ?: "Sin marca",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontFamily = Montserrat
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
