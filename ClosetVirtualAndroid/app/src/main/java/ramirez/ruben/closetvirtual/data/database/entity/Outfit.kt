package ramirez.ruben.closetvirtual.data.database.entity

import ramirez.ruben.closetvirtual.data.mocks.Prenda

data class Outfit(
    val nombre: String = "",
    val color: String = "",
    val isEstampada: Boolean = false,
    val estilo: String = "",
    val temporada: String = "",
    val formalidad: String = "",
    val prendas: List<Prenda> = emptyList(),
    val tags: List<String> = emptyList()
)