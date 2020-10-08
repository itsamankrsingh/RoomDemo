package com.magician.roomdemo.db

class SubscriberRepository(private val subscriberDAO: SubscriberDAO) {

    val subscribers = subscriberDAO.getAllSubscriber()

    suspend fun insert(subscriber: Subscriber): Long {
        return subscriberDAO.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber):Int {
        return subscriberDAO.updateSubscriber((subscriber))
    }

    suspend fun delete(subscriber: Subscriber):Int {
        return subscriberDAO.deleteSubscriber(subscriber)
    }

    suspend fun clearAll():Int {
        return subscriberDAO.clearAll()
    }
}