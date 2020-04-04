package company.com.movieappkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import company.com.movieappkotlin.db.dao.MovieDao
import company.com.movieappkotlin.db.dao.UserDao
import company.com.movieappkotlin.db.entities.Movie
import company.com.movieappkotlin.db.entities.User
import company.com.movieappkotlin.network.models.UserDetailResponse.UserDetailResponse

@Database(entities = [User::class,Movie::class],version = 5,exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getMovieDao():MovieDao

    companion object{
        @Volatile
        private var instance:AppDatabase?=null
        private val LOCK =Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance=it
            }
        }

        private fun buildDatabase(context: Context)=
            Room.databaseBuilder(context,
                AppDatabase::class.java,
                "Mydatabase.db"
            ).fallbackToDestructiveMigration()
                .build()
    }

}