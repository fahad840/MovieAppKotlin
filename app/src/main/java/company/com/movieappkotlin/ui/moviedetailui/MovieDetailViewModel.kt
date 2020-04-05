package company.com.movieappkotlin.ui.moviedetailui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import company.com.movieappkotlin.db.entities.Movie
import company.com.movieappkotlin.network.models.MovieDetailsResponse.MovieDetailResponse
import company.com.movieappkotlin.network.models.MovieResponse.Results
import company.com.movieappkotlin.repository.MovieRepository
import company.com.movieappkotlin.ui.moviesui.MoviesListener
import company.com.movieappkotlin.utils.Constants
import company.com.movieappkotlin.utils.Coroutines

class MovieDetailViewModel(private val movieRepository: MovieRepository):ViewModel() {
    var movieListener:MoviesListener?=null

    fun getMovieDetail(movieId:String): LiveData<MovieDetailResponse> {
        movieListener?.onStarted()
        val moviedetailmutable= MutableLiveData<MovieDetailResponse>()

        Coroutines.main {
            val movieDetailResponse = movieRepository.getMovieDetail(movieId)
            if (movieDetailResponse.isSuccessful) {
                moviedetailmutable.value = movieDetailResponse.body()
            }
            else{
                movieListener?.onFailure("Can't find movie details")

            }
        }
        return moviedetailmutable
    }

    fun getSimilarMovies(movieId:String) : LiveData<List<Results>> {
        movieListener?.onStarted()
        val moviedetailmutable= MutableLiveData<List<Results>>()

        Coroutines.main {
            val similarmovieResponse = movieRepository.getSimilarMovies(movieId)
            if (similarmovieResponse.isSuccessful) {
                moviedetailmutable.value = similarmovieResponse.body()?.results
            }
            else{
                movieListener?.onFailure("Can't find similar movies")

            }
        }
        return moviedetailmutable

    }

    fun saveFavMovie(movie:Movie):Long?{
        var movieId:Long?=null
        Coroutines.main {
            movieId= movieRepository.savefavMovie(movie)
            print("Movie Id : $movieId")
        }
        return movieId
    }

    fun checkFavMovie(movieId: Int):LiveData<Movie> {

        val favmovie = MutableLiveData<Movie>()
        Coroutines.main {
            favmovie.value = movieRepository.checkFavMovie(movieId)
        }
        return favmovie

    }
}