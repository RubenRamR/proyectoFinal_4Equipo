package ramirez.ruben.closetvirtual.datastore

import ramirez.ruben.closetvirtual.R


// Clase mock para que funcione el resto del registro, por ahora (pues no tenemos backend aun)

// Lista de las categorias que tenemos (por ahora?)
val categorias = listOf("Top", "Bottom", "Outerwear", "Bodysuit", "Zapatos", "Accesorios")

data class PrendaMock(
    val id: Int,
    val nombre: String,
    val marca: String,
    val temporada: String,
    val categoria: String,
    val imagenResId: Int
)

// Lista estatica para poder visualizar las prendas (mock)
val prendasMock = listOf(
    PrendaMock(1, "Camisa de Lino Cuello Mao", "Cuidado con el Perro", "Verano","Top", R.mipmap.camisa_cuidado_perro),
    PrendaMock(2, "Tank Top Stringer","Gymshark","Verano", "Top", R.mipmap.tank_top_rojo),
    PrendaMock(3, "Pantalón Cargo Oversize Paracaídas","Pull&Bear","Primavera", "Bottom", R.mipmap.pantalon_cargo),
    PrendaMock(4, "Jeans Corte Recto Lavado Claro","Levi's","Primavera", "Bottom", R.mipmap.jean_corte_levis),
    PrendaMock(3, "Sobrecamisa de Cuadros","Zara","Invierno", "Top", R.mipmap.sobrecamisa_zara),
    PrendaMock(4, "Falda fina Lila","Shein","Verano", "Bottom", R.mipmap.falda_fina)
)