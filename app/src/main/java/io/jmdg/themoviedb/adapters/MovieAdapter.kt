package io.jmdg.themoviedb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.jmdg.themoviedb.R
import io.jmdg.themoviedb.data.models.Movie
import io.jmdg.themoviedb.utils.extensions.loadImageFromUrl
import kotlinx.android.synthetic.main.item_movie.view.*

/**
 * Created by Joshua de Guzman on 04/09/2018.
 */


class MovieAdapter(val context: Context) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var movies = ArrayList<Movie>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.itemView.tvTitle.text = movie.title
        holder.itemView.rbVotes.rating = movie.voteAverage / 2f
        holder.itemView.ivPoster.loadImageFromUrl("https://image.tmdb.org/t/p/w500" + movie.posterPath, 20)
    }

    fun addMovies(dataSet: List<Movie>?) {
        if (dataSet != null) {
            movies.addAll(dataSet)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = movies.count()

    fun clear() {
        movies.clear()
        notifyDataSetChanged()
    }
}