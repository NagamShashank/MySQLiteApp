package com.example.mysqlapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysqlapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var dbHelper: DBHelper
    private  var adapter : DataAdapter ? = null


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Kotlin App"
        val context = this
         dbHelper = DBHelper(context)

        binding.saveData.setOnClickListener {
            addData()
        }

        binding.ReadData.setOnClickListener{
            getData()
        }

        binding.DataRecyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun getData() {
        val List = dbHelper.getAllUsers()
        Log.e("pppp","$List")
        adapter = DataAdapter(this,List)
        binding.DataRecyclerView.adapter = adapter

    }
    private fun addData() {

        val dpList = dbHelper.getAllUsers()

        val entered_Name = binding.NameEdittext.text.toString()
        val entered_Age = binding.AgeEdittext.text.toString()

        if(entered_Name.isEmpty() && entered_Age.isEmpty()){
            Toast.makeText(this,"Please enter the all Fields",Toast.LENGTH_SHORT).show()
        } else {
            val newage = entered_Age.toInt()
            val id = MyModel.getAutoId()




            val data = MyModel(id, entered_Name, newage)
            val status = dbHelper.insertData(data)

            if (status > -1){
                Toast.makeText(this,"User Added",Toast.LENGTH_SHORT).show()
                getData()
                binding.NameEdittext.setText("")
                binding.AgeEdittext.setText("")
            }else{
                Toast.makeText(this,"User Not Added",Toast.LENGTH_SHORT).show()
            }

        }
    }

}