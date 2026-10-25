package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_settings")
data class PaymentSettings(
    @PrimaryKey
    val id: Int = 1,
    val moncashInfo: String = "+509 38444413",
    val natcashInfo: String = "+509 42444114",
    val binanceInfo: String = "1193247367",
    val adminPin: String = "2468",
    val isVipActive: Boolean = false,
    val vipExpiryDate: String = "",
    val supportPhone: String = "+509 38444413"
)
