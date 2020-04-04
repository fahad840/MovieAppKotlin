package company.com.movieappkotlin.ui.ratedui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import company.com.movieappkotlin.db.entities.Movie
import company.com.movieappkotlin.repository.MovieRepository
import company.com.movieappkotlin.utils.Coroutines

class RatedMoviesViewModel(private val movieRepository: MovieRepository):ViewModel() {

    fun deleteFavMovie(movieId:Int){
        Coroutines.main {
            movieRepository.deletefavMovie(movieId)
        }
    }

    fun getFavMovies(): LiveData<List<Movie>> {
        val favmoviesList= MutableLiveData<List<Movie>>()
        Coroutines.main {
            favmoviesList.value = movieRepository.getfavMovies()
        }
        return favmoviesList
    }
}