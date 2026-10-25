package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class MatchAnalysis(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val league: String,
    val matchTime: String,
    val analysisText: String,
    val prediction: String,
    val odds: String = "1.85",
    val confidence: String = "Wo", // "Ba", "Mwayen", "Wo"
    val visibility: String = "both", // "public", "premium", "both"
    val resultStatus: String = "Pending", // "Pending", "Won", "Lost"
    val createdAt: Long = System.currentTimeMillis()
)
