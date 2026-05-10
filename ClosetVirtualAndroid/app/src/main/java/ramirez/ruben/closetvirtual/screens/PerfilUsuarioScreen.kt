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
import ramirez.ruben.closetvirtual.viewmodel.UsuarioViewModel
import ramirez.ruben.closetvirtual.viewmodel.LoginViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.material.icons.Icons
import ramirez.ruben.closetvirtual.data.database.dao.UsuarioDao
import ramirez.ruben.closetvirtual.data.database.entity.UsuarioEntity
import ramirez.ruben.closetvirtual.data.database.repository.UsuarioRepository
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ramirez.ruben.closetvirtual.R
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    onNavigateBack: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    viewModel: UsuarioViewModel,
    loginViewModel: LoginViewModel? = null
) {

    val usuarioActual by viewModel.usuarioActual.collectAsState()

    var name by remember(usuarioActual) { mutableStateOf(usuarioActual?.nombre ?: "") }
    val email by remember(usuarioActual) { mutableStateOf(usuarioActual?.correo ?: "") }
    var dateOfBirth by remember(usuarioActual) { mutableStateOf(usuarioActual?.fechaNacimiento ?: "") }

    var expandedGender by remember { mutableStateOf(false) }
    val genderOptions = listOf(
        stringResource(R.string.gender_male),
        stringResource(R.string.gender_female),
        stringResource(R.string.gender_other)
    )

    var selectedGender by remember(usuarioActual) {
        mutableStateOf(usuarioActual?.genero ?: genderOptions[0])
    }


    // preferencias del usuario
    var isBiometricsEnabled by remember(usuarioActual) {
        mutableStateOf(usuarioActual?.isBiometricsEnabled ?: false)
    }

    // Observar el modo oscuro desde DataStore
    val isDarkThemeDataStore by loginViewModel?.isDarkThemeEnabled?.collectAsState(initial = false) ?: remember { mutableStateOf(false) }

    var isDarkThemeEnabled by remember(isDarkThemeDataStore) {
        mutableStateOf(isDarkThemeDataStore)
    }

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
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 25.dp)
                    ) {
                        CustomThemeSwitch(
                            checked = isDarkThemeEnabled,
                            onCheckedChange = { 
                                isDarkThemeEnabled = it
                                loginViewModel?.toggleDarkMode(it)
                            }
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
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.perfil_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Box(modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp)) {
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
                        contentDescription = stringResource(R.string.cd_profile_avatar),
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
                        .clickable { /* Cambio de foto */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(R.string.cd_edit_profile_photo),
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Nombre
            TextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Text(
                        stringResource(R.string.label_name),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.mipmap.user_icon),
                        contentDescription = stringResource(R.string.cd_user_icon),
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = stringResource(R.string.cd_edit_name),
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Email (Solo lectura)
            TextField(
                value = email,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(
                        stringResource(R.string.label_email),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.mipmap.email_icon),
                        contentDescription = stringResource(R.string.cd_email_icon),
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Gray,
                    unfocusedTextColor = Color.Gray,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Fecha de Nacimiento
            TextField(
                value = dateOfBirth,
                onValueChange = { dateOfBirth = it },
                label = {
                    Text(
                        stringResource(R.string.label_birth_date),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.mipmap.calendar_icon),
                        contentDescription = stringResource(R.string.cd_calendar_icon),
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = stringResource(R.string.cd_edit_date),
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = MaterialTheme.typography.bodyLarge,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Género
            ExposedDropdownMenuBox(
                expanded = expandedGender,
                onExpandedChange = { expandedGender = !expandedGender }
            ) {
                TextField(
                    value = selectedGender,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text(
                            stringResource(R.string.label_gender),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGender) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyLarge,
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
                            text = {
                                Text(
                                    selectionOption,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            onClick = {
                                selectedGender = selectionOption
                                expandedGender = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Biometría
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(R.string.label_biometrics),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(12.dp))
                Checkbox(
                    checked = isBiometricsEnabled,
                    onCheckedChange = { 
                        isBiometricsEnabled = it
                        loginViewModel?.toggleBiometrics(it)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF6750A4),
                        checkmarkColor = Color.White,
                        uncheckedColor = Color.Gray
                    )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Botón Guardar
            Button(
                onClick = {
                    usuarioActual?.let { usuarioViejo ->
                        // copia del usuario con los datos que el usuario editó
                        val usuarioActualizado = usuarioViejo.copy(
                            nombre = name,
                            fechaNacimiento = dateOfBirth,
                            genero = selectedGender,
                            isBiometricsEnabled = isBiometricsEnabled,
                            isDarkThemeEnabled = isDarkThemeEnabled
                        )
                        // Lo mandamos al ViewModel para que Room lo guarde
                        viewModel.actualizarPerfil(usuarioActualizado)
                        loginViewModel?.toggleBiometrics(isBiometricsEnabled)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF26657A))
            ) {
                Text(
                    text = stringResource(R.string.btn_save_changes),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Logout
            TextButton(
                onClick = {
                    loginViewModel?.logout()
                    onLogoutClick()
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFD32F2F))
            ) {
                Text(
                    text = stringResource(R.string.btn_logout),
                    style = MaterialTheme.typography.labelLarge.copy(
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Medium
                    )
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
                contentDescription = if (checked) stringResource(R.string.cd_theme_dark)
                else stringResource(R.string.cd_theme_light),
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

// hacer viewmodel falso para mocks pa las preview
private fun provideDummyUsuarioViewModel(): UsuarioViewModel {
    val mockDao = object : UsuarioDao {
        override suspend fun insertarUsuario(usuario: UsuarioEntity) = 0L
        override suspend fun actualizarUsuario(usuario: UsuarioEntity) = 0
        override suspend fun login(correo: String, contrasena: String): UsuarioEntity? = null
        override suspend fun obtenerUsuarioPorCorreo(correo: String): UsuarioEntity? = null
        override suspend fun obtenerUsuarioPorId(id: Int): UsuarioEntity? = null
    }
    val repository = UsuarioRepository(mockDao)
    return UsuarioViewModel(repository)
}

@Preview(name = "1. Perfil (Claro)", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewPerfilClaro() {
    ClosetVirtualTheme(darkTheme = false) {
        PerfilScreen(
            onNavigateBack = {},
            onLogoutClick = {},
            viewModel = provideDummyUsuarioViewModel(), // mock
            loginViewModel = null
        )
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
        PerfilScreen(
            onNavigateBack = {},
            onLogoutClick = {},
            viewModel = provideDummyUsuarioViewModel(), // mock
            loginViewModel = null
        )
    }
}