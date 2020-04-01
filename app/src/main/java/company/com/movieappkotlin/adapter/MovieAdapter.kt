package company.com.movieappkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import company.com.movieappkotlin.R
import company.com.movieappkotlin.network.models.MovieResponse.Results

import java.util.*

class MovieAdapter(
    private val context: Context,
    data: List<Results>,
    tag:String
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private val mData: List<Results> = data
    private val tag:String=tag
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View =
            mInflater.inflate(R.layout.movies_layout, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
            holder.myTextView.text = mData[position].title
        holder.imageView.setImageResource(R.drawable.ic_launcher_background)
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/${mData[position].poster_path}")
            .into(holder.imageView)
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var myTextView: TextView
        var imageView: ImageView
        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition,tag)
        }

        init {
            myTextView = itemView.findViewById(R.id.movieNametxt)
            imageView = itemView.findViewById(R.id.moviePosterimg)
            itemView.setOnClickListener(this)
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): Results {
        return mData[id]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener

    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int,tag: String)
    }

}