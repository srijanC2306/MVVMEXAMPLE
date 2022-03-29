package com.example.mvvmkotlin.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.mvvmkotlin.Blog

class MainViewModel: ViewModel() {
    var lst = MutableLiveData<ArrayList<Blog>>()

    var newlist = arrayListOf<Blog>()

    fun add(blog: Blog){
        newlist.add(blog)
        lst.value=newlist
    }

    fun remove(blog: Blog){
        newlist.remove(blog)
        lst.value=newlist
    }



}