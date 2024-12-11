package com.ejercicio3notas.notas
import com.ejercicio3notas.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class CategoriesAdapter(private val categories: List<TaskCategory>, private val onTaskSelected:(Int)->Unit) :
    RecyclerView.Adapter<CategoriesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_task_category,parent,false)
        return CategoriesHolder(view)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        holder.render(categories[position],onTaskSelected)
    }
}

class CategoriesHolder(val v: View) : RecyclerView.ViewHolder(v) {
    private val tvCategory: TextView = v.findViewById(R.id.tvCategoryName)
    private val vSeparator: View = v.findViewById(R.id.vSeparator)
    private val cvCategory: CardView = v.findViewById(R.id.cvCategory)
    fun render(taskCategory: TaskCategory, onItemSelected:(Int) -> Unit) {
        tvCategory.text = taskCategory.toString(v.context)
        vSeparator.setBackgroundColor(taskCategory.getColor(v.context))
        val color = if (taskCategory.isSelected) {
            R.color.todo_background_card
        } else {
            R.color.todo_background_disabled
        }
        cvCategory.setCardBackgroundColor(ContextCompat.getColor(tvCategory.context,color))
        itemView.setOnClickListener{onItemSelected(layoutPosition)}

    }
}