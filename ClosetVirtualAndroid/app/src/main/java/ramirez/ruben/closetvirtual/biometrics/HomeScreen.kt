package ramirez.ruben.closetvirtual.biometrics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(viewModel: LoginViewMovel) {
    val username by viewModel.username.collectAsState(initial = "")
    val biometricsActive by viewModel.biometricActive.collectAsState(initial = false)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "Hola $username")

        Spacer(modifier = Modifier.height(30.dp))

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(text = "Autenticacion biometrica")
            Checkbox(checked = biometricsActive, onCheckedChange ={viewModel.toggleBiometrics(it) })
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = { viewModel.logout() }) {
            Text(text = "Cerrar sesion")
        }
    }
}

