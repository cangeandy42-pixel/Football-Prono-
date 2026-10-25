package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MatchAnalysis
import com.example.data.model.SubscriptionRequest
import com.example.ui.components.MatchCard
import com.example.ui.theme.AlertRed
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
import com.example.viewmodel.AdminTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    isAdminLoggedIn: Boolean,
    adminPinInput: String,
    onAdminPinChange: (String) -> Unit,
    onAdminLogin: () -> Unit,
    onAdminLogout: () -> Unit,
    adminLoginError: String?,

    adminTab: AdminTab,
    onSelectAdminTab: (AdminTab) -> Unit,

    // Match Editor
    editingMatchId: Long?,
    matchTitle: String,
    onTitleChange: (String) -> Unit,
    matchLeague: String,
    onLeagueChange: (String) -> Unit,
    matchTime: String,
    onTimeChange: (String) -> Unit,
    matchPick: String,
    onPickChange: (String) -> Unit,
    matchPrediction: String,
    onPredictionChange: (String) -> Unit,
    matchOdds: String,
    onOddsChange: (String) -> Unit,
    matchConfidence: String,
    onConfidenceChange: (String) -> Unit,
    matchVisibility: String,
    onVisibilityChange: (String) -> Unit,
    onPublishAnalysis: () -> Unit,
    onClearEditor: () -> Unit,
    editorMessage: String?,

    // Match Management
    allMatches: List<MatchAnalysis>,
    onStartEditMatch: (MatchAnalysis) -> Unit,
    onDeleteMatch: (MatchAnalysis) -> Unit,
    onStatusChange: (MatchAnalysis, String) -> Unit,

    // Subscription Requests
    subscriptionRequests: List<SubscriptionRequest>,
    onApproveRequest: (SubscriptionRequest) -> Unit,
    onRejectRequest: (SubscriptionRequest) -> Unit,
    onDeleteRequest: (Long) -> Unit,

    // Payment Settings
    editMoncash: String,
    onEditMoncashChange: (String) -> Unit,
    editNatcash: String,
    onEditNatcashChange: (String) -> Unit,
    editBinance: String,
    onEditBinanceChange: (String) -> Unit,
    editPin: String,
    onEditPinChange: (String) -> Unit,
    onSavePaymentSettings: () -> Unit,
    settingsSavedFeedback: String?,

    modifier: Modifier = Modifier
) {
    if (!isAdminLoggedIn) {
        // PIN Entry Screen
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(PitchDark)
                .padding(20.dp)
                .testTag("admin_login_screen"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = PitchCard),
                border = BorderStroke(1.dp, PitchCardBorder)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = TurfGreenPrimary.copy(alpha = 0.15f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = null,
                            tint = TurfGreenLight,
                            modifier = Modifier
                                .padding(12.dp)
                                .size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Espas Administratè",
                        color = TextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Antre kòd PIN administratè a pou pibliye match ak jere peman yo. (Kòd defo: 2468)",
                        color = TextMuted,
                        fontSize = 12.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = adminPinInput,
                        onValueChange = onAdminPinChange,
                        placeholder = { Text("Antre PIN (2468)", color = TextMuted) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_pin_input"),
                        shape = RoundedCornerShape(12.dp),
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

                    if (adminLoginError != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = adminLoginError,
                            color = AlertRed,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = onAdminLogin,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("admin_login_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TurfGreenPrimary)
                    ) {
                        Text(
                            text = "Antre nan espas mwen",
                            color = Color(0xFF07140D),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    } else {
        // Logged-in Admin Dashboard
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(PitchDark)
                .testTag("admin_dashboard")
        ) {
            // Admin Top Bar
            Surface(
                color = PitchDarkElevated,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = null,
                            tint = TurfGreenLight,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Administratè",
                            color = TextPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        onClick = onAdminLogout,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = AlertRed),
                        border = BorderStroke(1.dp, AlertRed.copy(alpha = 0.5f)),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                    ) {
                        Icon(Icons.Default.ExitToApp, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sòti", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Sub-tabs
            TabRow(
                selectedTabIndex = adminTab.ordinal,
                containerColor = PitchDark,
                contentColor = TurfGreenPrimary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[adminTab.ordinal]),
                        color = TurfGreenPrimary
                    )
                }
            ) {
                Tab(
                    selected = adminTab == AdminTab.WRITE_MATCH,
                    onClick = { onSelectAdminTab(AdminTab.WRITE_MATCH) },
                    text = { Text("Ekri", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = adminTab == AdminTab.MANAGE_MATCHES,
                    onClick = { onSelectAdminTab(AdminTab.MANAGE_MATCHES) },
                    text = { Text("Match (${allMatches.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = adminTab == AdminTab.REQUESTS,
                    onClick = { onSelectAdminTab(AdminTab.REQUESTS) },
                    text = { Text("Demann (${subscriptionRequests.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = adminTab == AdminTab.PAYMENT_CONFIG,
                    onClick = { onSelectAdminTab(AdminTab.PAYMENT_CONFIG) },
                    text = { Text("Peman", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
            }

            // Sub-tab content
            when (adminTab) {
                AdminTab.WRITE_MATCH -> AdminWriteMatchView(
                    editingMatchId = editingMatchId,
                    matchTitle = matchTitle,
                    onTitleChange = onTitleChange,
                    matchLeague = matchLeague,
                    onLeagueChange = onLeagueChange,
                    matchTime = matchTime,
                    onTimeChange = onTimeChange,
                    matchPick = matchPick,
                    onPickChange = onPickChange,
                    matchPrediction = matchPrediction,
                    onPredictionChange = onPredictionChange,
                    matchOdds = matchOdds,
                    onOddsChange = onOddsChange,
                    matchConfidence = matchConfidence,
                    onConfidenceChange = onConfidenceChange,
                    matchVisibility = matchVisibility,
                    onVisibilityChange = onVisibilityChange,
                    onPublishAnalysis = onPublishAnalysis,
                    onClearEditor = onClearEditor,
                    editorMessage = editorMessage
                )
                AdminTab.MANAGE_MATCHES -> AdminManageMatchesView(
                    matches = allMatches,
                    onEdit = onStartEditMatch,
                    onDelete = onDeleteMatch,
                    onStatusChange = onStatusChange
                )
                AdminTab.REQUESTS -> AdminRequestsView(
                    requests = subscriptionRequests,
                    onApprove = onApproveRequest,
                    onReject = onRejectRequest,
                    onDelete = onDeleteRequest
                )
                AdminTab.PAYMENT_CONFIG -> AdminPaymentConfigView(
                    editMoncash = editMoncash,
                    onEditMoncashChange = onEditMoncashChange,
                    editNatcash = editNatcash,
                    onEditNatcashChange = onEditNatcashChange,
                    editBinance = editBinance,
                    onEditBinanceChange = onEditBinanceChange,
                    editPin = editPin,
                    onEditPinChange = onEditPinChange,
                    onSavePaymentSettings = onSavePaymentSettings,
                    settingsSavedFeedback = settingsSavedFeedback
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminWriteMatchView(
    editingMatchId: Long?,
    matchTitle: String,
    onTitleChange: (String) -> Unit,
    matchLeague: String,
    onLeagueChange: (String) -> Unit,
    matchTime: String,
    onTimeChange: (String) -> Unit,
    matchPick: String,
    onPickChange: (String) -> Unit,
    matchPrediction: String,
    onPredictionChange: (String) -> Unit,
    matchOdds: String,
    onOddsChange: (String) -> Unit,
    matchConfidence: String,
    onConfidenceChange: (String) -> Unit,
    matchVisibility: String,
    onVisibilityChange: (String) -> Unit,
    onPublishAnalysis: () -> Unit,
    onClearEditor: () -> Unit,
    editorMessage: String?
) {
    val predictionOptions = listOf(
        "Plis pase 2.5 gòl",
        "1 — Viktwa ekip lakay",
        "X — Match nil",
        "2 — Viktwa ekip deyò",
        "Plis pase 1.5 gòl",
        "MWENS pase 2.5 gòl",
        "Chak ekip make",
        "Double chans 1X",
        "Double chans X2",
        "Rezilta egzak"
    )

    var predictionExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = if (editingMatchId != null) "✏️ Modifye Analiz la" else "✍️ Ekri Pwòp Analiz Mwen",
                color = TextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Ou lib pou mete analiz ekip yo, fòm yo, gòl, estatistik ak pronostic.",
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        // Title
        item {
            Text("Non match la", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = matchTitle,
                onValueChange = onTitleChange,
                placeholder = { Text("Barcelona vs Real Madrid", color = TextMuted) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("match_title_input"),
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
        }

        // League & Time
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Lig / Konpetisyon", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = matchLeague,
                        onValueChange = onLeagueChange,
                        placeholder = { Text("La Liga", color = TextMuted) },
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
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text("Dat ak lè", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = matchTime,
                        onValueChange = onTimeChange,
                        placeholder = { Text("26 oktòb, 15:00", color = TextMuted) },
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
                }
            }
        }

        // Prediction dropdown & Odds
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Prediction Dropdown
                Column(modifier = Modifier.weight(1.4f)) {
                    Text("Chwa pronostic", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))

                    ExposedDropdownMenuBox(
                        expanded = predictionExpanded,
                        onExpandedChange = { predictionExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = matchPrediction,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = predictionExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary,
                                focusedContainerColor = PitchDarkElevated,
                                unfocusedContainerColor = PitchDarkElevated,
                                focusedBorderColor = TurfGreenPrimary,
                                unfocusedBorderColor = PitchCardBorder
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = predictionExpanded,
                            onDismissRequest = { predictionExpanded = false },
                            modifier = Modifier.background(PitchCard)
                        ) {
                            predictionOptions.forEach { opt ->
                                DropdownMenuItem(
                                    text = { Text(opt, color = TextPrimary) },
                                    onClick = {
                                        onPredictionChange(opt)
                                        predictionExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Odds
                Column(modifier = Modifier.weight(0.6f)) {
                    Text("Cote", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = matchOdds,
                        onValueChange = onOddsChange,
                        placeholder = { Text("1.85", color = TextMuted) },
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
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
                }
            }
        }

        // Confidence & Visibility
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Confidence selector
                Column(modifier = Modifier.weight(1f)) {
                    Text("Nivo konfyans", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        listOf("Wo", "Mwayen", "Ba").forEach { conf ->
                            val isSel = matchConfidence == conf
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onConfidenceChange(conf) },
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSel) TurfGreenPrimary.copy(alpha = 0.2f) else PitchDarkElevated,
                                border = BorderStroke(1.dp, if (isSel) TurfGreenPrimary else PitchCardBorder)
                            ) {
                                Text(
                                    text = conf,
                                    color = if (isSel) TurfGreenLight else TextMuted,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }
                }

                // Visibility selector
                Column(modifier = Modifier.weight(1f)) {
                    Text("Vizibilite", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        listOf(
                            "both" to "Toulede",
                            "public" to "Piblik",
                            "premium" to "VIP"
                        ).forEach { (vis, label) ->
                            val isSel = matchVisibility == vis
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onVisibilityChange(vis) },
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSel) TrophyGold.copy(alpha = 0.2f) else PitchDarkElevated,
                                border = BorderStroke(1.dp, if (isSel) TrophyGold else PitchCardBorder)
                            ) {
                                Text(
                                    text = label,
                                    color = if (isSel) TrophyGold else TextMuted,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }

        // Full Analysis Textarea
        item {
            Text("Analiz konplè pa mwen", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = matchPick,
                onValueChange = onPickChange,
                placeholder = {
                    Text(
                        "Ekri tout analiz ou isit la...\n\nFòm dènye match yo:\nAnaliz atak ak defans:\nEstatistik:\nPronostic mwen:\nNòt pèsonèl:",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .testTag("match_analysis_textarea"),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedContainerColor = PitchDarkElevated,
                    unfocusedContainerColor = PitchDarkElevated,
                    focusedBorderColor = TurfGreenPrimary,
                    unfocusedBorderColor = PitchCardBorder
                )
            )
        }

        // Publish / Cancel Buttons
        item {
            Button(
                onClick = onPublishAnalysis,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("publish_analysis_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TurfGreenPrimary)
            ) {
                Text(
                    text = if (editingMatchId != null) "💾 Sove Chanjman Analiz la" else "📢 Pibliye Analiz Mwen",
                    color = Color(0xFF07140D),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onClearEditor,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                border = BorderStroke(1.dp, PitchCardBorder)
            ) {
                Text("Netwaye fòm nan", fontSize = 12.sp)
            }

            if (editorMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF153D29),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = editorMessage,
                        color = TurfGreenLight,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun AdminManageMatchesView(
    matches: List<MatchAnalysis>,
    onEdit: (MatchAnalysis) -> Unit,
    onDelete: (MatchAnalysis) -> Unit,
    onStatusChange: (MatchAnalysis, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "📋 Tout Analiz Pibliye Yo (${matches.size})",
                color = TextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Ou ka chanje rezilta match yo (Ganyan, Pèdi, Atant), modifye oswa efase yo.",
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        if (matches.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = PitchCard)
                ) {
                    Text(
                        text = "Ou poko pibliye analiz. Ale nan tab 'Ekri' pou kòmanse.",
                        color = TextMuted,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
        } else {
            items(matches, key = { it.id }) { match ->
                MatchCard(
                    match = match,
                    isAdmin = true,
                    onEdit = { onEdit(match) },
                    onDelete = { onDelete(match) },
                    onStatusChange = { newStatus -> onStatusChange(match, newStatus) }
                )
            }
        }
    }
}

@Composable
fun AdminRequestsView(
    requests: List<SubscriptionRequest>,
    onApprove: (SubscriptionRequest) -> Unit,
    onReject: (SubscriptionRequest) -> Unit,
    onDelete: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "📥 Demann Abònman (${requests.size})",
                color = TextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Verifye referans peman kliyan yo pou apwouve oswa rejte demann yo.",
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        if (requests.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = PitchCard)
                ) {
                    Text(
                        text = "Pa gen okenn demann peman anrejistre pou kounye a.",
                        color = TextMuted,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
        } else {
            items(requests, key = { it.id }) { req ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = PitchCard),
                    border = BorderStroke(1.dp, PitchCardBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = req.customerName,
                                color = TextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            val (statusBg, statusFg) = when (req.status) {
                                "Apwouve" -> Pair(TurfGreenPrimary.copy(alpha = 0.2f), TurfGreenLight)
                                "Rejte" -> Pair(AlertRed.copy(alpha = 0.2f), AlertRed)
                                else -> Pair(TrophyGold.copy(alpha = 0.2f), TrophyGold)
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = statusBg
                            ) {
                                Text(
                                    text = req.status,
                                    color = statusFg,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "📞 ${req.phone} • 💰 ${req.plan}",
                            color = TextSecondary,
                            fontSize = 13.sp
                        )

                        Text(
                            text = "Metòd: ${req.paymentMethod} • Ref: ${req.reference}",
                            color = TrophyGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onApprove(req) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = TurfGreenPrimary),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 4.dp)
                            ) {
                                Text("Apwouve ✓", color = Color(0xFF07140D), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            OutlinedButton(
                                onClick = { onReject(req) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = AlertRed),
                                border = BorderStroke(1.dp, AlertRed.copy(alpha = 0.5f)),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 4.dp)
                            ) {
                                Text("Rejte ✗", fontSize = 11.sp)
                            }

                            IconButton(
                                onClick = { onDelete(req.id) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminPaymentConfigView(
    editMoncash: String,
    onEditMoncashChange: (String) -> Unit,
    editNatcash: String,
    onEditNatcashChange: (String) -> Unit,
    editBinance: String,
    onEditBinanceChange: (String) -> Unit,
    editPin: String,
    onEditPinChange: (String) -> Unit,
    onSavePaymentSettings: () -> Unit,
    settingsSavedFeedback: String?
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "💳 Enstriksyon ak Nimewo Peman",
                color = TextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Nimewo sa yo ap parèt nan espas abònman an pou kliyan yo voye lajan an.",
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        item {
            Text("MonCash", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = editMoncash,
                onValueChange = onEditMoncashChange,
                modifier = Modifier.fillMaxWidth(),
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
        }

        item {
            Text("NatCash", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = editNatcash,
                onValueChange = onEditNatcashChange,
                modifier = Modifier.fillMaxWidth(),
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
        }

        item {
            Text("Binance ID", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = editBinance,
                onValueChange = onEditBinanceChange,
                modifier = Modifier.fillMaxWidth(),
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
        }

        item {
            Text("Kòd PIN Administratè", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = editPin,
                onValueChange = onEditPinChange,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
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
        }

        item {
            Button(
                onClick = onSavePaymentSettings,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TurfGreenPrimary)
            ) {
                Text(
                    text = "Sove Nimewo Peman yo",
                    color = Color(0xFF07140D),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (settingsSavedFeedback != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF153D29),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = settingsSavedFeedback,
                        color = TurfGreenLight,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}
