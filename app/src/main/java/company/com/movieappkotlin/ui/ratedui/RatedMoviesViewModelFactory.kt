package company.com.movieappkotlin.ui.ratedui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import company.com.movieappkotlin.repository.MovieRepository

@Suppress("UNCHECKED_CAST")
class RatedMoviesViewModelFactory (private val movieRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RatedMoviesViewModel(movieRepository) as T
    }

}