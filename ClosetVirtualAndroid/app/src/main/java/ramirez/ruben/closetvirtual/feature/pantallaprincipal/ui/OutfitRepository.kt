package ramirez.ruben.closetvirtual.feature.pantallaprincipal.ui

import ramirez.ruben.closetvirtual.data.Prenda

class OutfitRepository {

    private val prendaRepo = PrendaRepository()
    private val todasLasPrendas = prendaRepo.obtenerPrendas()

    fun obtenerOutfits(): List<Outfit> {
        return listOf(
            // 1. Outfit Vinipiel (Chaqueta, Pantalón, Bolsa, Sombrero)
            Outfit(
                nombre = "Total Vinipiel Look",
                estilo = "Rock / Fashion",
                temporada = "Otoño",
                formalidad = "Casual Night",
                prendas = todasLasPrendas.filter { 
                    it.nombre.contains("Vinipiel", ignoreCase = true) || 
                    it.nombre.contains("Bolsa", ignoreCase = true) ||
                    it.nombre.contains("Tacones", ignoreCase = true)
                },
                tags = listOf("Noche", "Cuero", "Trendy")
            ),
            
            // 2. Outfit Deportivo
            Outfit(
                nombre = "Gym & Run",
                estilo = "Sport",
                temporada = "Todas las temporadas",
                formalidad = "Deportivo",
                prendas = todasLasPrendas.filter { 
                    it.nombre.contains("Deportiva", ignoreCase = true) || 
                    it.nombre.contains("Short Deportivo", ignoreCase = true) ||
                    it.nombre.contains("Tenis", ignoreCase = true)
                },
                tags = listOf("Fitness", "Cómodo", "Entrenamiento")
            ),
            
            // 3. Outfit Formal (2 prendas: Blusa Formal y Pantalón Formal)
            Outfit(
                nombre = "Business Casual",
                estilo = "Oficina",
                temporada = "Todas las temporadas",
                formalidad = "Formal",
                prendas = todasLasPrendas.filter { 
                    it.nombre == "Blusa Formal Blanca" || it.nombre == "Pantalón de Vestir Formal"
                },
                tags = listOf("Trabajo", "Elegante")
            ),
            
            // 4. Outfit Casual Mixto
            Outfit(
                nombre = "Casual Weekend",
                estilo = "Relajado",
                temporada = "Primavera / Verano",
                formalidad = "Casual",
                prendas = todasLasPrendas.filter { 
                    it.nombre == "Blusa Rosa Casual" || 
                    it.nombre == "Pantalón de Mezclilla Clásico" || 
                    it.nombre == "Sombrero de Playa" || 
                    it.nombre == "Tenis Blancos Urbanos"
                },
                tags = listOf("Fin de semana", "Fresco", "Básico")
            )
        )
    }
}
