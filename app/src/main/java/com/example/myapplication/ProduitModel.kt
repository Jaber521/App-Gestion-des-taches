package com.example.myapplication

class ProduitModel(
    val id: String = "produit0",
    val name: String = "Sushi",
    val description: String = "Petite description",
    val imageUrl: String = "http://graven.yt/plante.jpg",
    val importance: String = "Faible",
    var liked: Boolean = false
)


data class CartItem(
    val product: ProduitModel,
    var quantity: Int
)



