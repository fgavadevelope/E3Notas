package com.ejercicio3notas.notas

import com.ejercicio3notas.R
import android.content.Context
import androidx.core.content.ContextCompat


sealed  class TaskCategory(var isSelected:Boolean=true) {
    object Personal:TaskCategory()
    object Business:TaskCategory()
    object Others:TaskCategory()
    companion object {
        // Método estático para convertir un String en un TaskCategory
        fun fromString(value: String?): TaskCategory {
            return when (value) {
                "Personal" -> Personal
                "Business" -> Business
                "Others" -> Others
                else -> Personal // Retorna null si no se encuentra el valor
            }
        }
    }


    fun toString(context: Context):String{
       return when (this){
            Business -> context.getString(R.string.business)
            Others -> context.getString(R.string.other)
            Personal -> context.getString(R.string.personal)
        }
    }
    fun getColor(context: Context):Int{
        return when (this){
            Business -> ContextCompat.getColor(context,R.color.todo_business_category)
            Others -> ContextCompat.getColor(context,R.color.todo_other_category)
            Personal -> ContextCompat.getColor(context,R.color.todo_personal_category)
        }
    }
}