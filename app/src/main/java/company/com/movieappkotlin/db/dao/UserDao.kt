package company.com.movieappkotlin.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import company.com.movieappkotlin.db.entities.User
import company.com.movieappkotlin.network.models.UserDetailResponse.CURRENT_USER_ID

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: User) : Long
    @Query("SELECT * FROM user WHERE uid=$CURRENT_USER_ID ")
    fun getUser(): LiveData<User>
    @Query("DELETE FROM user")
    fun deleteUser()
}