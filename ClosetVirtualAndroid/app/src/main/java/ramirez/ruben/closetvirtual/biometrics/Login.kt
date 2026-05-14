package ramirez.ruben.closetvirtual.biometrics

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

@Composable
fun LoginScreen(innerPadding: PaddingValues, context: Context, viewModel: LoginViewMovel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var authStatus by remember { mutableStateOf("Esperando autenticacion") }
    var biometricAvailable by remember { mutableStateOf(false) }

    val biometricActive by viewModel.biometricActive.collectAsState(initial = false)

    LaunchedEffect(Unit) {
        val biometricManager: BiometricManager = BiometricManager.from(context)
        when(biometricManager.canAuthenticate(BIOMETRIC_STRONG)){
            BiometricManager.BIOMETRIC_SUCCESS -> {
                authStatus = "Biometricos disponibles, presione el boton para iniciar sesion"
                biometricAvailable = true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                authStatus = "El dispositivo no tiene sensor biometrico"
                biometricAvailable = false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                authStatus = "El sensor biometrico no esta disponible"
                biometricAvailable = false
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                authStatus = "Datos biometricos no registrados, registralos en tu dispositivo"
                biometricAvailable = false
            }
        }
    }

    val activity = context as FragmentActivity
    val executor: Executor = ContextCompat.getMainExecutor(context)

    val biometricPrompt: BiometricPrompt = remember{
        BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    authStatus = "Error: ${errString}"
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    authStatus = "Autenticacion exitosa"
                    viewModel.loginWithBiometrics()
                }

                override fun onAuthenticationFailed() {
                    authStatus = "Autenticacion fallida"
                }
            }
        )

    }

    val promptInfo: BiometricPrompt.PromptInfo = remember {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticacion biometrica")
            .setSubtitle("Usa tu huella-cara para iniciar")
            .setDescription("Coloca tu dedo en el sensor o mira la camara")
            .setNegativeButtonText("Cancelar")
            .build()
    }

    Column(){
        Text(text = "Iniciar sesion")
        TextField(email, onValueChange = { email = it }, label = {Text("Correo electronico:")})
        TextField(password, onValueChange = { password = it },
            label = {Text("Contraseña: ")}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Button(
            onClick = {
                viewModel.login(email, password)
            },
            enabled = true
        ) {
            Text("Iniciar sesion con contraseña")
        }

        if (biometricActive) {
            Button(onClick = { biometricPrompt.authenticate(promptInfo) }) {
                Text("Usar huella")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(authStatus)
    }

}