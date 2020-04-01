package company.com.movieappkotlin.ui.moviesui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import company.com.movieappkotlin.db.entities.User
import company.com.movieappkotlin.network.models.MovieResponse.MovieResponse
import company.com.movieappkotlin.network.models.MovieResponse.Results
import company.com.movieappkotlin.network.models.UserDetailResponse.UserDetailResponse
import company.com.movieappkotlin.repository.MovieRepository
import company.com.movieappkotlin.repository.UserRepository
import company.com.movieappkotlin.utils.Constants
import company.com.movieappkotlin.utils.Coroutines
import company.com.movieappkotlin.utils.SharedPreferenceHelper

class MoviesViewModel(private val userRepository: UserRepository,private val movieRepository: MovieRepository,private val preferenceHelper: SharedPreferenceHelper) :ViewModel() {

    var moviesListener:MoviesListener?=null

    fun getUserDetails(){
        Coroutines.main {
            val resposne=userRepository.getUserDetails(preferenceHelper.getValueString(Constants.SESSION_ID)!!)
            print(Gson().newBuilder().setPrettyPrinting().create().toJson(resposne))
            if (resposne.isSuccessful){
                val userData=resposne.body()
                userRepository.saveUser(User(userData?.name,userData?.username,userData?.id,userData?.avatar?.gravatar?.hash))
                moviesListener?.onSuccess()
            }
            else{
                moviesListener?.onFailure("Failed to get User")
            }
        }
    }
    fun getLoggedInUser() = userRepository.getUser()
    fun deleteUser(){
        userRepository.deleteUser()
        preferenceHelper.removeValue(Constants.SESSION_ID)
    }

    fun getPopularMovies():LiveData<List<Results>>{
        val movesmutable= MutableLiveData<List<Results>>()
        Coroutines.main {
            val response=movieRepository.getPopularMovies()
            print(Gson().newBuilder().setPrettyPrinting().create().toJson(response))
            if (response.isSuccessful){
                movesmutable.value=response.body()?.results
            }
            else{
                moviesListener?.onFailure("Failed to get movies")
            }
        }
        return movesmutable
    }

    fun getTrendingMovies():LiveData<List<Results>>{
        val movesmutable= MutableLiveData<List<Results>>()
        Coroutines.main {
            val response=movieRepository.getTrendingMovies()
            print(Gson().newBuilder().setPrettyPrinting().create().toJson(response))

            if (response.isSuccessful){
                movesmutable.value=response.body()?.results
            }
            else{
                moviesListener?.onFailure("Failed to get movies")
            }
        }
        return movesmutable
    }

    fun getUpcomingMovies():LiveData<List<Results>>{
        val movesmutable= MutableLiveData<List<Results>>()
        Coroutines.main {
            val response=movieRepository.getUpcomingMovies()
            print(Gson().newBuilder().setPrettyPrinting().create().toJson(response))

            if (response.isSuccessful){
                movesmutable.value=response.body()?.results
            }
            else{
                moviesListener?.onFailure("Failed to get movies")
            }
        }
        return movesmutable
    }


}