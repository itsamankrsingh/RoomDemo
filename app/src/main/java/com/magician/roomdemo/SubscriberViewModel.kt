package com.magician.roomdemo

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magician.roomdemo.db.Subscriber
import com.magician.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel(), Observable {

    val subscriber = repository.subscribers
    var isUpdateOrDelete = false
    lateinit var subscriberToUpdateOrDelete: Subscriber
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {

        if (inputName.value == null) {
            statusMessage.value = Event("Please enter subscriber's name")
            return
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter subscriber's email")
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter valid subscriber's email")
            return
        } else {
            if (isUpdateOrDelete) {
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!
                update(subscriberToUpdateOrDelete)
            } else {
                val name = inputName.value!!
                val email = inputEmail.value!!
                insert(Subscriber(0, name, email))

                inputName.value = null
                inputEmail.value = null
            }
        }


    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }

    }

    fun initUpdateOrDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"

    }


    fun insert(subscriber: Subscriber): Job = viewModelScope.launch {
        val numberOfRowInserted = repository.insert(subscriber)
        if (numberOfRowInserted > -1) {


            statusMessage.value = Event("$numberOfRowInserted Row Subscriber Added successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun update(subscriber: Subscriber): Job = viewModelScope.launch {
        val numberOfRow = repository.update(subscriber)
        if (numberOfRow > 0) {
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"

            statusMessage.value = Event("$numberOfRow Row Subscriber Updated successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun delete(subscriber: Subscriber): Job = viewModelScope.launch {
        val numberOfRowDeleted = repository.delete(subscriber)
        if (numberOfRowDeleted > 0) {
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"

            statusMessage.value = Event("$numberOfRowDeleted Row Subscriber Deleted successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun clearAll(): Job = viewModelScope.launch {
        val numberOfRowDeleted = repository.clearAll()
        if (numberOfRowDeleted > 0) {
            statusMessage.value = Event("$numberOfRowDeleted subscribers Deleted successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}