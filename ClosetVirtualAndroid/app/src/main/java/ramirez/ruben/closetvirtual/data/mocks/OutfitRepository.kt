package ramirez.ruben.closetvirtual.data.mocks

import ramirez.ruben.closetvirtual.data.database.entity.Outfit

// Repositorio temporal para datos de prueba en pantalla.
// Se usa emptyList() para las prendas porque ahora los datos reales
// viven en SQLite y se cargan de forma asíncrona mediante ViewModels.
object OutfitRepository {

    val todosLosOutfits: List<Outfit> = listOf(
        Outfit(
            nombre = "Total Vinipiel Look",
            estilo = "Rock",
            temporada = "Otoño",
            formalidad = "No",
            prendas = emptyList(),
            tags = listOf("Noche", "Cuero", "Trendy")
        ),

        Outfit(
            nombre = "Gym & Run",
            estilo = "Sport",
            temporada = "Todas las temporadas",
            formalidad = "No",
            prendas = emptyList(),
            tags = listOf("Fitness", "Cómodo", "Entrenamiento")
        ),

        Outfit(
            nombre = "Business Casual",
            estilo = "Oficina",
            temporada = "Todas las temporadas",
            formalidad = "Formal",
            prendas = emptyList(),
            tags = listOf("Trabajo", "Elegante")
        ),

        Outfit(
            nombre = "Casual Weekend",
            estilo = "Relajado",
            temporada = "Primavera / Verano",
            formalidad = "Informal",
            prendas = emptyList(),
            tags = listOf("Fin de semana", "Fresco", "Básico")
        )
    )
}