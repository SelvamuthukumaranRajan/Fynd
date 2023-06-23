package com.iamsmk.fynd.src.views.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.iamsmk.fynd.R
import com.iamsmk.fynd.src.models.ProductDetails


class ProductListAdapter(private var dataSet: ArrayList<ProductDetails>) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvProductName: TextView
        val tvProductType: TextView
        val tvProductPrice: TextView
        val tvProductTax: TextView
        val ivProductImage: ImageView

        init {
            tvProductName = view.findViewById(R.id.tv_productName)
            tvProductType = view.findViewById(R.id.tv_productType)
            tvProductPrice = view.findViewById(R.id.tv_productPrice)
            tvProductTax = view.findViewById(R.id.tv_productTax)
            ivProductImage = view.findViewById(R.id.iv_productImage)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.rv_product_details, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvProductName.text = dataSet[position].productName
        viewHolder.tvProductType.text = dataSet[position].productType
        val price = """$ ${dataSet[position].price.toString()}"""
        viewHolder.tvProductPrice.text = price
        viewHolder.tvProductTax.text = dataSet[position].tax.toString()


        Glide.with(viewHolder.itemView.context)
            .load(dataSet[position].image.toString())
            .apply(
                RequestOptions().override(140, 140)
                    .placeholder(R.drawable.ic_no_image)
                    .error(R.drawable.ic_no_image)
                    .transform(RoundedCorners(25))
            ).thumbnail(0.1f)
            .into(viewHolder.ivProductImage);
    }

    override fun getItemCount() = dataSet.size

    fun updateDataSet(list: LiveData<ArrayList<ProductDetails>>) {
        this.dataSet = list.value!!
        notifyDataSetChanged()
    }

}