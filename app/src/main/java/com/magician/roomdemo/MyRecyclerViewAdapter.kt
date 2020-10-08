package com.magician.roomdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.magician.roomdemo.databinding.ListItemBinding
import com.magician.roomdemo.db.Subscriber

class MyRecyclerViewAdapter(
    private val onClickListener: (Subscriber) -> Unit
) :
    RecyclerView.Adapter<MyViewHolder>() {
    private val subscribersList = ArrayList<Subscriber>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.list_item, parent, false
            )
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position], onClickListener)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }

    fun setList(subscribers: List<Subscriber>) {
        subscribersList.clear()
        subscribersList.addAll(subscribers)
    }
}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(subscriber: Subscriber, onClickListener: (Subscriber) -> Unit) {
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text = subscriber.email
        binding.cardView.setOnClickListener {
            onClickListener(subscriber)
        }
    }
}