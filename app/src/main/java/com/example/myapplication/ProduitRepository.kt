package com.example.myapplication

import android.app.usage.NetworkStats.Bucket
import android.net.Uri
import com.example.myapplication.ProduitRepository.Singleton.databaseRef
import com.example.myapplication.ProduitRepository.Singleton.downloadUri
import com.example.myapplication.ProduitRepository.Singleton.produitList
import com.example.myapplication.ProduitRepository.Singleton.storageReferance
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.UUID

class ProduitRepository {

    object Singleton{
        //donner le lien pour acceder au bucket
        private val Bucket_url: String ="gs://kaysu-sushi.appspot.com"

        //se connecter a notre epace  de stockage
        val storageReferance = FirebaseStorage.getInstance().getReferenceFromUrl(Bucket_url)
        //pour se connecter a lA REFERANCE PRODUIT
        val databaseRef = FirebaseDatabase.getInstance().getReference("produits")

        //cree une liste qui va contenir nos produit
        val produitList = arrayListOf<ProduitModel>()

        //cree le lien de l image courante
        var downloadUri: Uri? = null
    }

  fun updateData(callback: () -> Unit){
      //absorber les donnees depuis la databaseRef -----/ liste de produit
      databaseRef.addValueEventListener(object :ValueEventListener{
          override fun onDataChange(snapshot: DataSnapshot) {
              //retirer les anciens
              produitList.clear()

              //recolter la liste "produit"
              for (ds in snapshot.children){

                  //construir un objet produit
                  val produit = ds.getValue(ProduitModel::class.java)

                  //verifier est ce que le produit n est pas null
                  if (produit != null){

                      //ajouter le produit a notre list
                      produitList.add(produit)
                  }
               }
              // actioner le callback
              callback()
          }
          override fun onCancelled(error: DatabaseError) {

          }

      })
  }
    //cree une fonction pour envoier  des fichiers sur le storage
    fun uploadimage(file: Uri, callback: () -> Unit){
        //verifier  que ce fichier n"est pas null
        if (file != null) {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReferance.child(fileName)
            val uploadTask = ref.putFile(file)

            //demarrer la tache d envoi
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->

                // si il y a  eu un probleme lors de lenvoi du fichier
                if (task.isSuccessful){
                    task.exception?.let { throw it }
                }

                return@Continuation ref.downloadUrl

            }).addOnCompleteListener { task ->
                //verifier si tout va bien fonctionne
                if (task.isSuccessful){
                    //recuperer l image
                     downloadUri = task.result
                    callback()
                }
            }
        }
    }

    //mettre a jour un objet produit en bdd
    fun updateProduit(produit: ProduitModel) = databaseRef.child(produit.id).setValue(produit)

    //inserer une nouvelle tache en bdd
    fun insertProduit(produit: ProduitModel) = databaseRef.child(produit.id).setValue(produit)

    //supprimer un produit de la bdd fun
        fun deleteProduit(produit: ProduitModel) = databaseRef.child(produit.id).removeValue()




    }
