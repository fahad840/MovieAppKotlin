package company.com.movieappkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import company.com.movieappkotlin.R
import company.com.movieappkotlin.db.entities.Movie

class RatedMovieAdapter(private val context: Context,
data: List<Movie>
) : RecyclerView.Adapter<RatedMovieAdapter.ViewHolder>() {
    private val mData: List<Movie> = data
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: RatedMovieAdapter.ItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View =
            mInflater.inflate(R.layout.rated_movie_layout, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.myTextView.text = mData[position].movieName
        holder.imageView.setImageResource(R.drawable.ic_launcher_background)
        holder.ratingBar.rating=mData[position].movierating!!
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/${mData[position].movieImage}")
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
        var ratingBar:RatingBar
        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        init {
            myTextView = itemView.findViewById(R.id.ratedmovieName)
            imageView = itemView.findViewById(R.id.rated_movieposter)
            ratingBar=itemView.findViewById(R.id.ratedMovieRatingbar)
            itemView.setOnClickListener(this)
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): Movie {
        return mData[id]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener

    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

}