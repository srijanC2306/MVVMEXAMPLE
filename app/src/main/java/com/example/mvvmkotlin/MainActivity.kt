package com.example.mvvmkotlin

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkotlin.ViewModel.MainViewModel
import com.example.mvvmkotlin.ViewModel.MainViewModelFactory
import com.example.mvvmkotlin.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import java.util.Observer

class MainActivity : AppCompatActivity() {


    private var viewManager = LinearLayoutManager(this)
    private lateinit var viewModel: MainViewModel
    private lateinit var mainrecycler: RecyclerView
    private lateinit var but: Button
    private lateinit var fab:FloatingActionButton

    private  var blogList: ArrayList<Blog> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainrecycler = findViewById(R.id.recycler)
        val application = requireNotNull(this).application
        val factory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        but = findViewById(R.id.button)
        fab = findViewById(R.id.fab_btn)
        but.setOnClickListener {
            addData()
        }

        initialiseAdapter()

        fab.setOnClickListener {

            showAddLeadPopup()

        }


    }


    private fun showAddLeadPopup() {
        val customDialog = Dialog(this@MainActivity)
// customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setCancelable(false)

        val view: View = layoutInflater.inflate(R.layout.popup_add_lead, null)
        view.animation =
            AnimationUtils.loadAnimation(this@MainActivity, R.anim.slide_animation)
        customDialog.setContentView(view)

        val btnCancel = view.findViewById<TextView>(R.id.btn_cancel)
        val btnAddLead = view.findViewById<TextView>(R.id.btn_add_lead)
        val metlLeadName = view.findViewById<TextInputLayout>(R.id.metl_lead_name)
        val metlLeadPhoneNumber = view.findViewById<TextInputLayout>(R.id.metl_lead_phone_number)


        metlLeadName.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnAddLead.isEnabled = metlLeadName.editText!!.text.toString().trim().isNotEmpty() && metlLeadPhoneNumber.editText!!.text.length == 10
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        metlLeadPhoneNumber.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnAddLead.isEnabled = metlLeadName.editText!!.text.toString().trim().isNotEmpty() && metlLeadPhoneNumber.editText!!.text.toString().length == 10
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })


        btnCancel.setOnClickListener {
            customDialog.dismiss()
        }

        btnAddLead.setOnClickListener {
            addData()
            serviceGetLeadPhoneNumberCheckObserver(
                metlLeadPhoneNumber.editText!!.text.toString(),
                metlLeadName.editText!!.text.toString(),
            )
            customDialog.dismiss()
        }

        customDialog.show()
    }

    private fun serviceGetLeadPhoneNumberCheckObserver(toString: String, toString1: String) {

//        addData()

        blogList.add(Blog(toString))

        val adapter = NoteRecyclerAdapter(viewModel, blogList,this)
        mainrecycler.adapter = adapter


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









