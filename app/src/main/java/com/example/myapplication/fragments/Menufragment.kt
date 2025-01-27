package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.ProduitRepository.Singleton.produitList
import com.example.myapplication.R
import com.example.myapplication.adapter.ProduitItemDecoration
import com.example.myapplication.adapter.produit_adapteur

class Menufragment(
    private val context: MainActivity
) : Fragment(){
    //instruction de la creation de la view pour injecter mon  layout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_menu , container , false)

//recuperer mon recyclerview (?.-------.)
        val menuRecyclerView = view?.findViewById<RecyclerView>(R.id.menu_recycler_list)
        menuRecyclerView?.adapter = produit_adapteur(context, produitList, R.layout.item_vertical_produit)
        menuRecyclerView?.layoutManager = LinearLayoutManager(context)
        menuRecyclerView?.addItemDecoration(ProduitItemDecoration())
        return view
    }
}
