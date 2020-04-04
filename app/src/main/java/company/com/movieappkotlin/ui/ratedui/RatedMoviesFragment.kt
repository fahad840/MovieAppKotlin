package company.com.movieappkotlin.ui.ratedui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import company.com.movieappkotlin.R
import company.com.movieappkotlin.adapter.RatedMovieAdapter
import company.com.movieappkotlin.databinding.FragmentRatedMoviesBinding
import company.com.movieappkotlin.utils.InjectionFragment
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class RatedMoviesFragment : InjectionFragment(),RatedMovieAdapter.ItemClickListener{
    private lateinit var binding:FragmentRatedMoviesBinding
    private lateinit var bindingViewModel:RatedMoviesViewModel
    private val ratedMoviesViewModelFactory:RatedMoviesViewModelFactory by instance()
    private lateinit var ratedMovieAdapter: RatedMovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_rated_movies,container,false)
        bindingViewModel = ViewModelProvider(this,ratedMoviesViewModelFactory).get(RatedMoviesViewModel::class.java)
        bindingViewModel.getFavMovies().observe(activity!! , Observer {
            ratedMovieAdapter= RatedMovieAdapter(activity!!.applicationContext,it)
            ratedMovieAdapter.setClickListener(this)
            binding.ratedMovierecycler.also {
                it.layoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.VERTICAL, false)
                it.adapter=ratedMovieAdapter
            }
        })
        return binding.root
    }

    override fun onItemClick(view: View?, position: Int) {

    }

}
