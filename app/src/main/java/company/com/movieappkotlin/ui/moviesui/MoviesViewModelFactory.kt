package company.com.movieappkotlin.ui.moviesui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import company.com.movieappkotlin.repository.MovieRepository
import company.com.movieappkotlin.repository.UserRepository
import company.com.movieappkotlin.utils.SharedPreferenceHelper

@Suppress("UNCHECKED_CAST")
class MoviesViewModelFactory (private val userRepository: UserRepository,private val movieRepository: MovieRepository,private val preferenceHelper: SharedPreferenceHelper) :
    ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesViewModel(userRepository,movieRepository,preferenceHelper) as T
    }

}