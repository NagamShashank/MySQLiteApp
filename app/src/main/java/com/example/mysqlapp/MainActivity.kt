package com.example.mysqlapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
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
            val mydata = MyModel(id, entered_Name, newage)

            val status = dbHelper.insertData(mydata)
            if (status > -1){
                Toast.makeText(this,"User Added",Toast.LENGTH_SHORT).show()
                //getData()
                binding.NameEdittext.setText("")
                binding.AgeEdittext.setText("")
            }else{
                Toast.makeText(this,"User Not Added",Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun updateRecordDialog(updateRecModel : MyModel){
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val updateDialog = inflater.inflate(R.layout.dialog_update,null)
        builder.setView(updateDialog)
        val alertdialog =  builder.create()
        val name = updateDialog.findViewById<EditText>(R.id.updateNameEdiText)
        val age =  updateDialog.findViewById<EditText>(R.id.updateAgeEdiText)
        val updateBtn = updateDialog.findViewById<TextView>(R.id.UpdateDataBtn)
        val cancelBtn = updateDialog.findViewById<TextView>(R.id.CancelBtn)

        updateBtn.setOnClickListener {
            val StringName = name.text.toString()
            val IntAge = age.text.toString()
            if (StringName.isEmpty() && IntAge.isEmpty()) {
                Toast.makeText(this, "Name and Age Cannot Be Blank", Toast.LENGTH_SHORT).show()
            } else {
                val newIntAge = IntAge.toInt()
                //val updateid = MyModel.getAutoId()
                val myUpdateData = MyModel(updateRecModel.Id, StringName, newIntAge)
                val updateStatus = dbHelper.getUpdatedData(myUpdateData)

                if (updateStatus > -1) {
                    Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show()
                    getData()
                    name.setText("")
                    age.setText("")
                    alertdialog.dismiss()
                } else {
                    Toast.makeText(this, "Data not Updated", Toast.LENGTH_SHORT).show()
                }

            }
        }

        cancelBtn.setOnClickListener {
            alertdialog.dismiss()
        }

        alertdialog.show()



    }


    fun deleteRecordDialog(deleteRecModel : MyModel){
        val deleteBuilder = AlertDialog.Builder(this)
        val delAlertDialog = deleteBuilder.create()
        deleteBuilder.setTitle("Delete Record")
        deleteBuilder.setMessage("Are you should you want to delete \n Name : ${deleteRecModel.Name} \n Age : ${deleteRecModel.Age}")
        deleteBuilder.setIcon(android.R.drawable.ic_dialog_alert)
        deleteBuilder.setPositiveButton("Yes"){ DialogInterface, which ->
            val myDeleteData = MyModel(deleteRecModel.Id,"",null)
            val deleteStatus = dbHelper.getDeleteData(myDeleteData)

            if(deleteStatus > -1){
                Toast.makeText(this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show()
                getData()
                delAlertDialog.dismiss()
            }else{
                Toast.makeText(this, "Fail To Delete The Data", Toast.LENGTH_SHORT).show()
            }

        }

        deleteBuilder.setNegativeButton("No"){ DialogInterface, which ->
            delAlertDialog.dismiss()
        }
        delAlertDialog.show()
    }



}