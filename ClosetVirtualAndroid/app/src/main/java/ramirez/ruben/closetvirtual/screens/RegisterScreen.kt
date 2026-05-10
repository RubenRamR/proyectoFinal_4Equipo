package ramirez.ruben.closetvirtual.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramirez.ruben.closetvirtual.R
import androidx.compose.foundation.verticalScroll
import ramirez.ruben.closetvirtual.data.database.dao.PrendaDao
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme
import ramirez.ruben.closetvirtual.viewmodel.UsuarioViewModel
import ramirez.ruben.closetvirtual.viewmodel.LoginViewModel
import ramirez.ruben.closetvirtual.data.database.dao.UsuarioDao
import ramirez.ruben.closetvirtual.data.database.entity.UsuarioEntity
import ramirez.ruben.closetvirtual.data.database.repository.UsuarioRepository
import ramirez.ruben.closetvirtual.viewmodel.GestionPrendaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {},
    viewModel: UsuarioViewModel,
    loginViewModel: LoginViewModel? = null
){

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }

    var isPasswordVisible by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    val showPasswordError = confirmPassword.isNotEmpty() && password != confirmPassword

    var expandedGender by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf("") }
    var customGender by remember { mutableStateOf("") }
    val genderOptions = listOf("Mujer", "Hombre", "Personalizado")

    val uiStateMessage by viewModel.uiStateMessage.collectAsState()

    // Pantalla principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Registrarse",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(64.dp))

        // Campos de texto del registro
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre", color = Color.Gray) },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.mipmap.user_icon),
                    contentDescription = "Icono de usuario",
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            },

            modifier = Modifier.width(270.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
            onValueChange = { email = it },
            label = { Text("Correo Electrónico", color = Color.Gray) },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.mipmap.email_icon),
                    contentDescription = "Icono de email",
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            },

            modifier = Modifier.width(270.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", color = Color.Gray) },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.mipmap.password_icon),
                    contentDescription = "Icono de contraseña",
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Image(
                        painter = painterResource(
                            id =
                                if (isPasswordVisible) R.mipmap.nohidden_icon
                                else R.mipmap.hidden_icon
                        ),
                        contentDescription =
                            if (isPasswordVisible) "Ocultar contraseña"
                            else "Mostrar contraseña",
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                    )
                }
            },
            modifier = Modifier.width(270.dp),
            singleLine = true,
            visualTransformation =
                if (isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Confirmar contra
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña", color = Color.Gray) },
            isError = showPasswordError,
            supportingText = {
                if (showPasswordError) {
                    Text("Passwords do not match", color = MaterialTheme.colorScheme.error)
                }
            },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.mipmap.password_icon),
                    contentDescription = "Icono de contraseña",
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            },
            trailingIcon = {
                IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                    Image(
                        painter = painterResource(
                            id =
                                if (isConfirmPasswordVisible) R.mipmap.nohidden_icon
                                else R.mipmap.hidden_icon
                        ),
                        contentDescription =
                            if (isConfirmPasswordVisible) "Ocultar contraseña"
                            else "Mostrar contraseña",
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                    )
                }
            },
            modifier = Modifier.width(270.dp),
            singleLine = true,
            visualTransformation =
                if (isConfirmPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                errorContainerColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = dateOfBirth,
            onValueChange = { dateOfBirth = it },
            label = { Text("(DD/MM/AAAA)", color = Color.Gray) },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.mipmap.calendar_icon),
                    contentDescription = "Icono de calendario",
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            },
            modifier = Modifier.width(270.dp),
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

        // Genero desplegable y personalizable (Se muestra textfield si el genero es "personalizado")
        ExposedDropdownMenuBox(
            expanded = expandedGender,
            onExpandedChange = { expandedGender = !expandedGender }
        ) {
            TextField(
                value = selectedGender,
                onValueChange = {},
                readOnly = true,
                label = { Text("Genero", color = Color.Gray) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGender) }, // La flechita que gira
                modifier = Modifier
                    .menuAnchor()
                    .width(270.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary
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

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // hay que validar la contrasena antes de mandarla
                if (password == confirmPassword) {
                    viewModel.registrar(
                        nombre = username,
                        correo = email,
                        contrasena = password,
                        fechaNacimiento = dateOfBirth,
                        genero = selectedGender,
                        onSuccess = { userId ->
                            loginViewModel?.login(email, password, onRegisterSuccess, {})
                        }
                    )
                }
            },
            modifier = Modifier.width(270.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors
                (containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Registrarse",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "--------------------- or ---------------------",
            color = Color.Gray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { onNavigateToLogin() }, // Aca manda al usuario al login
            modifier = Modifier.width(270.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors
                (containerColor = MaterialTheme.colorScheme.tertiary)
        ) {
            Text(
                text = "Iniciar Sesión",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }


    }
}

// mock de un usuario porque quier la preview si o si
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

@Preview(name = "Modo Claro", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewModoClaro() {
    ClosetVirtualTheme(darkTheme = false) {
        RegisterScreen(
            onNavigateToLogin = {},
            onRegisterSuccess = {},
            viewModel = provideDummyUsuarioViewModel(), //mock djfrbgkj
            loginViewModel = null
        )
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
        RegisterScreen(
            onNavigateToLogin = {},
            onRegisterSuccess = {},
            viewModel = provideDummyUsuarioViewModel() // mock
        )
    }
}