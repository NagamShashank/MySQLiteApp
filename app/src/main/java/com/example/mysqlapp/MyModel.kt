package com.example.mysqlapp

import kotlin.random.Random

data class MyModel(
    val Id: Int? = getAutoId(),
    val Name: String,
    val Age: Int?
) {

    companion object{
        fun getAutoId():Int{
            val random = Random
            return random.nextInt(100)
        }
    }
}
