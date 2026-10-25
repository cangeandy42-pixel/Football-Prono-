package com.example.ui.components

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MatchAnalysis
import com.example.ui.theme.AlertRed
import com.example.ui.theme.DividerGreen
import com.example.ui.theme.InfoBlue
import com.example.ui.theme.PitchCard
import com.example.ui.theme.PitchCardBorder
import com.example.ui.theme.PitchCardHover
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TrophyGold
import com.example.ui.theme.TurfGreenLight
import com.example.ui.theme.TurfGreenPrimary

@Composable
fun MatchCard(
    match: MatchAnalysis,
    isAdmin: Boolean = false,
    isLockedPremium: Boolean = false,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {},
    onStatusChange: (String) -> Unit = {},
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("match_card_${match.id}")
            .clickable {
                if (!isLockedPremium) {
                    isExpanded = !isExpanded
                    onClick()
                }
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PitchCard),
        border = BorderStroke(1.dp, PitchCardBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: League & Time + Result Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Text(
                        text = match.league,
                        color = TurfGreenLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (match.matchTime.isNotBlank()) {
                        Text(
                            text = " • ",
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = match.matchTime,
                                color = TextMuted,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                // Visibility / Status Tag
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (match.visibility == "premium") {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = TrophyGold.copy(alpha = 0.2f),
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            Text(
                                text = "VIP",
                                color = TrophyGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    // Result Status badge
                    val (statusBg, statusFg, statusLabel) = when (match.resultStatus) {
                        "Won" -> Triple(TurfGreenPrimary.copy(alpha = 0.2f), TurfGreenLight, "GANYAN")
                        "Lost" -> Triple(AlertRed.copy(alpha = 0.2f), AlertRed, "PÈDI")
                        else -> Triple(PitchCardHover, TextMuted, "AN ATANT")
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = statusBg
                    ) {
                        Text(
                            text = statusLabel,
                            color = statusFg,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Match Title (Teams)
            Text(
                text = match.title,
                color = TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Prediction & Confidence Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isLockedPremium) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = TrophyGold.copy(alpha = 0.2f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = TrophyGold,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Pronostic Rezève pou VIP",
                                color = TrophyGold,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } else {
                    // Prediction Pill (Gold)
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = TrophyGold
                    ) {
                        Text(
                            text = match.prediction,
                            color = Color(0xFF07140D),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }

                    // Odds Pill if available
                    if (match.odds.isNotBlank()) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = PitchCardHover,
                            border = BorderStroke(1.dp, TurfGreenPrimary.copy(alpha = 0.4f))
                        ) {
                            Text(
                                text = "Cote: ${match.odds}",
                                color = TurfGreenLight,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                            )
                        }
                    }

                    // Confidence Pill
                    val confColor = when (match.confidence) {
                        "Wo" -> TurfGreenLight
                        "Mwayen" -> TrophyGold
                        else -> TextMuted
                    }
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = PitchCardHover
                    ) {
                        Text(
                            text = "Konfyans: ${match.confidence}",
                            color = confColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Analysis text
            if (isLockedPremium) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = PitchCardHover,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = TrophyGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Debloke abònman Premium ou pou w wè analiz konplè ak prediksyon match sa a.",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            } else {
                Text(
                    text = match.analysisText,
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                    overflow = TextOverflow.Ellipsis
                )

                if (match.analysisText.length > 120) {
                    Text(
                        text = if (isExpanded) "Montre mwens ▲" else "Li tout analiz la ▼",
                        color = TurfGreenPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clickable { isExpanded = !isExpanded }
                    )
                }

                // Share / Action Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_SUBJECT,
                                    "Football Prono Haiti: ${match.title}"
                                )
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "⚽ *Football Prono Haiti*\n\n" +
                                            "🏆 *${match.league}*\n" +
                                            "🔥 *Match:* ${match.title}\n" +
                                            "🎯 *Chwa:* ${match.prediction}\n" +
                                            "📊 *Cote:* ${match.odds} • *Konfyans:* ${match.confidence}\n\n" +
                                            "📝 *Analiz:* ${match.analysisText}\n\n" +
                                            "_Pwonostik pa garanti okenn rezilta._"
                                )
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Pataje analiz"))
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Pataje analiz",
                            tint = TurfGreenPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Admin Controls
            if (isAdmin) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(DividerGreen)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Aksyon Administratè:",
                    color = TextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Status Toggles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    OutlinedButton(
                        onClick = { onStatusChange("Won") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = TurfGreenLight
                        ),
                        border = BorderStroke(1.dp, TurfGreenPrimary.copy(alpha = 0.5f))
                    ) {
                        Text("Ganyan ✓", fontSize = 11.sp)
                    }
                    OutlinedButton(
                        onClick = { onStatusChange("Lost") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = AlertRed
                        ),
                        border = BorderStroke(1.dp, AlertRed.copy(alpha = 0.5f))
                    ) {
                        Text("Pèdi ✗", fontSize = 11.sp)
                    }
                    OutlinedButton(
                        onClick = { onStatusChange("Pending") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = TextMuted
                        ),
                        border = BorderStroke(1.dp, DividerGreen)
                    ) {
                        Text("Atant ⏱", fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Edit / Delete Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onEdit,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = TurfGreenPrimary)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = Color(0xFF07140D), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Modifye", color = Color(0xFF07140D), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onDelete,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = AlertRed),
                        border = BorderStroke(1.dp, AlertRed.copy(alpha = 0.6f))
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null, tint = AlertRed, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Efase", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
