package company.com.movieappkotlin.repository

import company.com.movieappkotlin.network.ApiInterface
import company.com.movieappkotlin.network.models.Sessions.CreateSessionResponse
import company.com.movieappkotlin.network.models.Sessions.RequestTokenResponse
import company.com.movieappkotlin.network.models.Sessions.ValidateRequestTokenResponse
import company.com.movieappkotlin.utils.Constants
import retrofit2.Response

class SessionRepository (private val apiInterface: ApiInterface) {

    suspend fun createRequestToken() : Response<RequestTokenResponse> {
         return apiInterface.getRequestToken(Constants.API_KEY)
    }

    suspend fun createSessionId(requestToken:String): Response<CreateSessionResponse> {
        return apiInterface.createSessionId(Constants.API_KEY,requestToken)
    }

    suspend fun validateRequestToken(username:String,password:String,requestToken: String) : Response<ValidateRequestTokenResponse>{
      return  apiInterface.validateRequestToken(Constants.API_KEY,requestToken,username,password)

    }

}