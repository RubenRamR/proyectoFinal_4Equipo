package ramirez.ruben.closetvirtual.datastore

import ramirez.ruben.closetvirtual.R

// mock para los Outfits
data class Outfit(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val fecha: String,
    val imagenRes: Int
)

// datos mock para que se vea en el calendario
val mockOutfits = listOf(
    Outfit(
        1,
        "Noche de Gala",
        "Vestido largo seda, tacones plata, collar perlas",
        "11/04/26",
        R.mipmap.outfit_gala
    ),

    Outfit(
        2,
        "Gym Flow",
        "Leggings negros, top deportivo, tenis running",
        "10/04/26",
        R.mipmap.outfit_gym
    ),

    Outfit(
        3,
        "Casual Friday",
        "shorts, playera colorida, tennis vans",
        "09/04/26",
        R.mipmap.outfit_friday
    )
)
