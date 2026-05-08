package ramirez.ruben.closetvirtual.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Tune
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import ramirez.ruben.closetvirtual.R
import ramirez.ruben.closetvirtual.datastore.mockOutfits
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import ramirez.ruben.closetvirtual.datastore.Outfit

@Composable
fun CalendarioScreen(onNavigateBack: () -> Unit = {}) {
    // para tener el mes y el anio del calendario y la seleccion
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    // Fuente montserrat
    val Montserrat = FontFamily(
        Font(resId = R.font.montserrat_regular, weight = FontWeight.Normal),
        Font(resId = R.font.montserrat_italic, weight = FontWeight.Normal, style = FontStyle.Italic)
    )

    // estructura principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // btn de regreso
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.offset(x = (-24).dp)
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.arrow_back),
                    contentDescription = "Icono de atrás",
                    tint = Color(0xFF26657A),
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Text(
            text = "Calendario",
            fontSize = 32.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // contenedor del calendario
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFB0C4DE), RoundedCornerShape(16.dp)) // Borde sutil
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CalendarioHeader(
                    currentMonth = currentMonth,
                    onPreviousMonth = { currentMonth = currentMonth.minusMonths(1) },
                    onNextMonth = { currentMonth = currentMonth.plusMonths(1) },
                    onMonthSelected = { /* faltaria la logica del dropdown */ },
                    onYearSelected = { /* aca tambien */ }
                )

                Spacer(modifier = Modifier.height(16.dp))

                CalendarioDaysOfWeek()

                Spacer(modifier = Modifier.height(8.dp))

                CalendarioGrid(
                    currentMonth = currentMonth,
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it }
                )
            }
        }

        // categorias
        Spacer(modifier = Modifier.height(24.dp))

        FiltrosCalendario(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        // outfits mock
        mockOutfits.forEach { outfit ->
            OutfitCard(outfit = outfit)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// header del calendario
@Composable
fun CalendarioHeader(
    currentMonth: YearMonth,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onMonthSelected: () -> Unit,
    onYearSelected: () -> Unit
) {

    // detectar si el dispositivo esta modo darks o no
    val isDarkTheme = isSystemInDarkTheme()

    // Fuente montserrat
    val Montserrat = FontFamily(
        Font(resId = R.font.montserrat_regular, weight = FontWeight.Normal),
        Font(resId = R.font.montserrat_italic, weight = FontWeight.Normal, style = FontStyle.Italic)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = onPreviousMonth) {
            Icon(
                Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Mes anterior",
                tint = Color(0xFF26657A))
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .border(1.dp, Color(0xFFB0C4DE),RoundedCornerShape(8.dp))
                    .clickable { onMonthSelected() }
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    val monthName = currentMonth.month.getDisplayName(
                        TextStyle.SHORT,
                        Locale.getDefault()).replaceFirstChar { it.uppercase() }

                    Text(
                        text = monthName,
                        fontFamily = Montserrat,
                        color = if (isDarkTheme) Color(0xFFB1DCDF) else Color(0xFF2F7D9B),
                        fontWeight = FontWeight.W600
                    )

                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Seleccionar mes",
                        tint = Color(0xFF26657A),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .border(1.dp, Color(0xFFB0C4DE), RoundedCornerShape(8.dp))
                    .clickable { onYearSelected() }
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        fontFamily = Montserrat,
                        text = currentMonth.year.toString(),
                        color = if (isDarkTheme) Color(0xFFB1DCDF) else Color(0xFF2F7D9B),
                        fontWeight = FontWeight.W600
                    )

                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Seleccionar año",
                        tint = Color(0xFF26657A),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        IconButton(onClick = onNextMonth) {
            Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Mes siguiente", tint = Color(0xFF26657A))
        }
    }
}

@Composable
fun CalendarioDaysOfWeek() {
    // dias de la semana
    val days = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")

    // Fuente montserrat
    val Montserrat = FontFamily(
        Font(resId = R.font.montserrat_regular, weight = FontWeight.Normal),
        Font(resId = R.font.montserrat_italic, weight = FontWeight.Normal, style = FontStyle.Italic)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        days.forEach { day ->
            Text(
                text = day,
                color = Color(0xFF81B2B4),
                fontSize = 12.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun FiltrosCalendario(
    modifier: Modifier = Modifier
) {
    val filters = listOf("Estilo", "Formalidad", "Temporada", "Color")

    // Fuente montserrat
    val Montserrat = FontFamily(
        Font(resId = R.font.montserrat_regular, weight = FontWeight.Normal),
        Font(resId = R.font.montserrat_italic, weight = FontWeight.Normal, style = FontStyle.Italic)
    )

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            Icon(
                Icons.Default.Tune,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        items(filters) { text ->
            AssistChip(
                onClick = {},
                label = {
                    Text(
                        text,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.W600
                    )}
            )
        }
    }
}

// aca la cuadricula de los dias
@Composable
fun CalendarioGrid(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfMonth = currentMonth.atDay(1)
    val daysInMonth = currentMonth.lengthOfMonth()

    // lo pongo que empiece desde el domingo como sale en el storyboard
    val firstDayOfWeek = if (firstDayOfMonth.dayOfWeek == DayOfWeek.SUNDAY) 0 else firstDayOfMonth.dayOfWeek.value

    val totalDaysGrid = firstDayOfWeek + daysInMonth
    val weeks = Math.ceil(totalDaysGrid / 7.0).toInt()

    // referencias para los dias anterios y despues del mes actual (para que quede como en el storyboard)
    val prevMonth = currentMonth.minusMonths(1)
    val nextMonth = currentMonth.plusMonths(1)
    val daysInPrevMonth = prevMonth.lengthOfMonth()

    // detectar si el dispositivo esta modo darks o no
    val isDarkTheme = isSystemInDarkTheme()

    // Fuente montserrat
    val Montserrat = FontFamily(
        Font(resId = R.font.montserrat_regular, weight = FontWeight.Normal),
        Font(resId = R.font.montserrat_italic, weight = FontWeight.Normal, style = FontStyle.Italic)
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        for (week in 0 until weeks) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (dayIndex in 0..6) {
                    val currentGridDay = (week * 7) + dayIndex
                    val dateNum = currentGridDay - firstDayOfWeek + 1

                    // los cuadros de los dias
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {

                        val thisDate: LocalDate
                        val isCurrentMonth: Boolean
                        val displayText: String

                        if (dateNum in 1..daysInMonth) {

                            // dias del mes actual
                            thisDate = currentMonth.atDay(dateNum)
                            isCurrentMonth = true
                            displayText = dateNum.toString()
                        } else if (dateNum < 1) {

                            // dias del mes anterior
                            val prevMonthDay = daysInPrevMonth + dateNum
                            thisDate = prevMonth.atDay(prevMonthDay)
                            isCurrentMonth = false
                            displayText = prevMonthDay.toString()
                        } else {

                            // dias del mes siguiente
                            val nextMonthDay = dateNum - daysInMonth
                            thisDate = nextMonth.atDay(nextMonthDay)
                            isCurrentMonth = false
                            displayText = nextMonthDay.toString()
                        }

                        val isSelected = thisDate == selectedDate
                        val isToday = thisDate == LocalDate.now()

                        // con los colores es oscuro al seleccionar un dia y claro si es el dia de hoy
                        val backgroundColor = when {
                            isSelected -> Color(0xFF26657A)
                            isToday -> if (isDarkTheme) Color(0xFF1E3A45) else Color(0xFFE2EFF0)
                            else -> Color.Transparent
                        }

                        // si los dias no son del mes actual es un color mas fuerte
                        val textColor = when {
                            isSelected -> Color.White
                            isCurrentMonth -> if (isDarkTheme) Color(0xFF81B2B4) else Color(0xFF26657A)
                            else -> if (isDarkTheme) Color(0xFF26657A) else Color(0xFF81B2B4)
                        }

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(backgroundColor, RoundedCornerShape(8.dp))
                                .clickable { onDateSelected(thisDate) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = displayText,
                                color = textColor,
                                fontFamily = Montserrat,
                                fontSize = 14.sp,
                                fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }
}

// componente visual para las tarjetitas de los outfits de la fecha (mock)
@Composable
fun OutfitCard(outfit: Outfit) {
    val isDark = isSystemInDarkTheme()
    val cardBackgroundColor = if (isDark) Color(0xFF44344E) else Color(0xFFECE6F0)

    // Fuente montserrat
    val Montserrat = FontFamily(
        Font(resId = R.font.montserrat_regular, weight = FontWeight.Normal),
        Font(resId = R.font.montserrat_italic, weight = FontWeight.Normal, style = FontStyle.Italic)
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        color = cardBackgroundColor,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // IMAGEN RECTANGULAR
            Image(
                painter = painterResource(id = outfit.imagenRes),
                contentDescription = "Imagen de ${outfit.nombre}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .height(110.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Outfit ${outfit.nombre}",
                    fontSize = 16.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = outfit.descripcion,
                    fontSize = 14.sp,
                    fontFamily = Montserrat,
                    color = if (isDark) Color(0xFFD3D3D3) else Color(0xFF808080),
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.mipmap.calendario_icon),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = if (isDark) Color(0x4DFFFFFF) else Color(0x4D808080)
                    )
                    Text(
                        text = outfit.fecha,
                        fontFamily = Montserrat,
                        fontSize = 11.sp,
                        color = if (isDark) Color(0x33FFFFFF) else Color(0x33000000)
                    )
                }
            }
        }
    }
}

@Preview(name = "Modo Claro", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewModoClaro() {
    ClosetVirtualTheme(darkTheme = false) {
        CalendarioScreen()
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
        CalendarioScreen()
    }
}

