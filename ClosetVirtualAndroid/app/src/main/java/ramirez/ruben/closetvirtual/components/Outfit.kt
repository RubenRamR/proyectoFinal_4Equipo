package ramirez.ruben.closetvirtual.components

import ramirez.ruben.closetvirtual.datastore.Prenda

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