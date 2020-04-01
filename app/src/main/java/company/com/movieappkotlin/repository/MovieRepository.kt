package company.com.movieappkotlin.repository

import company.com.movieappkotlin.db.AppDatabase
import company.com.movieappkotlin.network.ApiInterface
import company.com.movieappkotlin.network.models.MovieDetailsResponse.MovieDetailResponse
import company.com.movieappkotlin.network.models.MovieResponse.MovieResponse
import company.com.movieappkotlin.utils.Constants
import retrofit2.Response

class MovieRepository(private val apiInterface: ApiInterface,private val appDatabase: AppDatabase) {

    suspend fun getTrendingMovies():Response<MovieResponse>{
        return apiInterface.trendingMovies(Constants.API_KEY)
    }
    suspend fun getUpcomingMovies() : Response<MovieResponse>{
        return apiInterface.upcomingMovies(Constants.API_KEY)
    }

    suspend fun getPopularMovies():Response<MovieResponse>{
        return apiInterface.popularMovies(Constants.API_KEY)
    }

    suspend fun getMovieDetail(movie_id:String):Response<MovieDetailResponse>{
        return apiInterface.movieDetail(movie_id,Constants.API_KEY)
    }

    suspend fun getSimilarMovies(movie_id:String):Response<MovieResponse>{
        return apiInterface.similarmovies(movie_id,Constants.API_KEY)
    }
}