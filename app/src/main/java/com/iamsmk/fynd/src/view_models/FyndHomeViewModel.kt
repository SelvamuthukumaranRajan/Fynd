package com.iamsmk.fynd.src.view_models

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iamsmk.fynd.src.models.ProductDetails
import com.iamsmk.fynd.src.models.providers.ProductsProvider

class FyndHomeViewModel : ViewModel() {
    private val productsList = ProductsProvider().populateProductList()

    fun getProductsList(): LiveData<ArrayList<ProductDetails>> {
        return productsList
    }

    fun getGreetingMessage(): LiveData<String> {
        val calendar = Calendar.getInstance()

        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> MutableLiveData("Good Morning")
            in 12..15 -> MutableLiveData("Good Afternoon")
            in 16..20 -> MutableLiveData("Good Evening")
            in 21..23 -> MutableLiveData("Good Night")
            else -> MutableLiveData("Hello")
        }
    }

    fun filterList(newText: String?): LiveData<ArrayList<ProductDetails>> {
        val productsList = productsList.value
        val filteredList = ArrayList<ProductDetails>()
        if (newText != null) {
            if (productsList != null) {
                for (item in productsList) {
                    if (item.productName?.contains(
                            newText,
                            ignoreCase = true
                        ) == true || item.productType?.contains(newText, ignoreCase = true) == true
                    ) {
                        filteredList.add(item)
                    }
                }
            }
        }
        return MutableLiveData(filteredList)
    }

}
