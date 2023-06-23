package com.iamsmk.fynd.src.models.providers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.iamsmk.fynd.src.models.AddProduct
import com.iamsmk.fynd.src.utils.retrofit.FyndAPI
import com.iamsmk.fynd.src.utils.retrofit.FyndAPIClient
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class AddProductHelper {
    private val fyndAPI = FyndAPIClient.getInstance().create(FyndAPI::class.java)

    fun addProduct(
        product_name: String?,
        product_type: String?,
        product_price: String?,
        product_tax: String?,
        product_image: File?
    ): MutableLiveData<AddProduct> {
        val addProductResponse = MutableLiveData<AddProduct>()
        val productImageBody: MultipartBody.Part? = if (product_image != null) {
            val requestFrontFile: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), product_image)
            MultipartBody.Part.createFormData(
                "files[]",
                product_image.name,
                requestFrontFile
            )
        } else {
            null
        }

        val productName: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), product_name ?: "")
        val productType: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), product_type ?: "")
        val productPrice: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), product_price ?: "")
        val productTax: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), product_tax ?: "")

        val addProductCall: Call<AddProduct> =
            fyndAPI.addProduct(
                productName,
                productType,
                productPrice,
                productTax,
                productImageBody
            )
        addProductCall.enqueue(object : Callback<AddProduct?> {
            override fun onResponse(
                call: Call<AddProduct?>,
                response: Response<AddProduct?>
            ) {
                addProductResponse.postValue(
                    response.body()
                )
            }

            override fun onFailure(call: Call<AddProduct?>, t: Throwable) {
                Log.e("TAG", "Got error : " + t.localizedMessage)
            }
        })
        return addProductResponse
    }
}