package company.com.movieappkotlin.ui.moviedetailui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import company.com.movieappkotlin.R
import company.com.movieappkotlin.adapter.MovieAdapter
import company.com.movieappkotlin.databinding.FragmentMovieDetailBinding
import company.com.movieappkotlin.db.entities.Movie
import company.com.movieappkotlin.ui.moviesui.MoviesListener
import company.com.movieappkotlin.utils.Constants
import company.com.movieappkotlin.utils.InjectionFragment
import company.com.movieappkotlin.utils.snackbar
import company.com.movieappkotlin.worker.NotificationWorker
import org.kodein.di.generic.instance

class MovieDetailFragment : InjectionFragment(),MoviesListener,MovieAdapter.ItemClickListener {
    private lateinit var binding:FragmentMovieDetailBinding
    private val movieDetailViewModelFactory:MovieDetailViewModelFactory by instance()
    private lateinit var adapter: MovieAdapter
    lateinit var bindingViewModel:MovieDetailViewModel
    lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_movie_detail,container,false)
        bindingViewModel = ViewModelProvider(this,movieDetailViewModelFactory).get(MovieDetailViewModel::class.java)
        bindingViewModel.movieListener=this
        binding.movieDetailViewModel=bindingViewModel
        val movieId=arguments?.getString("movieId")
        movieId?.let {
            bindingViewModel.checkFavMovie(it.toInt()).observe(activity!!, Observer {favmovie->
               if (favmovie!=null) {
                   binding.favmovieImg.setImageResource(android.R.drawable.btn_star_big_on)
                   binding.favmovieImg.isClickable = false
               }
            })

            bindingViewModel.getMovieDetail(it).observe(activity!!, Observer {
                showProgressBar(false)
                Glide.with(this).load("https://image.tmdb.org/t/p/w500/${it.backdrop_path}")
                    .into(binding.moviePosterImg)
                binding.movieName.text=it.original_title
                binding.imdbRating.text=it.vote_average.toString()
                binding.releaseDate.text=it.release_date
                binding.durationTxt.text="${it.runtime.toString()} mins"
                binding.overviewTxt.text=it.overview
                binding.ratingBar.rating= ((it.vote_average?.div(10))?.times(5))!!
                movie= Movie(it.id?.toInt(),it.original_title,it.poster_path, ((it.vote_average.div(10)).times(5)))
                binding.favmovieImg.setOnClickListener {
                    bindingViewModel.saveFavMovie(movie)
                    binding.favmovieImg.setImageResource(android.R.drawable.btn_star_big_on)
                    binding.favmovieImg.isClickable = false
                    val oneTimeWorkRequest= OneTimeWorkRequest.from(NotificationWorker::class.java)
                    WorkManager.getInstance(activity!!).enqueue(oneTimeWorkRequest)
                }
            })
            bindingViewModel.getSimilarMovies(it).observe(activity!!, Observer {
                showProgressBar(false)
                adapter= MovieAdapter(activity!!,it,Constants.SIMILAR)
                adapter.setClickListener(this)
                binding.similarRecycler.layoutManager=LinearLayoutManager(activity!!,LinearLayoutManager.HORIZONTAL,false)
                binding.similarRecycler.adapter=adapter
            })
        }

        return binding.root
    }

    override fun onItemClick(view: View?, position: Int,tag:String) {
        val bundle = bundleOf("movieId" to adapter.getItem(position).id.toString())
        findNavController().navigate(R.id.action_movieDetailFragment_self,bundle)

    }

    override fun onSuccess() {
    }

    override fun onStarted() {
        showProgressBar(true)
    }

    override fun onFailure(message: String) {
        showProgressBar(false)
        binding.root.snackbar(message)
    }


    private fun showProgressBar(show: Boolean){
        if (show) {
            binding.lottie.animationView.visibility = View.VISIBLE
            binding.lottie.animationView.playAnimation()
        }
        else{
            binding.lottie.animationView.clearAnimation()
            binding.lottie.animationView.visibility = View.GONE
        }
    }


}
