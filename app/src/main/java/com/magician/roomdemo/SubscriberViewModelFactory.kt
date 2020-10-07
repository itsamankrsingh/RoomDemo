package com.magician.roomdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.magician.roomdemo.db.SubscriberRepository
import java.lang.IllegalArgumentException

class SubscriberViewModelFactory(private val repository: SubscriberRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubscriberViewModel::class.java)) {
            return SubscriberViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}