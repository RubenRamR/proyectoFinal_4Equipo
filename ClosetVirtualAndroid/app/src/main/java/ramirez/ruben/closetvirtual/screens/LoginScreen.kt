package ramirez.ruben.closetvirtual.screens

import android.content.res.Configuration
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme
import ramirez.ruben.closetvirtual.R
import ramirez.ruben.closetvirtual.viewmodel.UsuarioViewModel
import ramirez.ruben.closetvirtual.data.database.dao.UsuarioDao
import ramirez.ruben.closetvirtual.data.database.entity.UsuarioEntity
import ramirez.ruben.closetvirtual.data.database.repository.UsuarioRepository
import ramirez.ruben.closetvirtual.data.datastore.DataStoreManager
import ramirez.ruben.closetvirtual.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    viewModel: UsuarioViewModel,
    loginViewModel: LoginViewModel? = null
){
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    // Esto es para poder mostrar y ocultar la contrasena a gusto
    var isPasswordVisible by remember { mutableStateOf(false) }

    val uiStateMessage by viewModel.uiStateMessage.collectAsState()
    val loginErrorMessage by loginViewModel?.errorMessage?.collectAsState() ?: remember { mutableStateOf(null) }
    val isBiometricEnabled by loginViewModel?.isBiometricsEnabled?.collectAsState(initial = false) ?: remember { mutableStateOf(false) }
    val userId by loginViewModel?.userId?.collectAsState(initial = null) ?: remember { mutableStateOf(null) }

    var authStatus by remember { mutableStateOf("") }
    var biometricAvailable by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val biometricManager = BiometricManager.from(context)
        val authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG or 
                            BiometricManager.Authenticators.DEVICE_CREDENTIAL
        
        when (biometricManager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_SUCCESS -> biometricAvailable = true
            else -> biometricAvailable = false
        }
    }

    val executor = remember { ContextCompat.getMainExecutor(context) }
    val biometricPrompt = remember {
        BiometricPrompt(
            context as FragmentActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    authStatus = "Error: $errString"
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    authStatus = "Autenticación exitosa"
                    loginViewModel?.loginWithBiometrics(onLoginSuccess)
                }

                override fun onAuthenticationFailed() {
                    authStatus = "Autenticación fallida"
                }
            }
        )
    }

    val promptInfo = remember {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación biométrica")
            .setSubtitle("Usa tu huella o cara para iniciar")
            .setNegativeButtonText("Cancelar")
            .build()
    }

    // Pantalla principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Inicio de Sesión",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(64.dp))

        // Campos de texto de email y la contraseña
        TextField(
            value = email,
            onValueChange =
                {
                    email = it
                    viewModel.clearMessage()
                    loginViewModel?.clearError()
                },
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
            onValueChange =
                {
                    password = it
                    viewModel.clearMessage()
                    loginViewModel?.clearError()
                },
            label = { Text("Contraseña", color = Color.Gray) },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.mipmap.password_icon),
                    contentDescription = "Icono de contraseña",
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            },

            // Mecanica para poder mostrar y ocultar la contrasena
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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿Olvidaste tu contraseña?",
            color = Color(0xFF4F46E5),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 40.dp)
                .clickable { println("Usuario Contraseña olvidada") }
        )

        Spacer(modifier = Modifier.height(45.dp))

        // se muestran debajo los mensajes de error (si existen)
        val displayMessage = uiStateMessage ?: loginErrorMessage
        displayMessage?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                // Hacemos el llamado a la BD a través del LoginViewModel para manejar la sesión
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    loginViewModel?.login(
                        correo = email,
                        password = password,
                        onLoginSuccess = onLoginSuccess,
                        onLoginError = { error ->
                            // Mostramos el mensaje de error usando el ViewModel de usuario para mantener compatibilidad con el UI existente
                            // O podríamos agregar un StateFlow de error al LoginViewModel
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
                text = "Iniciar Sesión",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "-------------------- or --------------------",
            color = Color.Gray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onNavigateToRegister() }, // Aca manda al usuario a que se registre
            modifier = Modifier.width(270.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors
                (containerColor = MaterialTheme.colorScheme.tertiary)
        ) {
            Text(
                text = "Registrarse",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }

        Spacer(modifier = Modifier.height(90.dp))

        if (authStatus.isNotEmpty()) {
            Text(
                text = authStatus,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.mipmap.fingerprint),
                contentDescription = "Iniciar sesión con huella",
                alpha = if (biometricAvailable && isBiometricEnabled && userId != null) 1f else 0.2f,
                modifier = Modifier
                    .size(100.dp)
                    .clickable {
                        if (!biometricAvailable) {
                            authStatus = "El dispositivo no soporta o no tiene configurada la biometría"
                        } else if (userId == null) {
                            authStatus = "Primero inicia sesión con tu correo y contraseña"
                        } else if (!isBiometricEnabled) {
                            authStatus = "Activa la biometría en tu perfil para usar esta función"
                        } else {
                            biometricPrompt.authenticate(promptInfo)
                        }
                    }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

    }
}

// viewmodel mock para la preview
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
        LoginScreen(
            onNavigateToRegister = {},
            onLoginSuccess = {},
            viewModel = provideDummyUsuarioViewModel(), // mock
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
        LoginScreen(
            onNavigateToRegister = {},
            onLoginSuccess = {},
            viewModel = provideDummyUsuarioViewModel(), // mock
            loginViewModel = null
        )
    }
}