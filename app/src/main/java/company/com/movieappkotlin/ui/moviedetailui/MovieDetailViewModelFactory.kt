package company.com.movieappkotlin.ui.moviedetailui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import company.com.movieappkotlin.repository.MovieRepository

@Suppress("UNCHECKED_CAST")
class MovieDetailViewModelFactory (private val movieRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(movieRepository) as T
    }

}