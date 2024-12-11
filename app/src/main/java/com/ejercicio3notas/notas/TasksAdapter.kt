package com.ejercicio3notas.notas
import android.content.res.ColorStateList
import android.graphics.Paint
import com.ejercicio3notas.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class TasksAdapter(var tasks: List<Task>, private val onTaskSelected:(Int)->Unit) :
    RecyclerView.Adapter<TasksHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false)
        return TasksHolder(view)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TasksHolder, position: Int) {
        holder.render(tasks[position])
        holder.itemView.setOnClickListener{onTaskSelected(position)}
    }
}

class TasksHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val tvTask: TextView = v.findViewById(R.id.tvTask)
    private val cbTask: CheckBox = v.findViewById(R.id.cbTask)

    fun render(task: Task) {
        tvTask.text = task.nota.name
        if (task.isSelected) {
            tvTask.paintFlags = tvTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvTask.paintFlags = tvTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
        tvTask.setTextColor(task.nota.category.getColor(tvTask.context));
        cbTask.buttonTintList= ColorStateList.valueOf(task.nota.category.getColor(cbTask.context))
        cbTask.isChecked=task.isSelected

    }
}