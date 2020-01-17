package me.hafizdwp.kade_submission_5.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.hafizdwp.kade_submission_5.data.source.MyRepository
import me.hafizdwp.kade_submission_5.ui.favorite.FavoriteViewModel
import me.hafizdwp.kade_submission_5.ui.home.HomeViewModel
import me.hafizdwp.kade_submission_5.ui.league.LeagueDetailViewModel
import me.hafizdwp.kade_submission_5.ui.matches.MatchDetailViewModel
import me.hafizdwp.kade_submission_5.ui.search.SearchViewModel

/**
 * @author hafizdwp
 * 10/07/19
 **/
class ViewModelFactory private constructor(
        private val mApplication: Application,
        private val mRepository: MyRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            with(modelClass) {
                when {

                    isAssignableFrom(HomeViewModel::class.java) ->
                        HomeViewModel(mApplication, mRepository)
                    isAssignableFrom(LeagueDetailViewModel::class.java) ->
                        LeagueDetailViewModel(mApplication, mRepository)
                    isAssignableFrom(MatchDetailViewModel::class.java) ->
                        MatchDetailViewModel(mApplication, mRepository)
                    isAssignableFrom(SearchViewModel::class.java) ->
                        SearchViewModel(mApplication, mRepository)
                    isAssignableFrom(FavoriteViewModel::class.java) ->
                        FavoriteViewModel(mApplication, mRepository)

                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application) =
                INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                    INSTANCE ?: ViewModelFactory(
                            mApplication = application,
                            mRepository = provideRepository(application.applicationContext)
                    )
                            .also { INSTANCE = it }
                }

        fun provideRepository(context: Context): MyRepository {
            return MyRepository.getInstance()
        }
    }
}