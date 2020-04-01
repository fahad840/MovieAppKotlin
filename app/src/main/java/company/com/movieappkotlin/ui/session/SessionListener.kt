package company.com.movieappkotlin.ui.session

interface SessionListener {
    fun onSuccess()
    fun onStarted()
    fun onFailure(message:String)
}