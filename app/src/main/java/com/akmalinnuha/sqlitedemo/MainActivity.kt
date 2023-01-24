package com.akmalinnuha.sqlitedemo

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter? = null
    private var std: StudentModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener { addStudent() }
        btnView.setOnClickListener { getStudent() }
        btnUpdate.setOnClickListener { updateStudent() }

        adapter?.setOnclickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
            edName.setText(it.name)
            edEmail.setText(it.email)
            std = it
        }

        adapter?.setOnclickDeleteItem {
            deleteStudent(it.id)
        }
        }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        edName = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edEmail)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        recyclerView = findViewById(R.id.recyclerView)
        btnUpdate = findViewById(R.id.btnUpdate)
    }

    private fun deleteStudent(id: Int) {
        if (id == null) return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure want to delete this data?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            sqLiteHelper.deleteStudentById(id)
            dialog.dismiss()
            getStudent()
        }
        builder.setNegativeButton("No") {dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }
    private fun updateStudent() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if (name.isEmpty() && email.isEmpty()) {
            Toast.makeText(this,"Please select the item first", Toast.LENGTH_SHORT).show()
            return
        }

        if (name == std?.name && email == std?.email) {
            Toast.makeText(this,"Record not changed . . .", Toast.LENGTH_SHORT).show()
            return
        }

        if (std == null) return

        val std = StudentModel(id = std!!.id, name = name, email = email)
        val status = sqLiteHelper.updateStudent(std)
        if (status > -1) {
            clearText()
            getStudent()
        } else {
            Toast.makeText(this,"Update Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getStudent() {
        val stdList = sqLiteHelper.getAllStudent()
        adapter?.addItems(stdList)
    }

    private fun addStudent() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this,"Please enter required field", Toast.LENGTH_SHORT).show()
        } else {
            val std = StudentModel(name = name, email = email)
            val status = sqLiteHelper.insertStudent(std)

            if (status > -1) {
                Toast.makeText(this,"Data added", Toast.LENGTH_SHORT).show()
                clearText()
                getStudent()
            } else {
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearText() {
        edName.setText("")
        edEmail.setText("")
        edName.requestFocus()
    }
}

//    fun initRecyclerView() {
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter = StudentAdapter()
//        recyclerView.adapter = adapter
//    }

//    private fun initView() {
//        edName = findViewById(R.id.edName)
//        edEmail = findViewById(R.id.edEmail)
//        btnAdd = findViewById(R.id.btnAdd)
//        btnView = findViewById(R.id.btnView)
//        recyclerView = findViewById(R.id.recyclerView)
//        btnUpdate = findViewById(R.id.btnUpdate)
//    }
