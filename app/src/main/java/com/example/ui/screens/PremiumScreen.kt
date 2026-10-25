package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MatchAnalysis
import com.example.ui.components.MatchCard
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
fun PremiumScreen(
    isVipActive: Boolean,
    premiumMatches: List<MatchAnalysis>,
    onGoToSubscribe: () -> Unit,
    onToggleVip: (Boolean) -> Unit,
    onSelectMatch: (MatchAnalysis) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(PitchDark)
            .padding(horizontal = 14.dp)
            .testTag("premium_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // VIP Status Header
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isVipActive) PitchCardHover else PitchCard
                ),
                border = BorderStroke(
                    1.5.dp,
                    if (isVipActive) TrophyGold else PitchCardBorder
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isVipActive) TrophyGold.copy(alpha = 0.2f) else PitchDarkElevated
                            ) {
                                Icon(
                                    imageVector = if (isVipActive) Icons.Default.WorkspacePremium else Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (isVipActive) TrophyGold else TextMuted,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (isVipActive) "Espas VIP Premium Aktif" else "Espas VIP Premium",
                                    color = if (isVipActive) TrophyGold else TextPrimary,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = if (isVipActive) "Ou gen aksè konplè a tout analiz ak pronostic" else "Aksè rezève pou manm abòne",
                                    color = TextSecondary,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    if (!isVipActive) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Benefisye analiz ki pi solid yo, pi gwo cote, tikè konbine wikenn ak konsèy pèsonalize.",
                            color = TextMuted,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = onGoToSubscribe,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("subscribe_from_premium_button"),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = TrophyGold)
                            ) {
                                Text(
                                    text = "Pran Abònman",
                                    color = Color(0xFF07140D),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }

                            OutlinedButton(
                                onClick = { onToggleVip(true) },
                                modifier = Modifier.weight(1f).height(44.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = TurfGreenLight),
                                border = BorderStroke(1.dp, TurfGreenPrimary.copy(alpha = 0.5f))
                            ) {
                                Text(
                                    text = "Tès Aksè VIP",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TurfGreenLight, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Aksè san limit sou tout match", color = TurfGreenLight, fontSize = 12.sp)
                            }

                            Text(
                                text = "Dezaktive",
                                color = TextMuted,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Section Title: Analiz Premium
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🏆 Pronostic ak Analiz VIP",
                    color = TextPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${premiumMatches.size} match VIP",
                    color = TrophyGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Matches list
        if (premiumMatches.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = PitchCard),
                    border = BorderStroke(1.dp, PitchCardBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Pa gen analiz Premium pibliye pou kounye a.",
                            color = TextMuted,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(premiumMatches, key = { it.id }) { match ->
                MatchCard(
                    match = match,
                    isLockedPremium = !isVipActive,
                    onClick = {
                        if (isVipActive) {
                            onSelectMatch(match)
                        } else {
                            onGoToSubscribe()
                        }
                    }
                )
            }
        }

        // Tikè Konbine Advice Section (visible when VIP)
        if (isVipActive) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF132A1C)),
                    border = BorderStroke(1.dp, TurfGreenPrimary.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Diamond, contentDescription = null, tint = TrophyGold, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Konsèy Tikè Konbine (Kominote VIP)",
                                color = TrophyGold,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "• Pa mete plis pase 3 a 4 match sou menm tikè a.\n• Favorize gwo konfyans (Plis pase 1.5 gòl oswa Double Chans).\n• Jere bankwòl ou: pa janm parye plis pase 5% kapital ou sou yon sèl tikè.",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
