package ramirez.ruben.closetvirtual.data

import java.util.UUID

data class Prenda(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val marca: String? = null,
    val imagenUri: String,
    val categoria: String,
    val color: String,
    val esEstampada: Boolean = false,
    val talla: String? = null,
    val temporada: String,
    val formalidad: String,
    val tags: List<String> = emptyList(),
    val fechaRegistro: Long = System.currentTimeMillis()
)