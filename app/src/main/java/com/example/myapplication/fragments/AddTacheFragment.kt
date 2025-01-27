package com.example.myapplication.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.ProduitModel
import com.example.myapplication.ProduitRepository
import com.example.myapplication.ProduitRepository.Singleton.downloadUri
import com.example.myapplication.R
import java.util.UUID

class AddTacheFragment(
    private val context: MainActivity
):Fragment() {
    private var file :Uri? = null
    private  var uploadedImage:ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add, container,false)

        //recuperer uploaded image pour lui associer son composant
        uploadedImage = view?.findViewById(R.id.preview_image)

        //recuperer le bouton pour charger l image
        val pickupImageButton = view?.findViewById<Button>(R.id.charger_image)

        //lorsqu on clique dessus ca ouvre les images du tel
        pickupImageButton?.setOnClickListener { pickupImage()}

        //recuperer le bouton confirmer
        val  confirmButton = view?.findViewById<Button>(R.id.confirmer_button)
        confirmButton?.setOnClickListener { sendForm(view) }

        return view
    }

    private fun sendForm(view: View) {
        val repo = ProduitRepository()
        repo.uploadimage(file!!){//!! 100/100 accepter
        val nomtache = view.findViewById<EditText>(R.id.name_input).text.toString()
        val descriptiontache = view.findViewById<EditText>(R.id.description_input).text.toString()
        val importance = view.findViewById<Spinner>(R.id.importance_spinner).selectedItem.toString()
        val downloadUrl = downloadUri

        // cree un nouvel objet  Produit model
        val produit = ProduitModel(
            UUID.randomUUID().toString(),
            nomtache,
            descriptiontache,
            downloadUrl.toString(),
            importance
                )

        //envoyer au bdd
        repo.insertProduit(produit)
    }

    }


    private fun pickupImage() {
        val intent = Intent()
        intent.type ="image/"
        intent.action =Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 47 && resultCode == Activity.RESULT_OK ){

            //verifier si les donnees sont nulles
            if(data == null || data.data == null )return

            //recuperer l image selectionner
            file = data.data

            //mettre a jour l apercu dfe l image
            uploadedImage?.setImageURI(file)

      }
    }
}