package company.com.movieappkotlin.ui.session

import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.ViewModel
import company.com.movieappkotlin.repository.SessionRepository
import company.com.movieappkotlin.utils.Constants
import company.com.movieappkotlin.utils.Coroutines
import company.com.movieappkotlin.utils.SharedPreferenceHelper

class SessionViewModel(private val sessionRepository: SessionRepository,private val sharedPreferenceHelper: SharedPreferenceHelper): ViewModel() {
    var username:String?=null
    var password:String?=null
    var sessionListener:SessionListener?=null


    fun onLoginPress(view:View){
        sessionListener?.onStarted()
        if (username.isNullOrEmpty() || password.isNullOrEmpty() )
        {
            sessionListener?.onFailure("username or password is empty.")

        }
        else{
            Coroutines.main {
                val response= sessionRepository.createRequestToken()
                print(response.message())
                if (response.isSuccessful){
                    if (response.body()!!.success) {
                        val validateTokenResponse=sessionRepository.validateRequestToken(username!!,password!!,response.body()?.request_token!!)
                      if (validateTokenResponse.isSuccessful) {
                          if (validateTokenResponse.body()!!.success) {
                              val createTokenResponse=sessionRepository.createSessionId(validateTokenResponse.body()?.request_token!!)
                             if (createTokenResponse.isSuccessful){
                                 if (createTokenResponse.body()!!.success){
                                     sharedPreferenceHelper.save(Constants.SESSION_ID,createTokenResponse.body()!!.session_id)
                                     sessionListener?.onSuccess()
                                 }
                                 else{
                                     sessionListener?.onFailure("Session Id not created")
                                 }

                             }
                          }
                      }
                        else{
                          sessionListener?.onFailure("username or password is incorrect")
                      }
                    }

                }

            }
        }
    }

    fun validateUser():Boolean{
        if (sharedPreferenceHelper.getValueString(Constants.SESSION_ID)!=null)
        {
            return true
        }
        return false
    }

}