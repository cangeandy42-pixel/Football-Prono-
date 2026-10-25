package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.model.MatchAnalysis
import com.example.data.model.PaymentSettings
import com.example.data.model.SubscriptionRequest
import kotlinx.coroutines.flow.Flow

class FootballPronoRepository(private val db: AppDatabase) {
    val allMatches: Flow<List<MatchAnalysis>> = db.matchDao().getAllMatches()
    val publicMatches: Flow<List<MatchAnalysis>> = db.matchDao().getPublicMatches()
    val premiumMatches: Flow<List<MatchAnalysis>> = db.matchDao().getPremiumMatches()
    val subscriptionRequests: Flow<List<SubscriptionRequest>> = db.subscriptionDao().getAllRequests()
    val paymentSettings: Flow<PaymentSettings?> = db.paymentSettingsDao().getSettingsFlow()

    suspend fun insertMatch(match: MatchAnalysis): Long {
        return db.matchDao().insertMatch(match)
    }

    suspend fun updateMatch(match: MatchAnalysis) {
        db.matchDao().updateMatch(match)
    }

    suspend fun deleteMatch(match: MatchAnalysis) {
        db.matchDao().deleteMatch(match)
    }

    suspend fun deleteMatchById(id: Long) {
        db.matchDao().deleteMatchById(id)
    }

    suspend fun submitSubscriptionRequest(request: SubscriptionRequest): Long {
        return db.subscriptionDao().insertRequest(request)
    }

    suspend fun updateSubscriptionStatus(id: Long, status: String) {
        db.subscriptionDao().updateStatus(id, status)
    }

    suspend fun deleteSubscriptionRequest(id: Long) {
        db.subscriptionDao().deleteRequestById(id)
    }

    suspend fun updatePaymentSettings(settings: PaymentSettings) {
        db.paymentSettingsDao().insertOrUpdate(settings)
    }

    suspend fun setVipActive(isActive: Boolean) {
        db.paymentSettingsDao().setVipActive(isActive)
    }

    suspend fun getSettings(): PaymentSettings? {
        return db.paymentSettingsDao().getSettings()
    }
}
