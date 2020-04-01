package company.com.movieappkotlin.network

import company.com.movieappkotlin.network.models.MovieDetailsResponse.MovieDetailResponse
import company.com.movieappkotlin.network.models.MovieResponse.MovieResponse
import company.com.movieappkotlin.network.models.Sessions.CreateSessionResponse
import company.com.movieappkotlin.network.models.Sessions.RequestTokenResponse
import company.com.movieappkotlin.network.models.Sessions.ValidateRequestTokenResponse
import company.com.movieappkotlin.network.models.UserDetailResponse.UserDetailResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {

    @GET("trending/movie/day")
    suspend fun trendingMovies(@Query("api_key")api_key:String) : Response<MovieResponse>
    @GET("movie/popular")
    suspend fun popularMovies(@Query("api_key")api_key:String) : Response<MovieResponse>
    @GET("movie/upcoming")
    suspend fun upcomingMovies(@Query("api_key")api_key:String) : Response<MovieResponse>
    @GET("movie/{movie_id}")
    suspend fun movieDetail(@Path("movie_id")movie_id:String,@Query("api_key")api_key:String) : Response<MovieDetailResponse>
    @GET("movie/{movie_id}/similar")
    suspend fun similarmovies(@Path("movie_id")movie_id:String,@Query("api_key")api_key:String) : Response<MovieResponse>
    @GET("authentication/token/new")
    suspend fun getRequestToken(@Query("api_key")api_key:String) : Response<RequestTokenResponse>
    @GET("account")
    suspend fun getUserDetail(@Query("api_key")api_key:String, @Query("session_id")sessionid:String) : Response<UserDetailResponse>
    @FormUrlEncoded
    @POST("authentication/session/new")
    suspend fun createSessionId(@Query("api_key")api_key:String, @Field("request_token") requestToken:String) : Response<CreateSessionResponse>
    @FormUrlEncoded
    @POST("authentication/token/validate_with_login")
    suspend fun validateRequestToken(@Query("api_key")api_key:String, @Field("request_token") requestToken:String,@Field("username") username:String,@Field("password") password:String) : Response<ValidateRequestTokenResponse>



    companion object{
        operator fun invoke(): ApiInterface {
           return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .build()
                .create(ApiInterface::class.java)

        }
    }
}