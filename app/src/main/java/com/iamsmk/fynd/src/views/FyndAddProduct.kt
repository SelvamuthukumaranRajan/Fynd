package com.iamsmk.fynd.src.views

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iamsmk.fynd.R
import com.iamsmk.fynd.databinding.ActivityFyndAddProductBinding
import com.iamsmk.fynd.src.view_models.FyndAddProductViewModel
import java.io.File
import java.io.FileOutputStream


class FyndAddProduct : AppCompatActivity() {
    private lateinit var addProductBinding: ActivityFyndAddProductBinding
    private lateinit var fyndAddProductViewModel: FyndAddProductViewModel
    private var productImage: File? = null
    private val pickImage =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            if (it != null) {
                initProductImage(it)
            }
            addProductBinding.ivProductImage.setImageURI(it)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addProductBinding = ActivityFyndAddProductBinding.inflate(layoutInflater)
        setContentView(addProductBinding.root)
        fyndAddProductViewModel = ViewModelProvider(this)[FyndAddProductViewModel::class.java]

        addProductBinding.ivBack.setOnClickListener(View.OnClickListener { finish() })

        val items =
            listOf("Computers", "Electronics", "Mobiles", "Fashion", "Home", "Toys", "Books")
        val adapter = ArrayAdapter(this, R.layout.autocomplete_list_item, items)
        (addProductBinding.actProductType as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    override fun onResume() {
        super.onResume()

        addTextWatchers();
        addProductBinding.btAddProduct.setOnClickListener {
            if (sanitizeUserInput(
                    addProductBinding.etProductName.text.toString(),
                    addProductBinding.actProductType.text.toString(),
                    addProductBinding.etProductPrice.text.toString(),
                    addProductBinding.etProductTax.text.toString(),
                )
            ) {
                val addProductResponse = fyndAddProductViewModel.addProducts(
                    addProductBinding.etProductName.text.toString(),
                    addProductBinding.actProductType.text.toString(),
                    addProductBinding.etProductPrice.text.toString(),
                    addProductBinding.etProductTax.text.toString(),
                    productImage
                )
                addProductResponse.observe(this, Observer {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT)
                        .show()
                    if (it.success!!) {
                        finish()
                    }
                })
            }
        }

        addProductBinding.ibAddImage.setOnClickListener {
            pickImage.launch("image/*")
            addProductBinding.ibAddImage.visibility = View.GONE
            addProductBinding.flProductImageLayout.visibility = View.VISIBLE
        }

        addProductBinding.flProductImageLayout.setOnClickListener {
            pickImage.launch("image/*")
        }
        addProductBinding.ibReplaceImage.setOnClickListener {
            pickImage.launch("image/*")
        }
        addProductBinding.ivProductImage.setOnClickListener {
            pickImage.launch("image/*")
        }
    }

    private fun initProductImage(uri: Uri) {
        val fileDirectory = applicationContext.filesDir
        productImage = File(fileDirectory, "image.png")

        contentResolver.openInputStream(uri).use {
            val outputStream = FileOutputStream(productImage)
            it!!.copyTo(outputStream)
        }
    }

    private fun addTextWatchers() {
        addProductBinding.etProductName.addTextChangedListener {
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    addProductBinding.etProductName.error = null
                }
            }
        }
        addProductBinding.etProductPrice.addTextChangedListener {
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    addProductBinding.etProductPrice.error = null
                }
            }
        }
        addProductBinding.etProductTax.addTextChangedListener {
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    addProductBinding.etProductTax.error = null
                }
            }
        }
    }

    private fun sanitizeUserInput(
        productName: String?,
        productType: String?,
        productPrice: String?,
        product_tax: String?,
    ): Boolean {

        return if (productName.isNullOrEmpty()) {
            addProductBinding.etProductName.error = "Please enter product name"
            false
        } else if (productPrice.isNullOrEmpty()) {
            addProductBinding.etProductPrice.error = "Please enter product price"
            false
        } else if (product_tax.isNullOrEmpty()) {
            addProductBinding.etProductTax.error = "Please enter product tax"
            false
        } else if (productType.isNullOrEmpty()) {
            Toast.makeText(this, "Please select product type", Toast.LENGTH_SHORT).show();
            false
        } else {
            true
        }
    }
}