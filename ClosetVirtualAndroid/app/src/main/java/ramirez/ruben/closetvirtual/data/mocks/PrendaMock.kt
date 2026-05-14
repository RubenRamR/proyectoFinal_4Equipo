package ramirez.ruben.closetvirtual.data.mocks


// Clase mock para que funcione el resto del registro, por ahora (pues no tenemos backend aun)

// Lista de las categorias que tenemos (por ahora?)
val categorias = listOf("Top", "Bottom", "Outerwear", "Bodysuit", "Zapatos", "Accesorios")

data class PrendaMock(
    val id: Int,
    val nombre: String,
    val marca: String,
    val temporada: String,
    val categoria: String,
    val color: Long
)

// Lista estatica para poder visualizar las prendas (mock)
val prendasMock = listOf(
    PrendaMock(1, "Camisa de Lino Cuello Mao", "Cuidado con el Perro", "Verano","Top", 0xFFFFFFFF),
    PrendaMock(2, "Tank Top Stringer","Gymshark","Verano", "Top", 0xFF808080),
    PrendaMock(3, "Pantalón Cargo Oversize Paracaídas","Pull&Bear","Primavera", "Bottom", 0xFF000000),
    PrendaMock(4, "Jeans Corte Recto Lavado Claro","Levi's","Primavera", "Bottom", 0xFFADD8E6),
    PrendaMock(3, "Sobrecamisa de Cuadros","Zara","Invierno", "Top", 0xFF000000),
    PrendaMock(4, "Falda fina Lila","Shein","Verano", "Bottom", 0xFFE6E6FA)
)