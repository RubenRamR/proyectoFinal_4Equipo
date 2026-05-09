package ramirez.ruben.closetvirtual.screens

import android.content.res.Configuration
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme
import ramirez.ruben.closetvirtual.R

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import ramirez.ruben.closetvirtual.viewmodel.LoginViewModel
import ramirez.ruben.closetvirtual.data.database.dao.UsuarioDao
import ramirez.ruben.closetvirtual.data.database.entity.UsuarioEntity
import ramirez.ruben.closetvirtual.data.database.repository.UsuarioRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    viewModel: LoginViewModel = viewModel()
){
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    // Esto es para poder mostrar y ocultar la contrasena a gusto
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Pantalla principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
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

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                viewModel.login(
                    correo = email,
                    password = password,
                    onLoginSuccess = {
                        Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                        onLoginSuccess()
                    },
                    onLoginError = { error ->
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    }
                )
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
            text = "-------------------------- or --------------------------",
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

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.mipmap.fingerprint),
                contentDescription = "Iniciar sesión con huella",
                alpha = 0.5f,
                modifier = Modifier
                    .size(100.dp)
                    .clickable { println("Inicio de sesion fon Fingerprint") }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

    }
}

@Preview(name = "Modo Claro", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewModoClaro() {
    val fakeDao = object : UsuarioDao {
        override suspend fun registrarUsuario(usuario: UsuarioEntity): Long = 0
        override suspend fun login(correo: String, password: String): UsuarioEntity? = null
    }
    val fakeRepo = UsuarioRepository(fakeDao)
    val fakeViewModel = LoginViewModel(fakeRepo)
    ClosetVirtualTheme(darkTheme = false) {
        LoginScreen(viewModel = fakeViewModel)
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
    val fakeDao = object : UsuarioDao {
        override suspend fun registrarUsuario(usuario: UsuarioEntity): Long = 0
        override suspend fun login(correo: String, password: String): UsuarioEntity? = null
    }
    val fakeRepo = UsuarioRepository(fakeDao)
    val fakeViewModel = LoginViewModel(fakeRepo)
    ClosetVirtualTheme(darkTheme = true) {
        LoginScreen(viewModel = fakeViewModel)
    }
}
