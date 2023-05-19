package com.example.mysqlapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DATABASENAME = "MY DATABASE"
val TABLENAME = "Users"
val COL_NAME = "name"
val COL_AGE = "age"
val COL_ID = "id"


class DBHelper(var context : Context) : SQLiteOpenHelper(context, DATABASENAME,null,1) {

    private val createTable = "CREATE TABLE $TABLENAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME  VARCHAR(256),$COL_AGE INTEGER )"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLENAME")
        db?.execSQL(createTable)
    }

    fun insertData(model : MyModel) : Long{

        val db2 = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME,model.Name)
        contentValues.put(COL_AGE,model.Age)

       // val checkQuery = "SELECT * FROM $TABLENAME WHERE $COL_NAME "
        val success = db2.insert(TABLENAME,null,contentValues)
        //val success = db2.insertWithOnConflict(TABLENAME,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE)
        db2.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllUsers() : ArrayList<MyModel> {
        val userList : ArrayList<MyModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLENAME"
        val db = this.readableDatabase

        val cursor : Cursor?

        try {
            cursor = db.rawQuery(selectQuery,null)

        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id : Int
        var name : String
        var age : Int

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                age = cursor.getInt(cursor.getColumnIndex("age"))

                val model = MyModel(id, name , age)
                userList.add(model)
            }while(cursor.moveToNext())
        }

        return userList
    }


    fun getUpdatedData(updateModel : MyModel):Int{
        val updateDB = this.writableDatabase
        val ctValues = ContentValues()
        ctValues.put(COL_NAME,updateModel.Name)
        ctValues.put(COL_AGE,updateModel.Age)
        val updateSuccess = updateDB.update(TABLENAME,ctValues, COL_ID + "=" +updateModel.Id,null)
        updateDB.close()
        return updateSuccess
    }


    fun getDeleteData(deleteModel : MyModel):Int{
        val deleteDB = this.writableDatabase
        val deleteCTValues = ContentValues()
        deleteCTValues.put(COL_NAME,deleteModel.Name)
        deleteCTValues.put(COL_AGE,deleteModel.Age)
        val deleteSuccess = deleteDB.delete(TABLENAME, COL_ID + "=" + deleteModel.Id,null)
        deleteDB.close()
        return deleteSuccess
    }
}