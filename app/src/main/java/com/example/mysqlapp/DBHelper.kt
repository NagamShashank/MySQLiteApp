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

    private val createTable = "CREATE TABLE $TABLENAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME VARCHAR(256),$COL_AGE INTEGER )"


    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLENAME")
        db?.execSQL(createTable)
    }

//    fun insertData(UserName : String, UserAge : Int) : Long{
//
//        val db2 = this.writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put(COL_NAME,UserName)
//        contentValues.put(COL_AGE,UserAge)
//
//        val success = db2.insert(TABLENAME,null,contentValues)
//        db2.close()
//        return success
//    }

    fun insertData(model : MyModel) : Long{

        val db2 = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME,model.Name)
        contentValues.put(COL_AGE,model.Age)

        val success = db2.insert(TABLENAME,null,contentValues)
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

}