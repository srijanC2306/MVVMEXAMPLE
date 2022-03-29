package com.example.mvvmkotlin

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkotlin.ViewModel.MainViewModel
import com.example.mvvmkotlin.ViewModel.MainViewModelFactory
import com.example.mvvmkotlin.databinding.ActivityMainBinding
import java.util.Observer

class MainActivity : AppCompatActivity() {


    private var viewManager = LinearLayoutManager(this)
    private lateinit var viewModel: MainViewModel
    private lateinit var mainrecycler: RecyclerView
    private lateinit var but: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainrecycler = findViewById(R.id.recycler)
        val application = requireNotNull(this).application
        val factory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        but = findViewById(R.id.button)
        but.setOnClickListener {
            addData()
        }

        initialiseAdapter()
    }

    private fun initialiseAdapter() {
        mainrecycler.layoutManager = viewManager
        observeData();
    }

    fun observeData() {
        viewModel.lst.observe(this) {
            Log.i("data", it.toString())
            mainrecycler.adapter = NoteRecyclerAdapter(viewModel, it, this)
        }
    }


    fun addData() {
        var txtplce = findViewById<EditText>(R.id.titletxt)
        var title = txtplce.text.toString()
        if (title.isNullOrBlank()) {
            Toast.makeText(this, "Enter value!", Toast.LENGTH_LONG).show()
        } else {
            var blog = Blog(title)
            viewModel.add(blog)
            txtplce.text.clear()
            mainrecycler.adapter?.notifyDataSetChanged()
        }

    }
}









