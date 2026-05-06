package ramirez.ruben.closetvirtual.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ramirez.ruben.closetvirtual.R
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme
import ramirez.ruben.closetvirtual.utils.OpcionColor
import ramirez.ruben.closetvirtual.utils.PrendaConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionPrendaScreen(
    isEditMode: Boolean = false,
    onNavigateBack: () -> Unit = {}
) {
    var nombre by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var esEstampada by remember { mutableStateOf(false) }

    val tagVintage = stringResource(R.string.label_tag_vintage)
    val tagFavorito = stringResource(R.string.label_tag_favorite)
    var tagsList by remember { mutableStateOf(listOf(tagVintage, tagFavorito)) }

    var categoria by remember { mutableStateOf("") }
    var colorPrenda by remember { mutableStateOf("") }
    var temporada by remember { mutableStateOf("") }
    var talla by remember { mutableStateOf("") }
    var formalidad by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isEditMode) stringResource(R.string.title_edit_prenda)
                else stringResource(R.string.title_register_prenda),
                style = MaterialTheme.typography.headlineLarge, // Montserrat 32sp Bold desde Type.kt
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            SeccionFotoYTextos(
                nombre = nombre, onNombreChange = { nombre = it },
                marca = marca, onMarcaChange = { marca = it },
                imageUri = imageUri, onImageSelected = { imageUri = it },
                isEditMode = isEditMode
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

            if (isEditMode) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { /* Guardar */ },
                        modifier = Modifier.weight(1f).height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.btn_save),
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White
                        )
                    }
                    Button(
                        onClick = { /* Eliminar */ },
                        modifier = Modifier.weight(1f).height(50.dp),
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
            } else {
                Button(
                    onClick = { /* Registrar */ },
                    modifier = Modifier.fillMaxWidth(0.6f).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.btn_register),
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
private fun SeccionFotoYTextos(
    nombre: String, onNombreChange: (String) -> Unit,
    marca: String, onMarcaChange: (String) -> Unit,
    imageUri: Uri?, onImageSelected: (Uri?) -> Unit,
    isEditMode: Boolean
) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> onImageSelected(uri) }
    )

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp))
                .clickable {
                    photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = stringResource(R.string.cd_prenda_photo),
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = if (isEditMode) stringResource(R.string.cd_edit_photo)
                    else stringResource(R.string.cd_add_photo),
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = nombre, onValueChange = onNombreChange,
                placeholder = { Text(stringResource(R.string.hint_name)) },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp), singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge
            )
            OutlinedTextField(
                value = marca, onValueChange = onMarcaChange,
                placeholder = { Text(stringResource(R.string.hint_brand)) },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp), singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.label_printed),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(12.dp))
                Checkbox(
                    checked = esEstampada,
                    onCheckedChange = onEstampadaChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = if (isDark) Color.White else Color(0xFF6750A4),
                        checkmarkColor = if (isDark) Color(0xFF5E9C94) else Color.White
                    )
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                InteractiveDropdown(
                    opciones = categorias,
                    seleccionActual = categoriaActual.ifEmpty { stringResource(R.string.hint_category) },
                    onSeleccionChange = onCategoriaChange
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                ColorPaletteDropdown(
                    opciones = PrendaConstants.coloresPrenda,
                    seleccionActual = colorActual.ifEmpty { stringResource(R.string.hint_color) },
                    onSeleccionChange = onColorChange
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                InteractiveDropdown(
                    opciones = temporadas,
                    seleccionActual = temporadaActual.ifEmpty { stringResource(R.string.hint_season) },
                    onSeleccionChange = onTemporadaChange
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                InteractiveDropdown(
                    opciones = tallas,
                    seleccionActual = tallaActual.ifEmpty { stringResource(R.string.hint_size) },
                    onSeleccionChange = onTallaChange,
                    isEditable = true
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                InteractiveDropdown(
                    opciones = formalidades,
                    seleccionActual = formalidadActual.ifEmpty { stringResource(R.string.hint_formality) },
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
    val filaSuperior = tagsList.filterIndexed { index, _ -> index % 2 == 0 }
    val filaInferior = tagsList.filterIndexed { index, _ -> index % 2 != 0 }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = tagInput,
                onValueChange = { tagInput = it },
                placeholder = { Text(stringResource(R.string.hint_add_tag)) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium
            )
            IconButton(
                onClick = {
                    if (tagInput.isNotBlank() && !tagsList.contains(tagInput.trim())) {
                        onAddTag(tagInput.trim()); tagInput = ""
                    }
                },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp))
                    .size(56.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(R.string.cd_add_tag),
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(filaSuperior, filaInferior).forEach { fila ->
                    if (fila.isNotEmpty()) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            fila.forEach { tag ->
                                Surface(color = MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp)) {
                                    Row(
                                        modifier = Modifier.clickable { onRemoveTag(tag) }.padding(horizontal = 12.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(tag, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onTertiary)
                                        Icon(
                                            Icons.Filled.Close,
                                            contentDescription = stringResource(R.string.cd_remove_tag),
                                            modifier = Modifier.size(16.dp).padding(start = 4.dp),
                                            tint = MaterialTheme.colorScheme.onTertiary
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
            textStyle = MaterialTheme.typography.bodyMedium
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
            textStyle = MaterialTheme.typography.bodyMedium
        )
        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = opcion,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = { onSeleccionChange(opcion); expandido = false }
                )
            }
        }
    }
}

// PREVIEWS
@Preview(name = "1. Registrar (Claro)", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewRegistrarClaro() {
    ClosetVirtualTheme(darkTheme = false) {
        GestionPrendaScreen(isEditMode = false)
    }
}

@Preview(name = "2. Registrar (Oscuro)", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewRegistrarOscuro() {
    ClosetVirtualTheme(darkTheme = true) {
        GestionPrendaScreen(isEditMode = false)
    }
}

@Preview(name = "3. Editar (Claro)", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewEditarClaro() {
    ClosetVirtualTheme(darkTheme = false) {
        GestionPrendaScreen(isEditMode = true)
    }
}

@Preview(name = "4. Editar (Oscuro)", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewEditarOscuro() {
    ClosetVirtualTheme(darkTheme = true) {
        GestionPrendaScreen(isEditMode = true)
    }
}