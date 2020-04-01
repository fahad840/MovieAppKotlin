package company.com.movieappkotlin.repository

import company.com.movieappkotlin.db.AppDatabase
import company.com.movieappkotlin.db.entities.User
import company.com.movieappkotlin.network.ApiInterface
import company.com.movieappkotlin.network.models.UserDetailResponse.UserDetailResponse
import company.com.movieappkotlin.utils.Constants
import retrofit2.Response

class UserRepository(private val apiInterface: ApiInterface,private val appDatabase: AppDatabase) {

    suspend fun getUserDetails(sessionId:String):Response<UserDetailResponse>{
        return apiInterface.getUserDetail(Constants.API_KEY,sessionId)
    }

    suspend fun saveUser(user:User){ appDatabase.getUserDao().upsert(user)}

    fun getUser() = appDatabase.getUserDao().getUser()
    fun deleteUser()=appDatabase.getUserDao().deleteUser()
}