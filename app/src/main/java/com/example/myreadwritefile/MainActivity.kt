package com.example.myreadwritefile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myreadwritefile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNewFile.setOnClickListener { newFile() }
        binding.btnOpenFile.setOnClickListener { showListOfFile() }
        binding.btnSaveFile.setOnClickListener { saveFile() }
    }

    private fun newFile() {
        binding.eTT.setText("")
        binding.eTTML.setText("")
        Toast.makeText(this, "Clearing File", Toast.LENGTH_SHORT).show()
    }

    private fun showListOfFile() {
        val items = fileList().filter { fileName -> (fileName != "profileInstalled") }.toTypedArray()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose a File")
        builder.setItems(items) { _, item -> loadData(items[item].toString()) }
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        binding.eTT.setText(fileModel.fileName)
        binding.eTTML.setText(fileModel.data)
        Toast.makeText(this, "${fileModel.fileName} is loaded", Toast.LENGTH_SHORT).show()
    }

    private fun saveFile() {
        when {
            binding.eTT.text.toString().isEmpty() ->
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()

            binding.eTTML.text.toString().isEmpty() ->
                Toast.makeText(this, "Content cannot be empty", Toast.LENGTH_SHORT).show()

            else -> {
                val title = binding.eTT.text.toString()
                val text = binding.eTTML.text.toString()
                val fileModel = FileModel()
                fileModel.fileName = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(this, "File is saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
}