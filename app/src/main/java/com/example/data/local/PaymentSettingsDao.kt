package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.PaymentSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentSettingsDao {
    @Query("SELECT * FROM payment_settings WHERE id = 1 LIMIT 1")
    fun getSettingsFlow(): Flow<PaymentSettings?>

    @Query("SELECT * FROM payment_settings WHERE id = 1 LIMIT 1")
    suspend fun getSettings(): PaymentSettings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(settings: PaymentSettings)

    @Update
    suspend fun update(settings: PaymentSettings)

    @Query("UPDATE payment_settings SET isVipActive = :isActive WHERE id = 1")
    suspend fun setVipActive(isActive: Boolean)
}
