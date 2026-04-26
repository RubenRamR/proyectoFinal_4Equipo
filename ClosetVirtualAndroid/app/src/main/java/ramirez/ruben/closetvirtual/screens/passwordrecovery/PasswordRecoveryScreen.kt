package ramirez.ruben.closetvirtual.screens.passwordrecovery

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme
import ramirez.ruben.closetvirtual.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordRecoveryScreen(){
    var email by remember { mutableStateOf("") }

    // Pantalla principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Recuperar Contraseña",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Ingresa tu correo electrónico asociado y te enviaremos las instrucciones para restablecer tu contraseña.",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 12.dp),
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )

        Spacer(modifier = Modifier.height(80.dp))

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

        Spacer(modifier = Modifier.height(70.dp))

        Button(
            onClick = { println("Correo a enviar el enlace: Email=$email") },
            modifier = Modifier.width(270.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors
                (containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Envíar enlace",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Preview(name = "Modo Claro", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewModoClaro() {
    ClosetVirtualTheme(darkTheme = false) {
        PasswordRecoveryScreen()
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
        PasswordRecoveryScreen()
    }
}