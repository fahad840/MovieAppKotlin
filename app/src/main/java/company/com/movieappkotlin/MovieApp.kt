package company.com.movieappkotlin

import android.app.Application
import company.com.movieappkotlin.db.AppDatabase
import company.com.movieappkotlin.network.ApiInterface
import company.com.movieappkotlin.repository.MovieRepository
import company.com.movieappkotlin.repository.SessionRepository
import company.com.movieappkotlin.repository.UserRepository
import company.com.movieappkotlin.ui.moviedetailui.MovieDetailViewModelFactory
import company.com.movieappkotlin.ui.moviesui.MoviesViewModelFactory
import company.com.movieappkotlin.ui.ratedui.RatedMoviesViewModelFactory
import company.com.movieappkotlin.ui.session.SessionViewModelFactory
import company.com.movieappkotlin.utils.SharedPreferenceHelper
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MovieApp : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MovieApp))
        bind() from singleton { ApiInterface() }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { SessionRepository(instance()) }
        bind() from singleton { SharedPreferenceHelper(instance()) }
        bind() from provider { SessionViewModelFactory(instance(),instance()) }
        bind() from singleton { UserRepository(instance(),instance()) }
        bind() from singleton { MovieRepository(instance(),instance()) }
        bind() from provider { MoviesViewModelFactory(instance(),instance(),instance()) }
        bind() from provider { MovieDetailViewModelFactory(instance()) }
        bind() from provider { RatedMoviesViewModelFactory(instance()) }
    }
}

