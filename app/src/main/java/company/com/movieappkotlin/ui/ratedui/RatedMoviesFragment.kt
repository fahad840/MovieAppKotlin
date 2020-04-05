package company.com.movieappkotlin.ui.ratedui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import company.com.movieappkotlin.R
import company.com.movieappkotlin.adapter.RatedMovieAdapter
import company.com.movieappkotlin.databinding.FragmentRatedMoviesBinding
import company.com.movieappkotlin.db.entities.Movie
import company.com.movieappkotlin.utils.InjectionFragment
import org.kodein.di.generic.instance


/**
 * A simple [Fragment] subclass.
 */
class RatedMoviesFragment : InjectionFragment(),RatedMovieAdapter.ItemClickListener{
    private lateinit var binding:FragmentRatedMoviesBinding
    private lateinit var bindingViewModel:RatedMoviesViewModel
    private val ratedMoviesViewModelFactory:RatedMoviesViewModelFactory by instance()
    private lateinit var ratedMovieAdapter: RatedMovieAdapter
    private lateinit var ratedmovieList:MutableList<Movie>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_rated_movies,container,false)
        bindingViewModel = ViewModelProvider(this,ratedMoviesViewModelFactory).get(RatedMoviesViewModel::class.java)
        bindingViewModel.getFavMovies().observe(activity!! , Observer {
            ratedmovieList= mutableListOf()
            ratedmovieList.addAll(it)
            ratedMovieAdapter= RatedMovieAdapter(activity!!.applicationContext,ratedmovieList)
            ratedMovieAdapter.setClickListener(this)
            binding.ratedMovierecycler.also {
                it.layoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.VERTICAL, false)
                it.adapter=ratedMovieAdapter
            }
        })
        swipeToDelete()

        return binding.root
    }

    override fun onItemClick(view: View?, position: Int) {
        val bundle = bundleOf("movieId" to ratedMovieAdapter.getItem(position).movieid.toString())
        findNavController().navigate(R.id.action_ratedMoviesFragment_to_movieDetailFragment,bundle)
    }

    fun swipeToDelete(){
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                bindingViewModel.deleteFavMovie(ratedmovieList.get(position).movieid!!)
                ratedmovieList.removeAt(position)
                ratedMovieAdapter.notifyDataSetChanged()
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.ratedMovierecycler)
    }

}
