package com.example.myapplication.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.MainActivity
import com.example.myapplication.ProduitModel
import com.example.myapplication.ProduitPopup
import com.example.myapplication.ProduitRepository
import com.example.myapplication.R



class produit_adapteur(
    val context:MainActivity,
    private val produitList: List<ProduitModel>,
    private val layoutId :Int) :RecyclerView.Adapter<produit_adapteur.viewholder>(){

//boite pour ranger tous les composants a controler
   class viewholder(view:View) :RecyclerView.ViewHolder(view){
       val produitImage =view.findViewById<ImageView>(R.id.image_item)
       val produitName:TextView? =view.findViewById(R.id.name_item)
       val produitDescription:TextView? =view.findViewById(R.id.description_item)
       val starIcon = view.findViewById<ImageView>(R.id.star_icon)
   }
//kaykhlik t injecter layout l adapteur
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
      val view = LayoutInflater
         .from(parent.context)
         .inflate(layoutId,parent,false)

      return viewholder(view)
   }

   override fun getItemCount(): Int{

      return produitList.size
   }

   override fun onBindViewHolder(holder: viewholder, position: Int) {
     //recuperer les informations du produit
       val  currentProduit = produitList[position]

       //recuperer le repository
       val repo = ProduitRepository()

    //utiliser glide pour recuperer les image a partir du lien
       Glide.with(context).load(Uri.parse(currentProduit.imageUrl)).into(holder.produitImage)

       //mettre a jour le nom du produit
       holder.produitName?.text =currentProduit.name

       //mettre a jour la description du produit
       holder.produitDescription?.text =currentProduit.description

       //verifier est ce que le produit est like
       if (currentProduit.liked){
           holder.starIcon.setImageResource(R.drawable.ic_like)
       }
       else{
           holder.starIcon.setImageResource(R.drawable.ic_unlike)
       }
       // rajouter une interaction sur cette etoile
       holder.starIcon.setOnClickListener{
           //inverser si le boutton est like ou non
           currentProduit.liked = !currentProduit.liked

           //mettre a jour l objet produit
           repo.updateProduit(currentProduit)

       }
       //interaction lors du click sur le produit
       holder.itemView.setOnClickListener{
           //afficher popup
           ProduitPopup(this, currentProduit).show()
       }
   }
}