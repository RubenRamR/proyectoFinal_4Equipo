package ramirez.ruben.closetvirtual.utils

import androidx.compose.ui.graphics.Color

data class OpcionColor(val nombre: String, val valor: Color)

object PrendaConstants {
    val coloresPrenda = listOf(
        OpcionColor("Blanco", Color(0xFFFFFFFF)),
        OpcionColor("Negro", Color(0xFF000000)),
        OpcionColor("Gris", Color(0xFF808080)),
        OpcionColor("Gris Claro", Color(0xFFD3D3D3)),
        OpcionColor("Azul Marino", Color(0xFF000080)),
        OpcionColor("Azul", Color(0xFF0000FF)),
        OpcionColor("Azul Claro", Color(0xFFADD8E6)),
        OpcionColor("Turquesa", Color(0xFF40E0D0)),
        OpcionColor("Verde Oscuro", Color(0xFF006400)),
        OpcionColor("Verde", Color(0xFF228B22)),
        OpcionColor("Verde Lima", Color(0xFF32CD32)),
        OpcionColor("Amarillo", Color(0xFFFFD700)),
        OpcionColor("Naranja", Color(0xFFFF8C00)),
        OpcionColor("Rojo", Color(0xFFFF0000)),
        OpcionColor("Burdeos", Color(0xFF800000)),
        OpcionColor("Rosa", Color(0xFFFFC0CB)),
        OpcionColor("Fucsia", Color(0xFFFF00FF)),
        OpcionColor("Morado", Color(0xFF800080)),
        OpcionColor("Lila", Color(0xFFE6E6FA)),
        OpcionColor("Café", Color(0xFF8B4513)),
        OpcionColor("Marrón", Color(0xFFA52A2A)),
        OpcionColor("Beige", Color(0xFFF5F5DC)),
        OpcionColor("Crema", Color(0xFFFFFDD0)),
        OpcionColor("Caqui", Color(0xFFF0E68C))
    )
}