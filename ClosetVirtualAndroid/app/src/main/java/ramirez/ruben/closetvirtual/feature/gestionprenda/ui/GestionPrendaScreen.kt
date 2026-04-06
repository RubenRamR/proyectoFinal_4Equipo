package ramirez.ruben.closetvirtual.feature.gestionprenda.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme

data class OpcionColor(val nombre: String, val valor: Color)

val coloresPrenda = listOf(
    OpcionColor("Blanco", Color(0xFFFFFFFF)),
    OpcionColor("Negro", Color(0xFF000000)),
    OpcionColor("Gris", Color(0xFF808080)),
    OpcionColor("Gris Claro", Color(0xFFD3D3D3)),
    OpcionColor("Azul Marino", Color(0xFF000080)),
    OpcionColor("Azul", Color(0xFF0000FF)),
    OpcionColor("Azul Claro", Color(0xFFADD8E6)),
    OpcionColor("Turquesa", Color(0xFF40E0D0)),
    OpcionColor("Verde Oscuro", Color(0xFF006400)),
    OpcionColor("Verde", Color(0xFF228B22)),
    OpcionColor("Verde Lima", Color(0xFF32CD32)),
    OpcionColor("Amarillo", Color(0xFFFFD700)),
    OpcionColor("Naranja", Color(0xFFFF8C00)),
    OpcionColor("Rojo", Color(0xFFFF0000)),
    OpcionColor("Burdeos", Color(0xFF800000)),
    OpcionColor("Rosa", Color(0xFFFFC0CB)),
    OpcionColor("Fucsia", Color(0xFFFF00FF)),
    OpcionColor("Morado", Color(0xFF800080)),
    OpcionColor("Lila", Color(0xFFE6E6FA)),
    OpcionColor("Café", Color(0xFF8B4513)),
    OpcionColor("Marrón", Color(0xFFA52A2A)),
    OpcionColor("Beige", Color(0xFFF5F5DC)),
    OpcionColor("Crema", Color(0xFFFFFDD0)),
    OpcionColor("Caqui", Color(0xFFF0E68C))
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionPrendaScreen(onNavigateBack: () -> Unit = {}) {
    var nombre by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var esEstampada by remember { mutableStateOf(false) }
    var tagsList by remember { mutableStateOf(listOf("Vintage", "Favorito")) }

    var categoria by remember { mutableStateOf("Categoría") }
    var colorPrenda by remember { mutableStateOf("Color") }
    var temporada by remember { mutableStateOf("Temporada") }
    var talla by remember { mutableStateOf("Talla") }
    var formalidad by remember { mutableStateOf("Formalidad") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registrar prenda",
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            SeccionFotoYTextos(
                nombre = nombre, onNombreChange = { nombre = it },
                marca = marca, onMarcaChange = { marca = it },
                imageUri = imageUri, onImageSelected = { imageUri = it }
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            SeccionAtributos(
                esEstampada, { esEstampada = it },
                categoria, { categoria = it },
                colorPrenda, { colorPrenda = it },
                temporada, { temporada = it },
                talla, { talla = it },
                formalidad, { formalidad = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            SeccionTags(
                tagsList,
                onAddTag = { nuevoTag -> tagsList = tagsList + nuevoTag },
                onRemoveTag = { tagABorrar -> tagsList = tagsList - tagABorrar }
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            Button(
                onClick = { /* Registrar click*/ },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Registrar prenda",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SeccionFotoYTextos(
    nombre: String, onNombreChange: (String) -> Unit,
    marca: String, onMarcaChange: (String) -> Unit,
    imageUri: Uri?, onImageSelected: (Uri?) -> Unit
) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> onImageSelected(uri) }
    )

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Foto de la prenda",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    Icons.Outlined.AddCircle,
                    contentDescription = "Añadir Foto",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = nombre,
                onValueChange = onNombreChange,
                placeholder = { Text("Nombre de la prenda") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )
            OutlinedTextField(
                value = marca,
                onValueChange = onMarcaChange,
                placeholder = { Text("Marca") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}

@Composable
private fun SeccionAtributos(
    esEstampada: Boolean, onEstampadaChange: (Boolean) -> Unit,
    categoriaActual: String, onCategoriaChange: (String) -> Unit,
    colorActual: String, onColorChange: (String) -> Unit,
    temporadaActual: String, onTemporadaChange: (String) -> Unit,
    tallaActual: String, onTallaChange: (String) -> Unit,
    formalidadActual: String, onFormalidadChange: (String) -> Unit
) {
    val categorias = listOf("Top", "Bottom", "Outerwear", "Zapatos", "Accesorios")
    val temporadas = listOf("Primavera", "Verano", "Otoño", "Invierno", "Todas")
    val tallas = listOf("XS", "S", "M", "L", "XL", "Unitalla")
    val formalidades = listOf("Casual", "Formal", "Deportivo")

    val isDark = isSystemInDarkTheme()

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        // Fila 1: Estampada | Categoría
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Text("Estampada", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)

                Spacer(modifier = Modifier.width(12.dp))

                Checkbox(
                    checked = esEstampada,
                    onCheckedChange = onEstampadaChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = if (isDark) Color.White else MaterialTheme.colorScheme.primary,
                        checkmarkColor = if (isDark) Color(0xFF5E9C94) else MaterialTheme.colorScheme.onPrimary,

                        uncheckedColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                InteractiveDropdown(
                    opciones = categorias,
                    seleccionActual = categoriaActual,
                    onSeleccionChange = onCategoriaChange
                )
            }
        }

        // Fila 2: Color | Temporada
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                ColorPaletteDropdown(
                    opciones = coloresPrenda,
                    seleccionActual = colorActual,
                    onSeleccionChange = onColorChange
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                InteractiveDropdown(
                    opciones = temporadas,
                    seleccionActual = temporadaActual,
                    onSeleccionChange = onTemporadaChange
                )
            }
        }

        // Fila 3: Talla | Formalidad
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                InteractiveDropdown(
                    opciones = tallas,
                    seleccionActual = tallaActual,
                    onSeleccionChange = onTallaChange,
                    isEditable = true
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                InteractiveDropdown(
                    opciones = formalidades,
                    seleccionActual = formalidadActual,
                    onSeleccionChange = onFormalidadChange
                )
            }
        }
    }
}

@Composable
private fun SeccionTags(
    tagsList: List<String>,
    onAddTag: (String) -> Unit,
    onRemoveTag: (String) -> Unit
) {
    var tagInput by remember { mutableStateOf("") }
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = tagInput,
                onValueChange = { tagInput = it },
                placeholder = { Text("Añadir tag") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )
            IconButton(
                onClick = {
                    if (tagInput.isNotBlank() && !tagsList.contains(tagInput.trim())) {
                        onAddTag(tagInput.trim()); tagInput = ""
                    }
                },
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .size(56.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Añadir",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            tagsList.forEach { tag ->
                Surface(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .clickable { onRemoveTag(tag) }
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(tag, color = MaterialTheme.colorScheme.onTertiary)

                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Eliminar tag",
                            modifier = Modifier
                                .size(16.dp)
                                .padding(start = 4.dp),
                            tint = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPaletteDropdown(
    opciones: List<OpcionColor>,
    seleccionActual: String,
    onSeleccionChange: (String) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }
    val colorVisualAncla = opciones.find { it.nombre == seleccionActual }?.valor

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = it }
    ) {
        OutlinedTextField(
            value = seleccionActual,
            onValueChange = {},
            readOnly = true,
            leadingIcon = if (colorVisualAncla != null) {
                {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(colorVisualAncla, CircleShape)
                            .border(1.dp, Color.Gray, CircleShape)
                    )
                }
            } else null,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
        )

        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false },
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier.width(IntrinsicSize.Max)
        ) {
            val columnas = 4
            val filasColores = opciones.chunked(columnas)

            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                filasColores.forEach { coloresEnFila ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        coloresEnFila.forEach { opcionColor ->
                            val estaSeleccionado = opcionColor.nombre == seleccionActual

                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(opcionColor.valor, CircleShape)
                                    .border(1.dp, Color.Gray, CircleShape)
                                    .then(
                                        if (estaSeleccionado) Modifier.border(
                                            3.dp,
                                            MaterialTheme.colorScheme.primary,
                                            CircleShape
                                        )
                                        else Modifier
                                    )
                                    .clickable {
                                        onSeleccionChange(opcionColor.nombre)
                                        expandido = false
                                    }
                            )
                        }

                        repeat(columnas - coloresEnFila.size) {
                            Spacer(modifier = Modifier.size(40.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InteractiveDropdown(
    opciones: List<String>,
    seleccionActual: String,
    onSeleccionChange: (String) -> Unit,
    isEditable: Boolean = false
) {
    var expandido by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expandido, onExpandedChange = { expandido = it }) {
        OutlinedTextField(
            value = seleccionActual,
            onValueChange = { if (isEditable) onSeleccionChange(it) },
            readOnly = !isEditable,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(), shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
        )
        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion, color = MaterialTheme.colorScheme.onSurface) },
                    onClick = { onSeleccionChange(opcion); expandido = false }
                )
            }
        }
    }
}

@Preview(name = "Modo Claro", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewModoClaro() {
    ClosetVirtualTheme(darkTheme = false) {
        GestionPrendaScreen()
    }
}

@Preview(
    name = "Modo Oscuro",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewModoOscuro() {
    ClosetVirtualTheme(darkTheme = true) {
        GestionPrendaScreen()
    }
}