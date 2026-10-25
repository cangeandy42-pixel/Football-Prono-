package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PaymentSettings
import com.example.ui.theme.DividerGreen
import com.example.ui.theme.PitchCard
import com.example.ui.theme.PitchCardBorder
import com.example.ui.theme.PitchCardHover
import com.example.ui.theme.PitchDark
import com.example.ui.theme.PitchDarkElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TrophyGold
import com.example.ui.theme.TurfGreenLight
import com.example.ui.theme.TurfGreenPrimary

@Composable
fun SubscribeScreen(
    customerName: String,
    onCustomerNameChange: (String) -> Unit,
    customerPhone: String,
    onCustomerPhoneChange: (String) -> Unit,
    selectedPlan: String,
    onPlanSelect: (String) -> Unit,
    paymentMethod: String,
    onPaymentMethodChange: (String) -> Unit,
    referenceId: String,
    onReferenceChange: (String) -> Unit,
    onSubmitSubscription: () -> Unit,
    isSubmitting: Boolean,
    subscriptionMessage: String?,
    paymentSettings: PaymentSettings?,
    vipCodeInput: String,
    onVipCodeInputChange: (String) -> Unit,
    onActivateVipWithCode: () -> Unit,
    vipCodeFeedback: String?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val currentMoncash = paymentSettings?.moncashInfo ?: "+509 38444413"
    val currentNatcash = paymentSettings?.natcashInfo ?: "+509 42444114"
    val currentBinance = paymentSettings?.binanceInfo ?: "1193247367"

    val currentPaymentInstruction = when (paymentMethod) {
        "MonCash" -> "Voye peman sou MonCash: $currentMoncash"
        "NatCash" -> "Voye peman sou NatCash: $currentNatcash"
        "Binance" -> "Voye peman sou Binance ID: $currentBinance"
        else -> "Kontakte sipò a."
    }

    val currentCopyValue = when (paymentMethod) {
        "MonCash" -> currentMoncash
        "NatCash" -> currentNatcash
        "Binance" -> currentBinance
        else -> ""
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PitchDark)
            .verticalScroll(scrollState)
            .padding(14.dp)
            .testTag("subscribe_screen")
    ) {
        // Card: Pran Abònman Ou
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PitchCard),
            border = BorderStroke(1.dp, PitchCardBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.WorkspacePremium,
                        contentDescription = null,
                        tint = TrophyGold,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pran Abònman Ou",
                        color = TextPrimary,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Aksede a tout pronostic Premium, analiz eksklizif ak pi gwo konfyans.",
                    color = TextSecondary,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Input: Non ou
                Text(
                    text = "Non ou konplè",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = customerName,
                    onValueChange = onCustomerNameChange,
                    placeholder = { Text("Egzanp: Jean Baptiste", color = TextMuted) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("customer_name_input"),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedContainerColor = PitchDarkElevated,
                        unfocusedContainerColor = PitchDarkElevated,
                        focusedBorderColor = TurfGreenPrimary,
                        unfocusedBorderColor = PitchCardBorder
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Input: Telefòn / WhatsApp
                Text(
                    text = "Telefòn / WhatsApp",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = customerPhone,
                    onValueChange = onCustomerPhoneChange,
                    placeholder = { Text("+509 3844 4413", color = TextMuted) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("customer_phone_input"),
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedContainerColor = PitchDarkElevated,
                        unfocusedContainerColor = PitchDarkElevated,
                        focusedBorderColor = TurfGreenPrimary,
                        unfocusedBorderColor = PitchCardBorder
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Plan Selector
                Text(
                    text = "Chwazi Plan ou",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val plans = listOf(
                        Triple("250", "250 HTG", "15 jou"),
                        Triple("500", "500 HTG", "30 jou"),
                        Triple("1000", "1 000 HTG", "60 jou")
                    )

                    plans.forEach { (value, price, period) ->
                        val isSelected = selectedPlan == value
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onPlanSelect(value) },
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) TurfGreenPrimary.copy(alpha = 0.15f) else PitchDarkElevated,
                            border = BorderStroke(
                                1.5.dp,
                                if (isSelected) TurfGreenPrimary else PitchCardBorder
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = price,
                                    color = if (isSelected) TurfGreenLight else TextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = period,
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Metòd Peman Selector
                Text(
                    text = "Metòd peman",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("MonCash", "NatCash", "Binance").forEach { method ->
                        val isSelected = paymentMethod == method
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onPaymentMethodChange(method) },
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) TrophyGold.copy(alpha = 0.15f) else PitchDarkElevated,
                            border = BorderStroke(
                                1.5.dp,
                                if (isSelected) TrophyGold else PitchCardBorder
                            )
                        ) {
                            Text(
                                text = method,
                                color = if (isSelected) TrophyGold else TextSecondary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                                    .fillMaxWidth(),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Box: Enstriksyon Peman
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = PitchCardHover),
                    border = BorderStroke(1.dp, TurfGreenPrimary.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Enstriksyon $paymentMethod",
                                color = TurfGreenLight,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )

                            // Copy button
                            Button(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    clipboard.setPrimaryClip(ClipData.newPlainText("Peman", currentCopyValue))
                                    Toast.makeText(context, "$paymentMethod kopye!", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = TurfGreenPrimary),
                                shape = RoundedCornerShape(6.dp),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = null, tint = Color(0xFF07140D), modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Kopye", color = Color(0xFF07140D), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = currentPaymentInstruction,
                            color = TextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Fè transfè a sou nimewo sa a, epi kopye ID/Referans tranzaksyon an nan fòm anba a.",
                            color = TextMuted,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Input: Referans tranzaksyon
                Text(
                    text = "Referans tranzaksyon / ID peman",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = referenceId,
                    onValueChange = onReferenceChange,
                    placeholder = { Text("Egzanp: MC-89218392", color = TextMuted) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("reference_input"),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedContainerColor = PitchDarkElevated,
                        unfocusedContainerColor = PitchDarkElevated,
                        focusedBorderColor = TurfGreenPrimary,
                        unfocusedBorderColor = PitchCardBorder
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Button: Voye demann
                Button(
                    onClick = onSubmitSubscription,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("submit_subscription_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TurfGreenPrimary),
                    enabled = !isSubmitting
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(color = Color(0xFF07140D), modifier = Modifier.size(20.dp))
                    } else {
                        Icon(Icons.Default.Send, contentDescription = null, tint = Color(0xFF07140D), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Voye demann abònman",
                            color = Color(0xFF07140D),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                // Submission Feedback Message
                if (subscriptionMessage != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFF153D29)
                    ) {
                        Text(
                            text = subscriptionMessage,
                            color = TurfGreenLight,
                            fontSize = 12.sp,
                            lineHeight = 17.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Direct VIP Code Activation Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PitchCard),
            border = BorderStroke(1.dp, TrophyGold.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Key,
                        contentDescription = null,
                        tint = TrophyGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ou gen yon kòd aksè VIP deja?",
                        color = TrophyGold,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Si ou te peye oswa administratè a ba ou yon kòd promo (egz: VIP509), antre li la a pou debloke tout analiz Premium yo dirèkteman.",
                    color = TextMuted,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = vipCodeInput,
                        onValueChange = onVipCodeInputChange,
                        placeholder = { Text("Kòd VIP (egz: VIP509)", color = TextMuted) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("vip_code_input"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedContainerColor = PitchDarkElevated,
                            unfocusedContainerColor = PitchDarkElevated,
                            focusedBorderColor = TrophyGold,
                            unfocusedBorderColor = PitchCardBorder
                        ),
                        singleLine = true
                    )

                    Button(
                        onClick = onActivateVipWithCode,
                        colors = ButtonDefaults.buttonColors(containerColor = TrophyGold),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .height(52.dp)
                            .testTag("activate_vip_button")
                    ) {
                        Text("Debloke", color = Color(0xFF07140D), fontWeight = FontWeight.Bold)
                    }
                }

                if (vipCodeFeedback != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = vipCodeFeedback,
                        color = if (vipCodeFeedback.contains("Felisitasyon")) TurfGreenLight else Color(0xFFFFB4A2),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
