package com.iamsmk.fynd.src.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iamsmk.fynd.databinding.ActivityFyndHomeScreenBinding
import com.iamsmk.fynd.src.view_models.FyndHomeViewModel
import com.iamsmk.fynd.src.views.adaptors.ProductListAdapter


class FyndHomeScreen : AppCompatActivity() {
    private lateinit var homeScreenBinding: ActivityFyndHomeScreenBinding
    private lateinit var adapter: ProductListAdapter
    private lateinit var fyndHomeViewModel: FyndHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeScreenBinding = ActivityFyndHomeScreenBinding.inflate(layoutInflater)
        setContentView(homeScreenBinding.root)
        fyndHomeViewModel = ViewModelProvider(this)[FyndHomeViewModel::class.java]

        homeScreenBinding.fabAddProduct.setOnClickListener {
            startActivity(Intent(this, FyndAddProduct::class.java))
        }

        homeScreenBinding.rvProductList.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()

        fyndHomeViewModel.getGreetingMessage().observe(this) {
            homeScreenBinding.tvGreetingsText.text = it
        }

        fyndHomeViewModel.getProductsList().observe(this) {
            adapter = ProductListAdapter(it)
            homeScreenBinding.rvProductList.adapter = adapter
        }

        homeScreenBinding.svSearchProduct.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.updateDataSet(fyndHomeViewModel.filterList(newText))
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })
    }

    override fun onRestart() {
        super.onRestart()
        fyndHomeViewModel.getProductsList().observe(this) {
            adapter = ProductListAdapter(it)
            homeScreenBinding.rvProductList.adapter = adapter
        }
    }
}