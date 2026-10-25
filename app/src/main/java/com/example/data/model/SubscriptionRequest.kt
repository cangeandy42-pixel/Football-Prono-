package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscription_requests")
data class SubscriptionRequest(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val customerName: String,
    val phone: String,
    val plan: String,
    val paymentMethod: String,
    val reference: String,
    val status: String = "An atant", // "An atant", "Apwouve", "Rejte"
    val submittedAt: Long = System.currentTimeMillis()
)
