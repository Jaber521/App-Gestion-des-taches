package com.example.myapplication.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.ProduitModel
import com.example.myapplication.ProduitRepository.Singleton.produitList
import com.example.myapplication.R
import com.example.myapplication.adapter.ProduitItemDecoration
import com.example.myapplication.adapter.produit_adapteur

class HomeFragment(
    private val context:MainActivity
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //recuperer le recyclerview
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter =produit_adapteur(context, produitList.filter { !it.liked }, R.layout.item_horizontal_produit)
      //recuperer le second recyclerview
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter =produit_adapteur(context, produitList.filter { it.liked }, R.layout.item_vertical_produit)
        verticalRecyclerView.addItemDecoration(ProduitItemDecoration())
        return view
    }
}