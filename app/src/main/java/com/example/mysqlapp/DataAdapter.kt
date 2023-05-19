package com.example.mysqlapp

import android.content.ClipData.Item
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mysqlapp.databinding.DataDisplayItemlayoutBinding

class DataAdapter(val context: Context, val dataList: ArrayList<MyModel>):RecyclerView.Adapter<DataAdapter.MyView>() {

    inner class MyView( val itemLayoutBinding: DataDisplayItemlayoutBinding):RecyclerView.ViewHolder(itemLayoutBinding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.MyView {
        return MyView(DataDisplayItemlayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val item = dataList[position]
        holder.itemLayoutBinding.textViewDataUserID.text = item.Id.toString()
        holder.itemLayoutBinding.textViewDataUserName.text = item.Name
        holder.itemLayoutBinding.textViewDataUserAge.text = item.Age.toString()

        holder.itemLayoutBinding.updateDataBtn.setOnClickListener {
            if (context is MainActivity) {
                context.updateRecordDialog(item)
            }
        }


            dataList?.let {

            }


        holder.itemLayoutBinding.deleteDataBtn.setOnClickListener {
            if (context is MainActivity) {
                context.deleteRecordDialog(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}