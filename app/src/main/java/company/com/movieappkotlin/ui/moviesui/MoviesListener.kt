package company.com.movieappkotlin.ui.moviesui

interface MoviesListener {
    fun onSuccess()
    fun onFailure(message:String)
}