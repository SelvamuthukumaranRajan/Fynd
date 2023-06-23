package com.iamsmk.fynd.src.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.iamsmk.fynd.src.models.AddProduct
import com.iamsmk.fynd.src.models.providers.AddProductHelper
import java.io.File

class FyndAddProductViewModel : ViewModel() {
    fun addProducts(
        productName: String?,
        product_type: String?,
        product_price: String?,
        product_tax: String?,
        product_image: File?
    ): LiveData<AddProduct> {
        return AddProductHelper().addProduct(
            productName,
            product_type,
            product_price,
            product_tax,
            product_image
        )
    }

}