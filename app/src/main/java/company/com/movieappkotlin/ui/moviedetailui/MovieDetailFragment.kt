package company.com.movieappkotlin.ui.moviedetailui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide

import company.com.movieappkotlin.R
import company.com.movieappkotlin.adapter.MovieAdapter
import company.com.movieappkotlin.databinding.FragmentMovieDetailBinding
import company.com.movieappkotlin.utils.Constants
import company.com.movieappkotlin.utils.InjectionFragment
import org.kodein.di.generic.instance

class MovieDetailFragment : InjectionFragment(),MovieDetailListener,MovieAdapter.ItemClickListener {
    private lateinit var binding:FragmentMovieDetailBinding
    private val movieDetailViewModelFactory:MovieDetailViewModelFactory by instance()
    private lateinit var adapter: MovieAdapter
    lateinit var bindingViewModel:MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_movie_detail,container,false)
        bindingViewModel = ViewModelProvider(this,movieDetailViewModelFactory).get(MovieDetailViewModel::class.java)
        binding.movieDetailViewModel=bindingViewModel
        val movieId=arguments?.getString("movieId")
        movieId?.let {
            bindingViewModel.getMovieDetail(it).observe(activity!!, Observer {
                Glide.with(this).load("https://image.tmdb.org/t/p/w500/${it.backdrop_path}")
                    .into(binding.moviePosterImg)
                binding.movieName.text=it.original_title
                binding.imdbRating.text=it.vote_average.toString()
                binding.releaseDate.text=it.release_date
                binding.durationTxt.text="${it.runtime.toString()} mins"
                binding.overviewTxt.text=it.overview
                binding.ratingBar.rating=((it.vote_average?.div(10))?.times(5))?.toFloat()!!
            })
            bindingViewModel.getSimilarMovies(it).observe(activity!!, Observer {
                adapter= MovieAdapter(activity!!,it,Constants.SIMILAR)
                adapter.setClickListener(this)
                binding.similarRecycler.layoutManager=LinearLayoutManager(activity!!,LinearLayoutManager.HORIZONTAL,false)
                binding.similarRecycler.adapter=adapter
            })
        }

        return binding.root
    }

    override fun onSucess() {
    }

    override fun onFailure() {
    }

    override fun onItemClick(view: View?, position: Int,tag:String) {
        val bundle = bundleOf("movieId" to adapter.getItem(position).id.toString())
        findNavController().navigate(R.id.action_movieDetailFragment_self,bundle)

    }
}