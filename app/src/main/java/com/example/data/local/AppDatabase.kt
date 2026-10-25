package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.MatchAnalysis
import com.example.data.model.PaymentSettings
import com.example.data.model.SubscriptionRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [MatchAnalysis::class, SubscriptionRequest::class, PaymentSettings::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun matchDao(): MatchDao
    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun paymentSettingsDao(): PaymentSettingsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "football_prono_haiti.db"
                )
                    .addCallback(DatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialData(database)
                    }
                }
            }

            suspend fun populateInitialData(db: AppDatabase) {
                // Initialize default payment settings
                db.paymentSettingsDao().insertOrUpdate(
                    PaymentSettings(
                        id = 1,
                        moncashInfo = "+509 38444413",
                        natcashInfo = "+509 42444114",
                        binanceInfo = "1193247367",
                        adminPin = "2468",
                        isVipActive = false,
                        supportPhone = "+509 38444413"
                    )
                )

                // Initialize sample matches
                val initialMatches = listOf(
                    MatchAnalysis(
                        id = 1,
                        title = "Barcelona vs Real Madrid",
                        league = "La Liga — Espay",
                        matchTime = "26 Oktòb • 15:00",
                        analysisText = "El Clásico sa a ap trè cho! Barcelona gen yon fòm ofansif enkwayab sezon sa a ak Lewandowski ak Lamine Yamal. Real Madrid bò kote pa yo gen Vinicius ak Mbappé ki trè danjere sou kontratak.\n\nEstatistik montre nan 5 dènye rankont ant 2 ekip yo, toujou gen omwen 3 gòl ki make. Defans Barcelona a monte trè wo, sa ki ouvri espas pou atak Madrid la. Nou prevwa yon match ak anpil entansite ak anpil okazyon.",
                        prediction = "Plis pase 2.5 gòl",
                        odds = "1.65",
                        confidence = "Wo",
                        visibility = "both",
                        resultStatus = "Pending"
                    ),
                    MatchAnalysis(
                        id = 2,
                        title = "Arsenal vs Chelsea",
                        league = "Premier League — Angletè",
                        matchTime = "26 Oktòb • 12:30",
                        analysisText = "Derby vil Lonn! Arsenal ap jwe lakay li nan Emirates Stadium kote yo solid anpil nan defans. Chelsea ap amelyore men yo gen feblès sou kout pyebal rete ak kontwòl mitan tèren an.\n\nMikel Arteta prepare yon plan pou domine depi nan premye minit yo. Saka ak Martinelli ap peze defans Chelsea a anpil. Viktwa Arsenal parèt trè pwobab.",
                        prediction = "1 — Viktwa ekip lakay",
                        odds = "1.78",
                        confidence = "Wo",
                        visibility = "public",
                        resultStatus = "Pending"
                    ),
                    MatchAnalysis(
                        id = 3,
                        title = "PSG vs Marseille",
                        league = "Ligue 1 — Frans",
                        matchTime = "27 Oktòb • 14:45",
                        analysisText = "Le Classique fransè a! Marseille ap jwe lakay li nan Stade Vélodrome ak yon gwo anbyans. PSG toujou gen talan endividyèl ki fè diferans lan men defans yo pran gòl nan prèske chak gwo match.\n\n2 ekip yo toujou jwenn chemen filè a nan konfwontasyon sa yo.",
                        prediction = "Chak ekip make (BTTS)",
                        odds = "1.72",
                        confidence = "Mwayen",
                        visibility = "public",
                        resultStatus = "Pending"
                    ),
                    MatchAnalysis(
                        id = 4,
                        title = "Manchester City vs Liverpool",
                        league = "Premier League — VIP Tikè",
                        matchTime = "27 Oktòb • 11:30",
                        analysisText = "MATCH VIP PREMIUM: Gwo chòk pou premye plas nan chanpyona angle a! Pep Guardiola kont Arne Slot. Haaland ap make nan prèske tout match, men defans City a pran gòl sou tranzisyon rapid Salah.\n\nEstrateji nou prevwa: Match sa a ap gen anpil gòl ak aksyon rapid nan toulede kan yo. Nou konseye jwe 'Toulede ekip make epi plis pase 2.5 gòl' oswa Viktwa City/Match Nil.",
                        prediction = "Double chans 1X & Plis 1.5",
                        odds = "2.10",
                        confidence = "Wo",
                        visibility = "premium",
                        resultStatus = "Pending"
                    ),
                    MatchAnalysis(
                        id = 5,
                        title = "Inter Milan vs Juventus",
                        league = "Serie A — Itali",
                        matchTime = "27 Oktòb • 13:45",
                        analysisText = "Derby d'Italia! Juventus anba Thiago Motta ap jwe yon foutbòl taktik ki byen fèmen, yo pran trè kèk gòl. Inter gen yon ekip konplè men yo respekte Juventus anpil.\n\nNou atann yon match trè sere kote p ap gen anpil espas ni anpil gòl.",
                        prediction = "MWENS pase 2.5 gòl",
                        odds = "1.80",
                        confidence = "Mwayen",
                        visibility = "premium",
                        resultStatus = "Pending"
                    )
                )
                db.matchDao().insertAll(initialMatches)
            }
        }
    }
}
