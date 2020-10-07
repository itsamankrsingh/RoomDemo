package com.magician.roomdemo.db

class SubscriberRepository(private val subscriberDAO: SubscriberDAO) {

    val subscribers = subscriberDAO.getAllSubscriber()

    suspend fun insert(subscriber: Subscriber) {
        subscriberDAO.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber) {
        subscriberDAO.updateSubscriber((subscriber))
    }

    suspend fun delete(subscriber: Subscriber) {
        subscriberDAO.deleteSubscriber(subscriber)
    }

    suspend fun clearAll() {
        subscriberDAO.clearAll()
    }
}