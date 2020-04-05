package company.com.movieappkotlin.ui.moviesui

interface MoviesListener {
    fun onSuccess()
    fun onStarted()
    fun onFailure(message:String)
}