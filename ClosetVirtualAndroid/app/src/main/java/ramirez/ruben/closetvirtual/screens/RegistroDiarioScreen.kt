package ramirez.ruben.closetvirtual.screens

import android.content.res.Configuration
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramirez.ruben.closetvirtual.R
import ramirez.ruben.closetvirtual.data.database.entity.PrendaEntity
import ramirez.ruben.closetvirtual.data.mocks.PrendaMock
import ramirez.ruben.closetvirtual.data.mocks.prendasMock
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme
import ramirez.ruben.closetvirtual.viewmodel.RegistroDiarioViewModel

@Composable
fun RegistroDiarioScreen(
    viewModel: RegistroDiarioViewModel,
    onNavigateBack: () -> Unit = {}
) {
    // Ya no usamos mocks, llamamos al viewmodel inyectado
    val prendasDisponibles by viewModel.prendasDisponibles.collectAsState()
    val prendasSeleccionadas by viewModel.prendasSeleccionadas.collectAsState()

    // Obtenemos el estado de la racha
    val streakCount by viewModel.streakCount.collectAsState()
    val hasLoggedToday by viewModel.hasLoggedToday.collectAsState()

    // Estructura principal de registro
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {

            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.offset(x = (-25).dp)
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.left),
                    contentDescription = "Icono de atrás",
                    tint = Color(0xFF000000),
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Text(
            text = "Registro Diario",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Racha en fila
        // Si el streak es 1, enciende 1. Si es 5, enciende 5. Si es 6, vuelve a encender 1 visualmente
        val maxVisualIcons = 5
        val filledIcons = if (streakCount == 0) 0 else ((streakCount - 1) % maxVisualIcons) + 1

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            for (i in 0 until maxVisualIcons) {
                val isFilled = i < filledIcons
                Image(
                    painter = painterResource(id = if (isFilled) R.mipmap.streak_icon else R.mipmap.coldstreak_icon),
                    contentDescription = if (isFilled) "Racha activa" else "Racha inactiva",
                    modifier = Modifier.size(40.dp),

                    // Solo aplica el filtro si está inactiva para que el coldstreak combine con el tema
                    colorFilter = if (!isFilled) ColorFilter.tint(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)) else null
                )
            }
        }

        // Texto de cuántos días llevas
        Text(
            text = "🔥 $streakCount días 🔥",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (streakCount > 0) Color(0xFFE07A5F) else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¡Registra tu outfit dirariamente para continuar con tu racha!",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Image(
            painter = painterResource(id = R.mipmap.dress_icon),
            contentDescription = "Icono de vestido",
            modifier = Modifier
                .size(64.dp)
                .padding(bottom = 16.dp)
        )

        // Prendas de PrendaMock
        Surface(
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "SELECCIONA TUS PRENDAS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Mostrar las prendas en dos columnas juntas cuadricula
                val filas = prendasDisponibles.chunked(2)
                filas.forEach { fila ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        fila.forEach { prenda ->
                            PrendaItem(
                                prenda = prenda,
                                isSelected = prendasSeleccionadas.contains(prenda.id),
                                onClick = { viewModel.togglePrenda(prenda.id) },
                                onFavoriteClick = { viewModel.toggleFavorito(prenda) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (fila.size == 1) Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Boton para guardar el outfit/prendas del dia (mock)
        Button(
            onClick = {
                viewModel.guardarRegistroDiario(onSuccess = { onNavigateBack() })
            },
            enabled = !hasLoggedToday && prendasSeleccionadas.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF264653),
                disabledContainerColor = Color.Gray // Se pone gris si ya registró
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = if (hasLoggedToday) "REGISTRO COMPLETADO HOY" else "GUARDAR REGISTRO",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun PrendaItem(
    prenda: PrendaEntity,
    isSelected: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val highlightColor = Color(0xFF26657A)
    val bgColor = if (isSelected) highlightColor.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
    val borderColor = if (isSelected) highlightColor else Color.Transparent

    // Decodificar ByteArray a ImageBitmap
    val imageBitmap = remember(prenda.imagen) {
        prenda.imagen?.let { bytes ->
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)?.asImageBitmap()
        }
    }

    Box(
        modifier = modifier
            .background(bgColor, RoundedCornerShape(12.dp))
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
            Text(prenda.nombre, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            prenda.marca?.let { Text(it, fontSize = 10.sp, maxLines = 1) }

            Spacer(modifier = Modifier.height(8.dp))

            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = prenda.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(8.dp))
                )
            } else {
                Image(
                    painter = painterResource(id = R.mipmap.no_image),
                    contentDescription = "Sin imagen",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(8.dp))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(prenda.temporada, fontSize = 10.sp, maxLines = 1)
                    Text(prenda.categoria, fontSize = 10.sp, maxLines = 1)
                }

                Image(
                    painter = painterResource(id = if (prenda.favorito) R.mipmap.favorite_icon else R.mipmap.unfavorite_icon),
                    contentDescription = "Favorito",
                    modifier = Modifier.size(16.dp).clickable { onFavoriteClick() }
                )
            }
        }
    }
}
