package ramirez.ruben.closetvirtual.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramirez.ruben.closetvirtual.feature.registrodiario.ui.RegistroDiarioScreen
import ramirez.ruben.closetvirtual.ui.theme.ClosetVirtualTheme

// estructura simple para el menu de categorias mock
data class FilterCategory(
    val id: Int,
    val name: String
)

// mock de categorias
val mockCategories = listOf(
    FilterCategory(1, "Marca"),
    FilterCategory(2, "Temporada"),
    FilterCategory(3, "Popular"),
    FilterCategory(4, "Trending"),
    FilterCategory(5, "Recientes"),
    FilterCategory(6, "Formal"),
    FilterCategory(7, "Casual"),
    FilterCategory(8, "Deporte"),
    FilterCategory(9, "Ofertas"),
    FilterCategory(10, "Color")
)

@Composable
fun CategoryMenu(
    modifier: Modifier = Modifier,
    categories: List<FilterCategory> = mockCategories
) {
    // para ver cual esta seleccionada, marca esta por defecto
    var selectedCategoryId by remember { mutableStateOf(1) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        // LazyRow para scroll horizontal
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { category ->
                // parte visual para los cuadritos de cada categoría
                CategoryChip(
                    category = category,
                    isSelected = category.id == selectedCategoryId,
                    onClick = {
                        selectedCategoryId = category.id
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryChip(
    category: FilterCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // cambia de color al seleccionarse
    val backgroundColor = if (isSelected) Color.Black else Color(0xFFF0F0F0)
    val textColor = if (isSelected) Color.White else Color.Black
    val borderColor = if (isSelected) Color.Black else Color(0xFFD0D0D0)

    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(50.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(50.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = category.name,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoryMenu() {
    CategoryMenu()
}