package com.example.myapplication


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.fragments.AddTacheFragment
import com.example.myapplication.fragments.HomeFragment
import com.example.myapplication.fragments.Menufragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(HomeFragment(this), R.string.home_page_title)

        //importer la bottomnavigationview
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener{
            when(it.itemId)
                {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_page -> {
                    loadFragment(Menufragment(this), R.string.menu_page_title)
                    return@setOnNavigationItemSelectedListener true
            }
                //la 3eme page pas encore faite
                R.id.add_page -> {
                    loadFragment(AddTacheFragment(this),R.string.add_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }

            }
    }

      private fun loadFragment(fragment: Fragment, string: Int) {
        //charger notre  repository
        val repo = ProduitRepository()

          //actualiser le titre de la page
          findViewById<TextView>(R.id.page_title).text = resources.getString(string)

        //mettre a jour la liste des produits
          repo.updateData{
            //Injecter le fragment dans notre boite (fragment_home_page)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

      }

    }


