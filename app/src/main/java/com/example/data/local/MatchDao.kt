package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.MatchAnalysis
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    @Query("SELECT * FROM matches ORDER BY id DESC")
    fun getAllMatches(): Flow<List<MatchAnalysis>>

    @Query("SELECT * FROM matches WHERE visibility IN ('public', 'both') ORDER BY id DESC")
    fun getPublicMatches(): Flow<List<MatchAnalysis>>

    @Query("SELECT * FROM matches WHERE visibility IN ('premium', 'both') ORDER BY id DESC")
    fun getPremiumMatches(): Flow<List<MatchAnalysis>>

    @Query("SELECT * FROM matches WHERE id = :id LIMIT 1")
    suspend fun getMatchById(id: Long): MatchAnalysis?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: MatchAnalysis): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(matches: List<MatchAnalysis>)

    @Update
    suspend fun updateMatch(match: MatchAnalysis)

    @Delete
    suspend fun deleteMatch(match: MatchAnalysis)

    @Query("DELETE FROM matches WHERE id = :id")
    suspend fun deleteMatchById(id: Long)
}
