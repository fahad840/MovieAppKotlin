package company.com.movieappkotlin.network.models.Sessions

data class ValidateRequestTokenResponse(val success:Boolean , val expires_at:String , val request_token:String)