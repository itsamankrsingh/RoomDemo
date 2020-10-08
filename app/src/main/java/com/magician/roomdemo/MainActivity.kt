package com.magician.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.magician.roomdemo.databinding.ActivityMainBinding
import com.magician.roomdemo.db.Subscriber
import com.magician.roomdemo.db.SubscriberDatabase
import com.magician.roomdemo.db.SubscriberRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var adapter: MyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application)
            .subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory)
            .get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        binding.subscriberReyclerView.layoutManager = LinearLayoutManager(this)
        adapter =
            MyRecyclerViewAdapter() { selectedItem: Subscriber -> listItemClicked(selectedItem) }
        binding.subscriberReyclerView.adapter = adapter

        displaySubscriber()
    }

    private fun displaySubscriber() {
        subscriberViewModel.subscriber.observe(this, Observer {
            Log.i("MYTAG", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber: Subscriber) {
        /*Toast.makeText(
            this, "Selected subscriber is ${subscriber.name}",
            Toast.LENGTH_SHORT
        ).show()*/
        subscriberViewModel.initUpdateOrDelete(subscriber)
    }
}