package company.com.movieappkotlin.ui.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import company.com.movieappkotlin.repository.SessionRepository
import company.com.movieappkotlin.utils.SharedPreferenceHelper

@Suppress("UNCHECKED_CAST")
class SessionViewModelFactory (private val repository: SessionRepository,private val sharedPreferenceHelper: SharedPreferenceHelper) :ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SessionViewModel(repository,sharedPreferenceHelper) as T
    }

}