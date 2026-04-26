package ramirez.ruben.closetvirtual.data

import ramirez.ruben.closetvirtual.R
import ramirez.ruben.closetvirtual.data.Prenda

// repositorio solo para tener datos para mostrar en pantalla
object PrendaRepository {
    val packageName = "ramirez.ruben.closetvirtual"
    
    val todasLasPrendas: List<Prenda> = listOf(
        Prenda(
            nombre = "Tacones Elegantes",
            marca = "Steve Madden",
            imagenUri = "android.resource://$packageName/${R.drawable.tacones}",
            categoria = "Zapatos",
            color = "Negro",
            temporada = "Invierno",
            formalidad = "Formal",
            tags = listOf("Gala", "Noche", "Elegante")
        ),
        Prenda(
            nombre = "Sombrero de Playa",
            marca = "Sun & Sand",
            imagenUri = "android.resource://$packageName/${R.drawable.sombrero}",
            categoria = "Accesorios",
            color = "Café",
            temporada = "Verano",
            formalidad = "Casual",
            tags = listOf("Playa", "Sol", "Vacaciones")
        ),
        Prenda(
            nombre = "Blusa Rosa Casual",
            marca = "H&M",
            imagenUri = "android.resource://$packageName/${R.drawable.blusa_rosa}",
            categoria = "Top",
            color = "Rosa",
            temporada = "Primavera",
            formalidad = "Casual",
            tags = listOf("Básico", "Color", "Fresco")
        ),
        Prenda(
            nombre = "Bolsa de Mano Negra",
            marca = "Coach",
            imagenUri = "android.resource://$packageName/${R.drawable.bolsa_negra}",
            categoria = "Accesorios",
            color = "Negro",
            temporada = "Todas las temporadas",
            formalidad = "Formal",
            tags = listOf("Cuero", "Oficina", "Esencial")
        ),
        Prenda(
            nombre = "Blusa Formal Blanca",
            marca = "Zara",
            imagenUri = "android.resource://$packageName/${R.drawable.blusa_formal}",
            categoria = "Top",
            color = "Blanco",
            temporada = "Todas las temporadas",
            formalidad = "Formal",
            tags = listOf("Trabajo", "Elegante", "Entrevista")
        ),
        Prenda(
            nombre = "Tenis Blancos Urbanos",
            marca = "Nike",
            imagenUri = "android.resource://$packageName/${R.drawable.tenis_blancos}",
            categoria = "Zapatos",
            color = "Blanco",
            temporada = "Todas las temporadas",
            formalidad = "Casual",
            tags = listOf("Urbano", "Cómodo", "Deporte")
        ),
        Prenda(
            nombre = "Blusa Deportiva Fitness",
            marca = "Adidas",
            imagenUri = "android.resource://$packageName/${R.drawable.blusa_deportiva}",
            categoria = "Top",
            color = "Gris",
            temporada = "Todas las temporadas",
            formalidad = "Deportivo",
            tags = listOf("Gym", "Entrenamiento", "Running")
        ),
        Prenda(
            nombre = "Gorra Deportiva Negra",
            marca = "Puma",
            imagenUri = "android.resource://$packageName/${R.drawable.gorra_deportiva}",
            categoria = "Accesorios",
            color = "Negro",
            temporada = "Verano",
            formalidad = "Deportivo",
            tags = listOf("Sport", "Sol", "Outdoor")
        ),
        Prenda(
            nombre = "Pantalón de Vestir Formal",
            marca = "Dockers",
            imagenUri = "android.resource://$packageName/${R.drawable.pantalon_formal}",
            categoria = "Bottom",
            color = "Negro",
            temporada = "Invierno",
            formalidad = "Formal",
            tags = listOf("Oficina", "Clásico", "Venta")
        ),
        Prenda(
            nombre = "Short Deportivo",
            marca = "Under Armour",
            imagenUri = "android.resource://$packageName/${R.drawable.short_deportivo}",
            categoria = "Bottom",
            color = "Azul",
            temporada = "Verano",
            formalidad = "Deportivo",
            tags = listOf("Running", "Gimnasio", "Short")
        ),
        Prenda(
            nombre = "Chaqueta Vinipiel",
            marca = "Pull&Bear",
            imagenUri = "android.resource://$packageName/${R.drawable.chaqueta_vinipiel}",
            categoria = "Outerwear",
            color = "Negro",
            temporada = "Otoño",
            formalidad = "Casual",
            tags = listOf("Noche", "Estilo", "Rock")
        ),
        Prenda(
            nombre = "Pantalón Vinipiel Slim",
            marca = "Bershka",
            imagenUri = "android.resource://$packageName/${R.drawable.pantalon_vinipiel}",
            categoria = "Bottom",
            color = "Negro",
            temporada = "Otoño",
            formalidad = "Casual",
            tags = listOf("Noche", "Fashion", "Tendencia")
        ),
        Prenda(
            nombre = "Sombrero Vinipiel Black",
            marca = "StreetStyle",
            imagenUri = "android.resource://$packageName/${R.drawable.sombrero_vinipiel}",
            categoria = "Accesorios",
            color = "Negro",
            temporada = "Invierno",
            formalidad = "Casual",
            tags = listOf("Único", "Moda", "Negro")
        ),
        Prenda(
            nombre = "Pantalón de Mezclilla Clásico",
            marca = "Levi's",
            imagenUri = "android.resource://$packageName/${R.drawable.pantalon_mezclilla}",
            categoria = "Bottom",
            color = "Azul",
            temporada = "Todas las temporadas",
            formalidad = "Casual",
            tags = listOf("Mezclilla", "Básico", "Denim")
        )
    )
}
