package com.iamsmk.fynd.src.utils.retrofit

import com.iamsmk.fynd.src.models.AddProduct
import com.iamsmk.fynd.src.models.ProductDetails
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface FyndAPI {
    @GET("get")
    fun getProducts(): Call<ArrayList<ProductDetails>>

    @Multipart
    @POST("add")
    fun addProduct(
        @Part("product_name") product_name: RequestBody,
        @Part("product_type") product_type: RequestBody,
        @Part("price") product_price: RequestBody,
        @Part("tax") product_tax: RequestBody,
        @Part product_image: MultipartBody.Part?,
    ): Call<AddProduct>

}