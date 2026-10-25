package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
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
import com.example.ui.theme.TrophyGoldContainer
import com.example.ui.theme.TurfGreenLight
import com.example.ui.theme.TurfGreenPrimary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    matches: List<MatchAnalysis>,
    selectedFilter: String,
    onFilterChange: (String) -> Unit,
    onChoosePlan: (String) -> Unit,
    onSelectMatch: (MatchAnalysis) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(PitchDark)
            .padding(horizontal = 14.dp)
            .testTag("home_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Welcome Hero Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = PitchCard),
                border = BorderStroke(1.dp, PitchCardBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = TurfGreenPrimary.copy(alpha = 0.2f),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = TurfGreenLight,
                                modifier = Modifier
                                    .padding(6.dp)
                                    .size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Byenveni nan Football Prono Haiti",
                                color = TextPrimary,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Analiz pwofesyonèl chak jou sou pi gwo match yo",
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Tout analiz ak estatistik se opinyon pèsonèl ki baze sou fòm ekip yo, blesi, taktik ak konfwontasyon dirèk. Yo pa garanti okenn rezilta.",
                        color = TextMuted,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }
        }

        // Subscription Plans Section
        item {
            Text(
                text = "💎 Chwazi Plan Abònman Ou",
                color = TrophyGold,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Grid of 3 plans
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Plan 15 Jou
                PlanCard(
                    title = "Plan 15 Jou",
                    price = "250 HTG",
                    duration = "15 jou aksè",
                    isPopular = false,
                    onSelect = { onChoosePlan("250") },
                    modifier = Modifier.weight(1f)
                )

                // Plan 1 Mwa (Popular)
                PlanCard(
                    title = "Plan 1 Mwa",
                    price = "500 HTG",
                    duration = "30 jou aksè",
                    isPopular = true,
                    onSelect = { onChoosePlan("500") },
                    modifier = Modifier.weight(1f)
                )

                // Plan 2 Mwa
                PlanCard(
                    title = "Plan 2 Mwa",
                    price = "1 000 HTG",
                    duration = "60 jou aksè",
                    isPopular = false,
                    onSelect = { onChoosePlan("1000") },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Section Title: Analiz Piblik + Filters
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "⚽ Analiz Piblik",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${matches.size} match disponib",
                    color = TurfGreenLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Filter chips
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Tout", "Wo Konfyans", "Ganyan").forEach { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { onFilterChange(filter) },
                        label = {
                            Text(
                                text = filter,
                                fontSize = 12.sp,
                                fontWeight = if (selectedFilter == filter) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = TurfGreenPrimary,
                            selectedLabelColor = Color(0xFF07140D),
                            containerColor = PitchCardHover,
                            labelColor = TextSecondary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = selectedFilter == filter,
                            borderColor = PitchCardBorder,
                            selectedBorderColor = TurfGreenPrimary
                        )
                    )
                }
            }
        }

        // List of Public Matches
        if (matches.isEmpty()) {
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
                            text = "Pa gen analiz piblik pou kounye a.",
                            color = TextMuted,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(matches, key = { it.id }) { match ->
                MatchCard(
                    match = match,
                    onClick = { onSelectMatch(match) }
                )
            }
        }

        // Haitian Disclaimer Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2A230F)),
                border = BorderStroke(1.dp, TrophyGold.copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = TrophyGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Football Prono Haiti pa yon platfòm pari anliy. Se yon kominote analiz ak prediksyon espòtif. Tout parayè dwe jwe avèk responsablite.",
                        color = Color(0xFFFFE7A1),
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun PlanCard(
    title: String,
    price: String,
    duration: String,
    isPopular: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .testTag("plan_card_${title.replace(" ", "_")}")
            .clickable { onSelect() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPopular) PitchCardHover else PitchCard
        ),
        border = BorderStroke(
            width = if (isPopular) 1.5.dp else 1.dp,
            color = if (isPopular) TrophyGold else PitchCardBorder
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isPopular) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = TrophyGold,
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = "POPILÈ",
                        color = Color(0xFF07140D),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Text(
                text = title,
                color = TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = price,
                color = if (isPopular) TrophyGold else TurfGreenLight,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black
            )

            Text(
                text = duration,
                color = TextMuted,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onSelect,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPopular) TrophyGold else TurfGreenPrimary
                ),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 4.dp, horizontal = 6.dp)
            ) {
                Text(
                    text = "Chwazi",
                    color = Color(0xFF07140D),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
