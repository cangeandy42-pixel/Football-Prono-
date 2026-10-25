package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.MatchDetailDialog
import com.example.ui.components.TopHeader
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PremiumScreen
import com.example.ui.screens.SubscribeScreen
import com.example.ui.theme.FootballPronoTheme
import com.example.ui.theme.PitchCard
import com.example.ui.theme.PitchCardBorder
import com.example.ui.theme.PitchDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TrophyGold
import com.example.ui.theme.TurfGreenLight
import com.example.ui.theme.TurfGreenPrimary
import com.example.viewmodel.AppTab
import com.example.viewmodel.FootballPronoViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: FootballPronoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FootballPronoTheme {
                MainApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainApp(viewModel: FootballPronoViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()
    val paymentSettings by viewModel.paymentSettings.collectAsState()
    val selectedMatch by viewModel.selectedMatch.collectAsState()

    val isVipActive = paymentSettings?.isVipActive ?: false

    // Back handling: if in sub-tab, back goes to HOME
    BackHandler(enabled = currentTab != AppTab.HOME) {
        viewModel.selectTab(AppTab.HOME)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(PitchDark),
        topBar = {
            TopHeader(isVipActive = isVipActive)
        },
        bottomBar = {
            NavigationBar(
                containerColor = PitchCard,
                tonalElevation = 8.dp,
                modifier = Modifier.testTag("bottom_nav")
            ) {
                // Home Tab
                NavigationBarItem(
                    selected = currentTab == AppTab.HOME,
                    onClick = { viewModel.selectTab(AppTab.HOME) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Akèy") },
                    label = {
                        Text(
                            text = "Akèy",
                            fontSize = 11.sp,
                            fontWeight = if (currentTab == AppTab.HOME) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF07140D),
                        selectedTextColor = TurfGreenLight,
                        indicatorColor = TurfGreenPrimary,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    ),
                    modifier = Modifier.testTag("tab_home")
                )

                // Subscribe Tab
                NavigationBarItem(
                    selected = currentTab == AppTab.SUBSCRIBE,
                    onClick = { viewModel.selectTab(AppTab.SUBSCRIBE) },
                    icon = { Icon(Icons.Default.CardMembership, contentDescription = "Abònman") },
                    label = {
                        Text(
                            text = "Abònman",
                            fontSize = 11.sp,
                            fontWeight = if (currentTab == AppTab.SUBSCRIBE) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF07140D),
                        selectedTextColor = TurfGreenLight,
                        indicatorColor = TurfGreenPrimary,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    ),
                    modifier = Modifier.testTag("tab_subscribe")
                )

                // Premium Tab
                NavigationBarItem(
                    selected = currentTab == AppTab.PREMIUM,
                    onClick = { viewModel.selectTab(AppTab.PREMIUM) },
                    icon = { Icon(Icons.Default.WorkspacePremium, contentDescription = "Premium") },
                    label = {
                        Text(
                            text = "Premium",
                            fontSize = 11.sp,
                            fontWeight = if (currentTab == AppTab.PREMIUM) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF07140D),
                        selectedTextColor = TrophyGold,
                        indicatorColor = TrophyGold,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    ),
                    modifier = Modifier.testTag("tab_premium")
                )

                // Admin Tab
                NavigationBarItem(
                    selected = currentTab == AppTab.ADMIN,
                    onClick = { viewModel.selectTab(AppTab.ADMIN) },
                    icon = { Icon(Icons.Default.AdminPanelSettings, contentDescription = "Admin") },
                    label = {
                        Text(
                            text = "Admin",
                            fontSize = 11.sp,
                            fontWeight = if (currentTab == AppTab.ADMIN) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF07140D),
                        selectedTextColor = TurfGreenLight,
                        indicatorColor = TurfGreenPrimary,
                        unselectedIconColor = TextMuted,
                        unselectedTextColor = TextMuted
                    ),
                    modifier = Modifier.testTag("tab_admin")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PitchDark)
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AppTab.HOME -> {
                    val publicMatches by viewModel.filteredPublicMatches.collectAsState()
                    val selectedFilter by viewModel.matchFilter.collectAsState()

                    HomeScreen(
                        matches = publicMatches,
                        selectedFilter = selectedFilter,
                        onFilterChange = { viewModel.setMatchFilter(it) },
                        onChoosePlan = { plan -> viewModel.selectPlanAndGoToSubscribe(plan) },
                        onSelectMatch = { match -> viewModel.selectMatch(match) }
                    )
                }

                AppTab.SUBSCRIBE -> {
                    val customerName by viewModel.customerName.collectAsState()
                    val customerPhone by viewModel.customerPhone.collectAsState()
                    val selectedPlan by viewModel.selectedPlan.collectAsState()
                    val paymentMethod by viewModel.paymentMethod.collectAsState()
                    val referenceId by viewModel.referenceId.collectAsState()
                    val isSubmitting by viewModel.isSubmitting.collectAsState()
                    val subscriptionMessage by viewModel.subscriptionMessage.collectAsState()
                    val vipCodeInput by viewModel.vipCodeInput.collectAsState()
                    val vipCodeFeedback by viewModel.vipCodeFeedback.collectAsState()

                    SubscribeScreen(
                        customerName = customerName,
                        onCustomerNameChange = { viewModel.onCustomerNameChange(it) },
                        customerPhone = customerPhone,
                        onCustomerPhoneChange = { viewModel.onCustomerPhoneChange(it) },
                        selectedPlan = selectedPlan,
                        onPlanSelect = { viewModel.onPlanSelect(it) },
                        paymentMethod = paymentMethod,
                        onPaymentMethodChange = { viewModel.onPaymentMethodChange(it) },
                        referenceId = referenceId,
                        onReferenceChange = { viewModel.onReferenceChange(it) },
                        onSubmitSubscription = { viewModel.submitSubscription() },
                        isSubmitting = isSubmitting,
                        subscriptionMessage = subscriptionMessage,
                        paymentSettings = paymentSettings,
                        vipCodeInput = vipCodeInput,
                        onVipCodeInputChange = { viewModel.onVipCodeInputChange(it) },
                        onActivateVipWithCode = { viewModel.activateVipWithCode() },
                        vipCodeFeedback = vipCodeFeedback
                    )
                }

                AppTab.PREMIUM -> {
                    val premiumMatches by viewModel.premiumMatches.collectAsState()

                    PremiumScreen(
                        isVipActive = isVipActive,
                        premiumMatches = premiumMatches,
                        onGoToSubscribe = { viewModel.selectTab(AppTab.SUBSCRIBE) },
                        onToggleVip = { viewModel.toggleVipStatus(it) },
                        onSelectMatch = { match -> viewModel.selectMatch(match) }
                    )
                }

                AppTab.ADMIN -> {
                    val isAdminLoggedIn by viewModel.isAdminLoggedIn.collectAsState()
                    val adminPinInput by viewModel.adminPinInput.collectAsState()
                    val adminLoginError by viewModel.adminLoginError.collectAsState()
                    val adminCurrentTab by viewModel.adminCurrentTab.collectAsState()

                    val editingMatchId by viewModel.editingMatchId.collectAsState()
                    val matchTitle by viewModel.matchTitle.collectAsState()
                    val matchLeague by viewModel.matchLeague.collectAsState()
                    val matchTime by viewModel.matchTime.collectAsState()
                    val matchPick by viewModel.matchPick.collectAsState()
                    val matchPrediction by viewModel.matchPrediction.collectAsState()
                    val matchOdds by viewModel.matchOdds.collectAsState()
                    val matchConfidence by viewModel.matchConfidence.collectAsState()
                    val matchVisibility by viewModel.matchVisibility.collectAsState()
                    val editorMessage by viewModel.editorMessage.collectAsState()

                    val allMatches by viewModel.allMatches.collectAsState()
                    val subscriptionRequests by viewModel.subscriptionRequests.collectAsState()

                    val editMoncash by viewModel.editMoncash.collectAsState()
                    val editNatcash by viewModel.editNatcash.collectAsState()
                    val editBinance by viewModel.editBinance.collectAsState()
                    val editPin by viewModel.editPin.collectAsState()
                    val settingsSavedFeedback by viewModel.settingsSavedFeedback.collectAsState()

                    AdminScreen(
                        isAdminLoggedIn = isAdminLoggedIn,
                        adminPinInput = adminPinInput,
                        onAdminPinChange = { viewModel.onAdminPinInputChange(it) },
                        onAdminLogin = { viewModel.loginAdmin() },
                        onAdminLogout = { viewModel.logoutAdmin() },
                        adminLoginError = adminLoginError,

                        adminTab = adminCurrentTab,
                        onSelectAdminTab = { viewModel.selectAdminTab(it) },

                        editingMatchId = editingMatchId,
                        matchTitle = matchTitle,
                        onTitleChange = { viewModel.onTitleChange(it) },
                        matchLeague = matchLeague,
                        onLeagueChange = { viewModel.onLeagueChange(it) },
                        matchTime = matchTime,
                        onTimeChange = { viewModel.onTimeChange(it) },
                        matchPick = matchPick,
                        onPickChange = { viewModel.onPickChange(it) },
                        matchPrediction = matchPrediction,
                        onPredictionChange = { viewModel.onPredictionChange(it) },
                        matchOdds = matchOdds,
                        onOddsChange = { viewModel.onOddsChange(it) },
                        matchConfidence = matchConfidence,
                        onConfidenceChange = { viewModel.onConfidenceChange(it) },
                        matchVisibility = matchVisibility,
                        onVisibilityChange = { viewModel.onVisibilityChange(it) },
                        onPublishAnalysis = { viewModel.publishOrUpdateAnalysis() },
                        onClearEditor = { viewModel.clearEditorForm() },
                        editorMessage = editorMessage,

                        allMatches = allMatches,
                        onStartEditMatch = { viewModel.startEditMatch(it) },
                        onDeleteMatch = { viewModel.deleteMatch(it) },
                        onStatusChange = { match, status -> viewModel.updateMatchResultStatus(match, status) },

                        subscriptionRequests = subscriptionRequests,
                        onApproveRequest = { viewModel.approveSubscriptionRequest(it) },
                        onRejectRequest = { viewModel.rejectSubscriptionRequest(it) },
                        onDeleteRequest = { viewModel.deleteSubscriptionRequest(it) },

                        editMoncash = editMoncash,
                        onEditMoncashChange = { viewModel.onEditMoncashChange(it) },
                        editNatcash = editNatcash,
                        onEditNatcashChange = { viewModel.onEditNatcashChange(it) },
                        editBinance = editBinance,
                        onEditBinanceChange = { viewModel.onEditBinanceChange(it) },
                        editPin = editPin,
                        onEditPinChange = { viewModel.onEditPinChange(it) },
                        onSavePaymentSettings = { viewModel.savePaymentSettings() },
                        settingsSavedFeedback = settingsSavedFeedback
                    )
                }
            }

            // Dialog for full match analysis
            selectedMatch?.let { match ->
                MatchDetailDialog(
                    match = match,
                    onDismiss = { viewModel.selectMatch(null) }
                )
            }
        }
    }
}
