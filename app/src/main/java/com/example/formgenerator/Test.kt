package com.example.formgenerator

import com.google.gson.Gson
import java.lang.Exception

fun main() {
    val name = "ALIREZA"
    val dataPath = "personal.identity.name"
    val dataPathList = dataPath.split(".")
    println("dataPath list:")
    println(dataPathList)
    var tempPair = mapOf<String, Any>()
    dataPathList.reversed().forEach {
        tempPair = if (dataPathList.indexOf(it) == dataPathList.size - 1) {
            mapOf(it to name)
        } else {
            mapOf(it to tempPair)
        }
    }


    val jsonString = Gson().toJson(tempPair).toString()
    println("json string from map:")
    println(jsonString)


    val mapOfJsonString = Gson().fromJson(jsonString, Map::class.java)
    println("map from json string")
    println(mapOfJsonString)

    var widgetValue: Any = ""
    var tempMap = mapOfJsonString
    dataPathList.forEach {
        if (dataPathList.indexOf(it) == dataPathList.size - 1) {
            widgetValue = tempMap[it] ?: ""
        } else {
            try {
                tempMap = tempMap[it] as Map<*, *>
            }catch (e:Exception){
                println(e.message)
            }
        }
    }

    println("Widget value:")
    println(widgetValue.toString())

}