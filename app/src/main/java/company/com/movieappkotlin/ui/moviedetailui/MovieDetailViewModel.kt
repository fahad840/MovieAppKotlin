package company.com.movieappkotlin.ui.moviedetailui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import company.com.movieappkotlin.network.models.MovieDetailsResponse.MovieDetailResponse
import company.com.movieappkotlin.network.models.MovieResponse.Results
import company.com.movieappkotlin.repository.MovieRepository
import company.com.movieappkotlin.utils.Constants
import company.com.movieappkotlin.utils.Coroutines

class MovieDetailViewModel(private val movieRepository: MovieRepository):ViewModel() {

    fun getMovieDetail(movieId:String): LiveData<MovieDetailResponse> {
        val moviedetailmutable= MutableLiveData<MovieDetailResponse>()

        Coroutines.main {
            val movieDetailResponse = movieRepository.getMovieDetail(movieId)
            moviedetailmutable.value=movieDetailResponse.body()
        }
        return moviedetailmutable
    }

    fun getSimilarMovies(movieId:String) : LiveData<List<Results>> {
        val moviedetailmutable= MutableLiveData<List<Results>>()

        Coroutines.main {
            val similarmovieResponse = movieRepository.getSimilarMovies(movieId)
            moviedetailmutable.value=similarmovieResponse.body()?.results
        }
        return moviedetailmutable

    }
}