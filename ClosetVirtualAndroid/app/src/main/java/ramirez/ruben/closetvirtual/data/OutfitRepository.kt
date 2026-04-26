package ramirez.ruben.closetvirtual.data

import ramirez.ruben.closetvirtual.components.Outfit

// repositorio solo para tener datos para mostrar en pantalla
object OutfitRepository {

    val todosLosOutfits: List<Outfit> = listOf(
        Outfit(
            nombre = "Total Vinipiel Look",
            estilo = "Rock",
            temporada = "Otoño",
            formalidad = "No",
            prendas = PrendaRepository.todasLasPrendas.filter {
                it.nombre.contains("Vinipiel", ignoreCase = true) ||
                        it.nombre.contains("Bolsa", ignoreCase = true) ||
                        it.nombre.contains("Tacones", ignoreCase = true)
            },
            tags = listOf("Noche", "Cuero", "Trendy")
        ),

        Outfit(
            nombre = "Gym & Run",
            estilo = "Sport",
            temporada = "Todas las temporadas",
            formalidad = "No",
            prendas = PrendaRepository.todasLasPrendas.filter {
                it.nombre.contains("Deportiva", ignoreCase = true) ||
                        it.nombre.contains("Short Deportivo", ignoreCase = true) ||
                        it.nombre.contains("Tenis", ignoreCase = true)
            },
            tags = listOf("Fitness", "Cómodo", "Entrenamiento")
        ),

        Outfit(
            nombre = "Business Casual",
            estilo = "Oficina",
            temporada = "Todas las temporadas",
            formalidad = "Formal",
            prendas = PrendaRepository.todasLasPrendas.filter {
                it.nombre == "Blusa Formal Blanca" || it.nombre == "Pantalón de Vestir Formal"
            },
            tags = listOf("Trabajo", "Elegante")
        ),

        Outfit(
            nombre = "Casual Weekend",
            estilo = "Relajado",
            temporada = "Primavera / Verano",
            formalidad = "Informal",
            prendas = PrendaRepository.todasLasPrendas.filter {
                it.nombre == "Blusa Rosa Casual" ||
                        it.nombre == "Pantalón de Mezclilla Clásico" ||
                        it.nombre == "Sombrero de Playa" ||
                        it.nombre == "Tenis Blancos Urbanos"
            },
            tags = listOf("Fin de semana", "Fresco", "Básico")
        )
    )
}
