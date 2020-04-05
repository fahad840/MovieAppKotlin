package company.com.movieappkotlin.ui.moviesui

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import company.com.movieappkotlin.R
import company.com.movieappkotlin.adapter.MovieAdapter
import company.com.movieappkotlin.databinding.FragmentMoviesBinding
import company.com.movieappkotlin.utils.Constants
import company.com.movieappkotlin.utils.InjectionFragment
import company.com.movieappkotlin.utils.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinContext
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext


/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : InjectionFragment(),MoviesListener,MovieAdapter.ItemClickListener {
    private lateinit var binding:FragmentMoviesBinding
    private val moviesViewModelFactory:MoviesViewModelFactory by instance()
    lateinit var bindingViewModel:MoviesViewModel
    private lateinit var upcomingAdapter: MovieAdapter
    private lateinit var popularAdapter: MovieAdapter
    private lateinit var trendingAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_movies,container,false)
        bindingViewModel = ViewModelProvider(this,moviesViewModelFactory).get(MoviesViewModel::class.java)
        bindingViewModel.moviesListener=this
        binding.movieViewModel=bindingViewModel
        if(bindingViewModel.getLoggedInUser().value == null) bindingViewModel.getUserDetails()

        return binding.root
    }

    override fun onSuccess() {
        showProgressBar(false)
        bindingViewModel.getUpcomingMovies().observe(this , Observer {
            showProgressBar(false)
            upcomingAdapter= MovieAdapter(activity!!.applicationContext,it,Constants.UPCOMING)
            upcomingAdapter.setClickListener(this)
            binding.upcomingRecycler.also {
                it.layoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.HORIZONTAL, false)
                it.adapter=upcomingAdapter
            }
        })

        bindingViewModel.getPopularMovies().observe(this, Observer {
            showProgressBar(false)
            popularAdapter= MovieAdapter(activity!!.applicationContext,it,Constants.POPULAR)
            popularAdapter.setClickListener(this)
            binding.popularRecycler.also {
                it.layoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.HORIZONTAL, false)
                it.adapter=popularAdapter
            }
        })

        bindingViewModel.getTrendingMovies().observe(this, Observer {
            showProgressBar(false)
            trendingAdapter= MovieAdapter(activity!!.applicationContext,it,Constants.TRENDING)
            trendingAdapter.setClickListener(this)
            binding.trendingRecycler.also {
                it.layoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.HORIZONTAL, false)
                it.adapter=trendingAdapter
            }
        })
    }

    override fun onStarted() {
        showProgressBar(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.logout -> {
                bindingViewModel.deleteUser()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onFailure(message: String) {
        activity?.toast(message)
    }

    override fun onItemClick(view: View?, position: Int,tag:String) {
        var movieId =""
        when(tag){
            Constants.TRENDING -> movieId=trendingAdapter.getItem(position).id.toString()
            Constants.POPULAR -> movieId=popularAdapter.getItem(position).id.toString()
            Constants.UPCOMING -> movieId=upcomingAdapter.getItem(position).id.toString()
        }

        val bundle = bundleOf("movieId" to movieId)
        findNavController().navigate(R.id.action_moviesFragment_to_movieDetailFragment,bundle)

    }

    fun showProgressBar(show: Boolean){
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

