package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.SubscriptionRequest
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriptionDao {
    @Query("SELECT * FROM subscription_requests ORDER BY id DESC")
    fun getAllRequests(): Flow<List<SubscriptionRequest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: SubscriptionRequest): Long

    @Update
    suspend fun updateRequest(request: SubscriptionRequest)

    @Query("UPDATE subscription_requests SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String)

    @Delete
    suspend fun deleteRequest(request: SubscriptionRequest)

    @Query("DELETE FROM subscription_requests WHERE id = :id")
    suspend fun deleteRequestById(id: Long)
}
