package fr.iamludal.cryptac.model

data class CryptoBasic(
    val cryptoId: Int,
    val cmcRank: Int,
    val name: String,
    val symbol: String,
    val logo: String,
    val price: Double,
    val percentChange1h: Float,
    var isFavorite: Boolean
)
