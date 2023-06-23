package com.iamsmk.fynd.src.models.providers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.iamsmk.fynd.src.models.ProductDetails
import com.iamsmk.fynd.src.utils.retrofit.FyndAPI
import com.iamsmk.fynd.src.utils.retrofit.FyndAPIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductsProvider {
    private val fyndAPI = FyndAPIClient.getInstance().create(FyndAPI::class.java)

    fun populateProductList() : MutableLiveData<ArrayList<ProductDetails>>{
        val productsList = MutableLiveData<ArrayList<ProductDetails>>()
        val getProductsCall: Call<ArrayList<ProductDetails>> = fyndAPI.getProducts()
        getProductsCall.enqueue(object : Callback<ArrayList<ProductDetails>?> {
            override fun onResponse(
                call: Call<ArrayList<ProductDetails>?>,
                response: Response<ArrayList<ProductDetails>?>
            ) {
                productsList.postValue(response.body())
            }

            override fun onFailure(call: Call<ArrayList<ProductDetails>?>, t: Throwable) {
                Log.e("APIError", "Got error : " + t.localizedMessage)
            }
        })
        return productsList
    }
}