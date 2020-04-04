package company.com.movieappkotlin.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import company.com.movieappkotlin.db.entities.Movie
import company.com.movieappkotlin.db.entities.User
import company.com.movieappkotlin.network.models.UserDetailResponse.CURRENT_USER_ID

@Dao
interface MovieDao {
    @Insert
     suspend fun insert(movie: Movie) : Long
    @Query("SELECT * FROM movie WHERE movieid=:movieId")
     suspend fun checkMovie(movieId:Int?): Movie
    @Query("SELECT * FROM movie")
     suspend fun getFavMovies(): List<Movie>
    @Query("DELETE FROM movie Where movieid=:movieId")
     suspend fun deleteFavMovie(movieId: Int?)

}