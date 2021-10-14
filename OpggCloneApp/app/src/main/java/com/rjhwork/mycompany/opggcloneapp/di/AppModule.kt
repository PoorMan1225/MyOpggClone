package com.rjhwork.mycompany.opggcloneapp.di

import android.app.Activity
import androidx.viewbinding.BuildConfig
import com.rjhwork.mycompany.opggcloneapp.data.api.DataDragonApi
import com.rjhwork.mycompany.opggcloneapp.data.api.RiotApi
import com.rjhwork.mycompany.opggcloneapp.data.api.RiotMatchApi
import com.rjhwork.mycompany.opggcloneapp.data.api.Url
import com.rjhwork.mycompany.opggcloneapp.data.db.AppDatabase
import com.rjhwork.mycompany.opggcloneapp.data.preference.PreferenceManager
import com.rjhwork.mycompany.opggcloneapp.data.preference.SharedPreferenceManager
import com.rjhwork.mycompany.opggcloneapp.data.repository.*
import com.rjhwork.mycompany.opggcloneapp.domain.usecase.*
import com.rjhwork.mycompany.opggcloneapp.presentation.addsummoner.AddSummonerActivity
import com.rjhwork.mycompany.opggcloneapp.presentation.addsummoner.AddSummonerContract
import com.rjhwork.mycompany.opggcloneapp.presentation.addsummoner.AddSummonerPresenter
import com.rjhwork.mycompany.opggcloneapp.presentation.favorite.FavoriteContract
import com.rjhwork.mycompany.opggcloneapp.presentation.favorite.FavoritePresenter
import com.rjhwork.mycompany.opggcloneapp.presentation.favorite.FavoritesActivity
import com.rjhwork.mycompany.opggcloneapp.presentation.ranking.RankingContract
import com.rjhwork.mycompany.opggcloneapp.presentation.ranking.RankingFragment
import com.rjhwork.mycompany.opggcloneapp.presentation.ranking.RankingPresenter
import com.rjhwork.mycompany.opggcloneapp.presentation.search.SearchContract
import com.rjhwork.mycompany.opggcloneapp.presentation.search.SearchFragment
import com.rjhwork.mycompany.opggcloneapp.presentation.search.SearchPresenter
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatch.SummonerMatchActivity
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatch.SummonerMatchContract
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatch.SummonerMatchPresenter
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.SummonerMatchDetailActivity
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.SummonerMatchDetailContract
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.SummonerMatchDetailPresenter
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis.MatchAnalysisContract
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis.MatchAnalysisFragment
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis.MatchAnalysisPresenter
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchdetailfragment.SummonerMatchDetailFragment
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchdetailfragment.SummonerMatchDetailFragmentContract
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchdetailfragment.SummonerMatchDetailFragmentPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module {
    single { Dispatchers.IO }

    // Api
    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if(BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
    }

    // Database
    single { AppDatabase.build(androidApplication())}
    single { get<AppDatabase>().favoriteDao()}

    single<RiotApi> {
        Retrofit.Builder()
            .baseUrl(Url.RIOT_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create()
    }

    single<DataDragonApi> {
        Retrofit.Builder().baseUrl(Url.RIOT_DATA_DRAGON_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create()
    }

    single<RiotMatchApi> {
        Retrofit.Builder().baseUrl(Url.RIOT_MATCH_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create()
    }
}

val domainModule = module {
    factory { GetSummonerProfileByPuuid(get()) }
    factory { GetSummonerProfileByName(get()) }
    factory { GetSummonerMatchesList(get()) }
    factory { GetSummonerMatchMostData(get(), get())}
    factory { GetSummonerProfileLeagueData(get())}
    factory { GetSummonerMatchData(get(),get()) }

    // favorite data
    factory { InsertFavoriteData(get()) }
    factory { DeleteFavoriteData(get()) }
    factory { GetAllFavoriteData(get()) }
    factory { GetTrackingFavoriteData(get()) }
    factory { UpdateFavoriteData(get()) }
    factory { GetFavoriteFilterItems(get()) }

    // match Data
    factory { GetRuneData(get()) }
    factory { GetMatchDataByMatchId(get()) }
    factory { GetKillAssistRate() }
    factory { GetParticipantMatchData() }
    factory { GetSummonerAllSpellData(get()) }
    factory { GetRune1IconImageUrl() }
    factory { GetFavoriteByName(get()) }
    factory { GetItemsData() }
    factory { GetMatchDataDetail(get(),get(),get(),get(),get(),get(),get(),get()) }

    // ranking Data
    factory { GetChallengerRankingData(get()) }
    factory { GetGrandMasterRankingData(get()) }
    factory { GetMasterRankingData(get()) }
}

val dataModule = module {
    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManager(get())}
    single<SummonerProfileRepo> { SummonerProfileRepoImpl(get(), get(), get())}
    single<FavoriteRepo> { FavoriteRepoImpl(get(),get())}
    single<MatchDataRepo> { MatchDataImpl(get(),get())}
    single<RankingDataRepo> { RankingDataRepoImpl(get(), get())}
}

val presenterModule = module {
    scope<SearchFragment> {
        scoped<SearchContract.Presenter>  { SearchPresenter(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    }

    scope<AddSummonerActivity> {
        scoped<AddSummonerContract.Presenter> {
            AddSummonerPresenter(getSource(), get(), get(), get(), get(), get(), get())
        }
    }

    scope<FavoritesActivity> {
        scoped<FavoriteContract.Presenter> {
            FavoritePresenter(getSource(), get(), get(), get(), get(), get(), get(), get())
        }
    }

    scope<SummonerMatchActivity> {
        scoped<SummonerMatchContract.Presenter> {
            SummonerMatchPresenter(get(), get(), get(), get(), get(), get(), get(), get())
        }
    }

    scope<SummonerMatchDetailActivity> {
        scoped<SummonerMatchDetailContract.Presenter> {
            SummonerMatchDetailPresenter(get(), get(), get())
        }
    }

    scope<SummonerMatchDetailFragment> {
        scoped<SummonerMatchDetailFragmentContract.Presenter> {
            SummonerMatchDetailFragmentPresenter(get(), get(), get(), get(), get(), get(), get())
        }
    }

    scope<MatchAnalysisFragment> {
        scoped<MatchAnalysisContract.Presenter> {
            MatchAnalysisPresenter(get(),get())
        }
    }

    scope<RankingFragment> {
        scoped<RankingContract.Presenter> {
            RankingPresenter(get(), get(), get(), get())
        }
    }

}