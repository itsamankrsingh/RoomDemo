package com.magician.roomdemo.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDAO {

    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber)

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    @Query("DELETE FROM subscriber_data_table")
    suspend fun clearAll()

    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscriber(): LiveData<List<Subscriber>>

}