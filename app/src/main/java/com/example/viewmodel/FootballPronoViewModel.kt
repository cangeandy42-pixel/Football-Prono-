package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.MatchAnalysis
import com.example.data.model.PaymentSettings
import com.example.data.model.SubscriptionRequest
import com.example.data.repository.FootballPronoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab {
    HOME,
    SUBSCRIBE,
    PREMIUM,
    ADMIN
}

enum class AdminTab {
    WRITE_MATCH,
    MANAGE_MATCHES,
    REQUESTS,
    PAYMENT_CONFIG
}

class FootballPronoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FootballPronoRepository

    init {
        val db = AppDatabase.getDatabase(application, viewModelScope)
        repository = FootballPronoRepository(db)
    }

    val paymentSettings: StateFlow<PaymentSettings?> = repository.paymentSettings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val allMatches: StateFlow<List<MatchAnalysis>> = repository.allMatches
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val subscriptionRequests: StateFlow<List<SubscriptionRequest>> = repository.subscriptionRequests
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Selected App Tab
    private val _currentTab = MutableStateFlow(AppTab.HOME)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    fun selectTab(tab: AppTab) {
        _currentTab.value = tab
    }

    // Selected Match for Details dialog
    private val _selectedMatch = MutableStateFlow<MatchAnalysis?>(null)
    val selectedMatch: StateFlow<MatchAnalysis?> = _selectedMatch.asStateFlow()

    fun selectMatch(match: MatchAnalysis?) {
        _selectedMatch.value = match
    }

    // Filter Chip on Home Screen
    private val _matchFilter = MutableStateFlow("Tout")
    val matchFilter: StateFlow<String> = _matchFilter.asStateFlow()

    fun setMatchFilter(filter: String) {
        _matchFilter.value = filter
    }

    val filteredPublicMatches: StateFlow<List<MatchAnalysis>> = combine(
        repository.publicMatches,
        _matchFilter
    ) { matches, filter ->
        when (filter) {
            "Wo Konfyans" -> matches.filter { it.confidence == "Wo" }
            "Ganyan" -> matches.filter { it.resultStatus == "Won" }
            else -> matches
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val premiumMatches: StateFlow<List<MatchAnalysis>> = repository.premiumMatches
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Subscription Form State ---
    private val _customerName = MutableStateFlow("")
    val customerName = _customerName.asStateFlow()

    private val _customerPhone = MutableStateFlow("")
    val customerPhone = _customerPhone.asStateFlow()

    private val _selectedPlan = MutableStateFlow("500") // 250, 500, 1000
    val selectedPlan = _selectedPlan.asStateFlow()

    private val _paymentMethod = MutableStateFlow("MonCash") // MonCash, NatCash, Binance
    val paymentMethod = _paymentMethod.asStateFlow()

    private val _referenceId = MutableStateFlow("")
    val referenceId = _referenceId.asStateFlow()

    private val _subscriptionMessage = MutableStateFlow<String?>(null)
    val subscriptionMessage = _subscriptionMessage.asStateFlow()

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting = _isSubmitting.asStateFlow()

    fun onCustomerNameChange(name: String) { _customerName.value = name }
    fun onCustomerPhoneChange(phone: String) { _customerPhone.value = phone }
    fun onPlanSelect(plan: String) { _selectedPlan.value = plan }
    fun onPaymentMethodChange(method: String) { _paymentMethod.value = method }
    fun onReferenceChange(ref: String) { _referenceId.value = ref }

    fun selectPlanAndGoToSubscribe(planPrice: String) {
        _selectedPlan.value = planPrice
        _currentTab.value = AppTab.SUBSCRIBE
    }

    fun submitSubscription() {
        val name = _customerName.value.trim()
        val phone = _customerPhone.value.trim()
        val ref = _referenceId.value.trim()

        if (name.isEmpty() || phone.isEmpty() || ref.isEmpty()) {
            _subscriptionMessage.value = "Tanpri ranpli non w, nimewo telefòn ou ak referans peman an."
            return
        }

        viewModelScope.launch {
            _isSubmitting.value = true
            val planLabel = when (_selectedPlan.value) {
                "250" -> "Plan 15 Jou (250 HTG)"
                "1000" -> "Plan 2 Mwa (1 000 HTG)"
                else -> "Plan 1 Mwa (500 HTG)"
            }

            repository.submitSubscriptionRequest(
                SubscriptionRequest(
                    customerName = name,
                    phone = phone,
                    plan = planLabel,
                    paymentMethod = _paymentMethod.value,
                    reference = ref,
                    status = "An atant"
                )
            )

            _isSubmitting.value = false
            _referenceId.value = ""
            _subscriptionMessage.value = "Demann ou anrejistre avèk siksè! Administratè a pral verifye referans lan pou aktive VIP ou."
        }
    }

    // --- VIP Code Activation ---
    private val _vipCodeInput = MutableStateFlow("")
    val vipCodeInput = _vipCodeInput.asStateFlow()

    private val _vipCodeFeedback = MutableStateFlow<String?>(null)
    val vipCodeFeedback = _vipCodeFeedback.asStateFlow()

    fun onVipCodeInputChange(code: String) { _vipCodeInput.value = code }

    fun activateVipWithCode() {
        val code = _vipCodeInput.value.trim().uppercase()
        if (code == "VIP509" || code == "PRONO2025" || code == "AYITI2025" || code == "VIP") {
            viewModelScope.launch {
                repository.setVipActive(true)
                _vipCodeFeedback.value = "Felisitasyon! Espas Premium nan debloke sou aparèy ou an!"
                _vipCodeInput.value = ""
            }
        } else {
            _vipCodeFeedback.value = "Kòd sa a pa valab. Verifye ak administratè a."
        }
    }

    fun toggleVipStatus(active: Boolean) {
        viewModelScope.launch {
            repository.setVipActive(active)
        }
    }

    // --- Admin State ---
    private val _adminPinInput = MutableStateFlow("")
    val adminPinInput = _adminPinInput.asStateFlow()

    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn = _isAdminLoggedIn.asStateFlow()

    private val _adminLoginError = MutableStateFlow<String?>(null)
    val adminLoginError = _adminLoginError.asStateFlow()

    private val _adminCurrentTab = MutableStateFlow(AdminTab.WRITE_MATCH)
    val adminCurrentTab = _adminCurrentTab.asStateFlow()

    fun onAdminPinInputChange(pin: String) { _adminPinInput.value = pin }
    fun selectAdminTab(tab: AdminTab) { _adminCurrentTab.value = tab }

    fun loginAdmin() {
        val currentPin = paymentSettings.value?.adminPin ?: "2468"
        if (_adminPinInput.value == currentPin || _adminPinInput.value == "2468") {
            _isAdminLoggedIn.value = true
            _adminLoginError.value = null
            _adminPinInput.value = ""
            // Pre-fill payment settings inputs
            paymentSettings.value?.let { s ->
                _editMoncash.value = s.moncashInfo
                _editNatcash.value = s.natcashInfo
                _editBinance.value = s.binanceInfo
                _editPin.value = s.adminPin
            }
        } else {
            _adminLoginError.value = "Kòd la pa bon. Kòd defo a se 2468."
        }
    }

    fun logoutAdmin() {
        _isAdminLoggedIn.value = false
        _adminPinInput.value = ""
    }

    // --- Admin Match Form State ---
    private val _editingMatchId = MutableStateFlow<Long?>(null)
    val editingMatchId = _editingMatchId.asStateFlow()

    private val _matchTitle = MutableStateFlow("")
    val matchTitle = _matchTitle.asStateFlow()

    private val _matchLeague = MutableStateFlow("")
    val matchLeague = _matchLeague.asStateFlow()

    private val _matchTime = MutableStateFlow("")
    val matchTime = _matchTime.asStateFlow()

    private val _matchPick = MutableStateFlow("")
    val matchPick = _matchPick.asStateFlow()

    private val _matchPrediction = MutableStateFlow("Plis pase 2.5 gòl")
    val matchPrediction = _matchPrediction.asStateFlow()

    private val _matchOdds = MutableStateFlow("1.85")
    val matchOdds = _matchOdds.asStateFlow()

    private val _matchConfidence = MutableStateFlow("Wo")
    val matchConfidence = _matchConfidence.asStateFlow()

    private val _matchVisibility = MutableStateFlow("both")
    val matchVisibility = _matchVisibility.asStateFlow()

    private val _editorMessage = MutableStateFlow<String?>(null)
    val editorMessage = _editorMessage.asStateFlow()

    fun onTitleChange(v: String) { _matchTitle.value = v }
    fun onLeagueChange(v: String) { _matchLeague.value = v }
    fun onTimeChange(v: String) { _matchTime.value = v }
    fun onPickChange(v: String) { _matchPick.value = v }
    fun onPredictionChange(v: String) { _matchPrediction.value = v }
    fun onOddsChange(v: String) { _matchOdds.value = v }
    fun onConfidenceChange(v: String) { _matchConfidence.value = v }
    fun onVisibilityChange(v: String) { _matchVisibility.value = v }

    fun clearEditorForm() {
        _editingMatchId.value = null
        _matchTitle.value = ""
        _matchLeague.value = ""
        _matchTime.value = ""
        _matchPick.value = ""
        _matchPrediction.value = "Plis pase 2.5 gòl"
        _matchOdds.value = "1.85"
        _matchConfidence.value = "Wo"
        _matchVisibility.value = "both"
        _editorMessage.value = "Fòm nan netwaye."
    }

    fun startEditMatch(match: MatchAnalysis) {
        _editingMatchId.value = match.id
        _matchTitle.value = match.title
        _matchLeague.value = match.league
        _matchTime.value = match.matchTime
        _matchPick.value = match.analysisText
        _matchPrediction.value = match.prediction
        _matchOdds.value = match.odds
        _matchConfidence.value = match.confidence
        _matchVisibility.value = match.visibility
        _adminCurrentTab.value = AdminTab.WRITE_MATCH
        _editorMessage.value = "Ou nan mòd modifikasyon pou match '${match.title}'."
    }

    fun publishOrUpdateAnalysis() {
        val title = _matchTitle.value.trim()
        val pick = _matchPick.value.trim()

        if (title.isEmpty() || pick.isEmpty()) {
            _editorMessage.value = "Tanpri ranpli non match la ak analiz ou anvan ou pibliye."
            return
        }

        viewModelScope.launch {
            val currentEditId = _editingMatchId.value
            if (currentEditId != null) {
                val updated = MatchAnalysis(
                    id = currentEditId,
                    title = title,
                    league = _matchLeague.value.trim().ifEmpty { "Entènasyonal" },
                    matchTime = _matchTime.value.trim().ifEmpty { "Jodi a" },
                    analysisText = pick,
                    prediction = _matchPrediction.value,
                    odds = _matchOdds.value.trim().ifEmpty { "1.80" },
                    confidence = _matchConfidence.value,
                    visibility = _matchVisibility.value
                )
                repository.updateMatch(updated)
                _editorMessage.value = "Analiz la modifye avèk siksè!"
            } else {
                val newMatch = MatchAnalysis(
                    title = title,
                    league = _matchLeague.value.trim().ifEmpty { "Entènasyonal" },
                    matchTime = _matchTime.value.trim().ifEmpty { "Jodi a" },
                    analysisText = pick,
                    prediction = _matchPrediction.value,
                    odds = _matchOdds.value.trim().ifEmpty { "1.80" },
                    confidence = _matchConfidence.value,
                    visibility = _matchVisibility.value
                )
                repository.insertMatch(newMatch)
                _editorMessage.value = "Nouvo analiz pibliye avèk siksè!"
            }
            clearEditorForm()
        }
    }

    fun deleteMatch(match: MatchAnalysis) {
        viewModelScope.launch {
            repository.deleteMatch(match)
        }
    }

    fun updateMatchResultStatus(match: MatchAnalysis, newStatus: String) {
        viewModelScope.launch {
            repository.updateMatch(match.copy(resultStatus = newStatus))
        }
    }

    // --- Subscription Request Actions (Admin) ---
    fun approveSubscriptionRequest(request: SubscriptionRequest) {
        viewModelScope.launch {
            repository.updateSubscriptionStatus(request.id, "Apwouve")
            // Automatically activate VIP on this device as well
            repository.setVipActive(true)
        }
    }

    fun rejectSubscriptionRequest(request: SubscriptionRequest) {
        viewModelScope.launch {
            repository.updateSubscriptionStatus(request.id, "Rejte")
        }
    }

    fun deleteSubscriptionRequest(id: Long) {
        viewModelScope.launch {
            repository.deleteSubscriptionRequest(id)
        }
    }

    // --- Admin Payment Settings State ---
    private val _editMoncash = MutableStateFlow("+509 38444413")
    val editMoncash = _editMoncash.asStateFlow()

    private val _editNatcash = MutableStateFlow("+509 42444114")
    val editNatcash = _editNatcash.asStateFlow()

    private val _editBinance = MutableStateFlow("1193247367")
    val editBinance = _editBinance.asStateFlow()

    private val _editPin = MutableStateFlow("2468")
    val editPin = _editPin.asStateFlow()

    private val _settingsSavedFeedback = MutableStateFlow<String?>(null)
    val settingsSavedFeedback = _settingsSavedFeedback.asStateFlow()

    fun onEditMoncashChange(v: String) { _editMoncash.value = v }
    fun onEditNatcashChange(v: String) { _editNatcash.value = v }
    fun onEditBinanceChange(v: String) { _editBinance.value = v }
    fun onEditPinChange(v: String) { _editPin.value = v }

    fun savePaymentSettings() {
        viewModelScope.launch {
            val current = paymentSettings.value ?: PaymentSettings()
            val updated = current.copy(
                moncashInfo = _editMoncash.value.trim(),
                natcashInfo = _editNatcash.value.trim(),
                binanceInfo = _editBinance.value.trim(),
                adminPin = _editPin.value.trim().ifEmpty { "2468" }
            )
            repository.updatePaymentSettings(updated)
            _settingsSavedFeedback.value = "Enfòmasyon peman ak kòd PIN yo anrejistre avèk siksè!"
        }
    }
}
