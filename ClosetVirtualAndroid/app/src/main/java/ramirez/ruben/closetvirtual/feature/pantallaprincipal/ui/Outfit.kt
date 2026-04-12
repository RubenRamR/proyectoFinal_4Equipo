package ramirez.ruben.closetvirtual.feature.pantallaprincipal.ui

import ramirez.ruben.closetvirtual.data.Prenda

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
