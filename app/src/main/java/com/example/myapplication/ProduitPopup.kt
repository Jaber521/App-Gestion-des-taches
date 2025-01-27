package com.example.myapplication

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.adapter.produit_adapteur

class ProduitPopup(
    private  val adapter: produit_adapteur,
    private val currentProduit: ProduitModel

) :Dialog(adapter.context) {
//code qui gere la popup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //injecter layout dyalk li hwa l popup
        setContentView(R.layout.popup_produit_details)
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
        setupStarButton()
    }
//derna fonction bach nb9aw n3eyto liha te7t bla ma n3awdo
    private  fun updateStar(button: ImageView){
        if (currentProduit.liked){
            button.setImageResource(R.drawable.ic_like)
        }
        else{
            button.setImageResource(R.drawable.ic_unlike)
        }
    }
    private fun setupStarButton() {
        //recuperer
        val starButton = findViewById<ImageView>(R.id.star_button)
        updateStar(starButton)

        //interaction
        starButton.setOnClickListener {
            currentProduit.liked = !currentProduit.liked
            val repo = ProduitRepository()
            repo.updateProduit(currentProduit)
            updateStar(starButton)
        }
    }

    private fun setupDeleteButton() {
     findViewById<ImageView>(R.id.delete_produit).setOnClickListener {
         //delete
         val  repo = ProduitRepository()
         repo.deleteProduit(currentProduit)
         dismiss()
     }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener{
            //fermer la fenetre popup
            dismiss()
        }
    }

    private fun setupComponents() {
        //actualiser l image du produit
val produitImage = findViewById<ImageView>(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(currentProduit.imageUrl)).into(produitImage)

        //actualiser le nom di produit
        findViewById<TextView>(R.id.popup_produit_name).text = currentProduit.name

        //actualiser la description
        findViewById<TextView>(R.id.popup_description_sutitle).text = currentProduit.description

        //actualiser le prix
        findViewById<TextView>(R.id.popup_prix_sutitle).text = currentProduit.importance
    }

}