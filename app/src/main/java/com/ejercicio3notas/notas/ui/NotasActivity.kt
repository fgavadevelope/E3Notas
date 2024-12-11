package com.ejercicio3notas.notas.ui

import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.allViews
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ejercicio3notas.R
import com.ejercicio3notas.notas.CategoriesAdapter
import com.ejercicio3notas.notas.Task
import com.ejercicio3notas.notas.TaskCategory
import com.ejercicio3notas.notas.TasksAdapter
import com.ejercicio3notas.notas.data.database.NotaDatabase
import com.ejercicio3notas.notas.data.modelo.Nota
import com.ejercicio3notas.notas.data.modelo.NotaDao
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch


class NotasActivity : AppCompatActivity() {
    private val categories =
        listOf<TaskCategory>(TaskCategory.Personal, TaskCategory.Business, TaskCategory.Others)
    private var tasks = mutableListOf<Task>()

    private lateinit var rvCategory: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var rvTasks: RecyclerView
    private lateinit var fabAddTask: FloatingActionButton
    private lateinit var fabDelTasks: FloatingActionButton
    private lateinit var dialog: Dialog
    private lateinit var notaDao: NotaDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)
        val db = NotaDatabase.getDatabase(this)
        notaDao = db.notaDao()
        lifecycleScope.launch {
            val todasLasNotas = notaDao.getAll()
            todasLasNotas.collect { listadeNotas ->
                tasks.clear()
                listadeNotas.map { nota ->
                    tasks.add(Task(nota))
                }
                updateTasks()
            }

        }
        initComponents()
        initUI()
        initListeners()
    }


    private fun initComponents() {
        rvCategory = findViewById(R.id.rvCategorias)
        rvTasks = findViewById(R.id.rvTasks)
        fabAddTask = findViewById(R.id.fabAddTask)
        fabDelTasks= findViewById(R.id.fabDelTasks)
        creaDialog()

    }


    private fun initUI() {
        categoriesAdapter = CategoriesAdapter(categories) { onCategorySelected(it) }
        tasksAdapter = TasksAdapter(tasks) { onTaskSelected(it) }
        rvCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTasks.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvCategory.adapter = categoriesAdapter
        rvTasks.adapter = tasksAdapter
    }


    private fun onCategorySelected(it: Int) {
       // categories.forEachIndexed{index,cate ->cate.isSelected=(index==it)}
        categories[it].isSelected=!categories[it].isSelected
        rvTasks.allViews
        categoriesAdapter.notifyDataSetChanged()
        updateTasks()

    }

    private fun onTaskSelected(position:Int){
    tasks[position].isSelected=!tasks[position].isSelected
    updateTasks()
}
    private fun initListeners() {
        fabAddTask.setOnClickListener { muestraDialog() }
        fabDelTasks.setOnClickListener { AlertDialog.Builder(this)
            .setTitle("Confirmación")
            .setMessage("¿Estás seguro de que deseas eliminar todas las notas seleccionadas?")
            .setPositiveButton("Sí") { _, _ -> deleteTasks() }
            .setNegativeButton("No", null)
            .show()
        }


    }
    fun deleteTasks() {
        lifecycleScope.launch {
            tasks.filter { it.isSelected }.forEach {taskx->notaDao.delete(taskx.nota)} }
        Toast.makeText(this, "Tareas eliminadas", Toast.LENGTH_SHORT).show()
    }
    private fun muestraDialog() {
        val etTask: EditText = dialog.findViewById(R.id.etTask)
        val rb1:RadioButton=dialog.findViewById(0)
        rb1.isChecked=true
        etTask.setText("")
        dialog.show()
    }

    private fun addTask() {
        val rgCategories: RadioGroup = dialog.findViewById(R.id.rgCategories)
        val etTask: EditText = dialog.findViewById(R.id.etTask)
        if (etTask.text.toString().isEmpty()) return
        val newTask = Task(Nota(categories[rgCategories.checkedRadioButtonId], etTask.text.toString()))
        tasks.add(newTask)
        lifecycleScope.launch {
            notaDao.insert(newTask.nota)
            System.out.println("Nota insertada correctamente")
        }

        dialog.hide()
    }

    private fun creaDialog() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_task)
        val btnAddTask: Button = dialog.findViewById(R.id.btnAddTask)
        dialog.findViewById<EditText>(R.id.etTask).requestFocus()
        btnAddTask.setOnClickListener { addTask() }
        val rgCategories: RadioGroup = dialog.findViewById(R.id.rgCategories)

        categories.forEachIndexed { indice, cate ->
            val rb1 = RadioButton(dialog.context)
            rb1.id = indice
            rb1.text = cate.toString(this)
            rb1.setTextColor(ContextCompat.getColor(this, R.color.white))
            rb1.buttonTintList = ColorStateList.valueOf(cate.getColor(this))
            rgCategories.addView(rb1)
        }
//        val rb1: RadioButton = dialog.findViewById(1)
//        rb1.isChecked=true

    }
private fun updateTasks(){
    val categoriesSelected:List<TaskCategory> = categories.filter { it.isSelected }
    val selectedTasks:List<Task> = tasks.filter { categoriesSelected.contains(it.nota.category) }
    fabDelTasks.visibility=if(selectedTasks.any { it.isSelected } ) Button.VISIBLE else Button.INVISIBLE
    tasksAdapter.tasks=selectedTasks
    tasksAdapter.notifyDataSetChanged()
}

}





