package ramirez.ruben.closetvirtual.screens

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramirez.ruben.closetvirtual.R
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    onNavigateBack: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    var name by remember { mutableStateOf("Rubén Ramírez") }
    val email by remember { mutableStateOf("ruben@ejemplo.com") }
    var dateOfBirth by remember { mutableStateOf("15/08/2000") }

    var expandedGender by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf("Masculino") }
    val genderOptions = listOf("Mujer", "Hombre", "Personalizado")

    var isBiometricsEnabled by remember { mutableStateOf(true) }
    var isDarkThemeEnabled by remember { mutableStateOf(false) }

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
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 25.dp)
                    ) {
                        CustomThemeSwitch(
                            checked = isDarkThemeEnabled,
                            onCheckedChange = { isDarkThemeEnabled = it }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },

        //
        //MENU BOTTOM BAR
        //

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Perfil",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF5C6B80))
                        .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Avatar",
                        modifier = Modifier.size(80.dp),
                        tint = Color(0xFFE2E6E9)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.BottomEnd)
                        .background(MaterialTheme.colorScheme.background, CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
                        .clickable { /* Lógica para cambiar foto */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar foto",
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre", color = Color.Gray) },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.mipmap.user_icon),
                        contentDescription = "Icono de usuario",
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Editar nombre",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = email,
                onValueChange = {},
                readOnly = true,
                label = { Text("Correo electrónico", color = Color.Gray) },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.mipmap.email_icon),
                        contentDescription = "Icono de email",
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Gray,
                    unfocusedTextColor = Color.Gray,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = dateOfBirth,
                onValueChange = { dateOfBirth = it },
                label = { Text("DD/MM/AAA", color = Color.Gray) },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.mipmap.calendar_icon),
                        contentDescription = "Icono de calendario",
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Editar fecha",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            ExposedDropdownMenuBox(
                expanded = expandedGender,
                onExpandedChange = { expandedGender = !expandedGender }
            ) {
                TextField(
                    value = selectedGender,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Género", color = Color.Gray) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGender) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )

                ExposedDropdownMenu(
                    expanded = expandedGender,
                    onDismissRequest = { expandedGender = false }
                ) {
                    genderOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedGender = selectionOption
                                expandedGender = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Activar Biometría",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.width(12.dp))

                Checkbox(
                    checked = isBiometricsEnabled,
                    onCheckedChange = { isBiometricsEnabled = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF6750A4),
                        checkmarkColor = Color.White,
                        uncheckedColor = Color.Gray
                    )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { /* Guardar cambios perfil */ },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF26657A)) // Teal oscuro
            ) {
                Text(
                    "Guardar cambios",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = onLogoutClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFFD32F2F)
                )
            ) {
                Text(
                    text = "Cerrar sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textDecoration = TextDecoration.Underline
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun CustomThemeSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val trackColor by animateColorAsState(
        targetValue = if (checked) Color(0xFF26657A) else Color(0xFF8CC6C6),
        label = "trackColor"
    )
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) 32.dp else 0.dp,
        label = "thumbOffset"
    )

    Box(
        modifier = Modifier
            .width(64.dp)
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(trackColor)
            .clickable { onCheckedChange(!checked) }
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.sun_svg),
                contentDescription = null,
                modifier = Modifier
                    .size(14.dp)
                    .alpha(if (checked) 0.5f else 0f),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Image(
                painter = painterResource(id = R.drawable.moon_svg),
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .alpha(if (!checked) 0.5f else 0f),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }

        Box(
            modifier = Modifier
                .size(28.dp)
                .offset(x = thumbOffset)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(
                    id = if (checked) R.drawable.moon_svg else R.drawable.sun_svg
                ),
                contentDescription = if (checked) "Tema Oscuro" else "Tema Claro",
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

@Preview(name = "1. Perfil (Claro)", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewPerfilClaro() {
    ClosetVirtualTheme(darkTheme = false) {
        val isDarkThemeEnabled = remember {
            mutableStateOf(false)
        }
        PerfilScreen()
    }
}

@Preview(
    name = "2. Perfil (Oscuro)",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewPerfilOscuro() {
    ClosetVirtualTheme(darkTheme = true) {
        PerfilScreen()
    }
}