package com.example.mysqlapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mysqlapp.databinding.DataDisplayItemlayoutBinding

class DataAdapter(val context: Context, val dataList: ArrayList<MyModel>):RecyclerView.Adapter<DataAdapter.MyView>() {

    inner class MyView( val itemLayoutBinding: DataDisplayItemlayoutBinding):RecyclerView.ViewHolder(itemLayoutBinding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.MyView {
        return MyView(DataDisplayItemlayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.itemLayoutBinding.textViewDataUserID.text = dataList[position].Id.toString()
        holder.itemLayoutBinding.textViewDataUserName.text = dataList[position].Name
        holder.itemLayoutBinding.textViewDataUserAge.text = dataList[position].Age.toString()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}