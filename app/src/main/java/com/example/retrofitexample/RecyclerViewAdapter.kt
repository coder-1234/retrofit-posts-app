package com.example.retrofitexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostsAdapter(private val posts: List<Post>): RecyclerView.Adapter<PostsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_layout, parent, false)
        return PostsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        return holder.bind(posts[position])
    }
}

class PostsViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
    private val photo:ImageView = itemView.findViewById(R.id.post_photo)
    private val title:TextView = itemView.findViewById(R.id.post_title)
    private val overview:TextView = itemView.findViewById(R.id.post_overview)
    private val postId:TextView = itemView.findViewById(R.id.post_id)
    private val userId: TextView = itemView.findViewById(R.id.user_id)

    fun bind(post: Post) {
        Glide.with(itemView.context).load("https://th.bing.com/th/id/OIP.VYePyFaFvMmSt4w5DrLhuQHaE8?pid=ImgDet&rs=1").into(photo)
        title.text = "Title : "+post.title
        overview.text = post.body
        userId.text = "User Id : "+post.userId
        postId.text = "Post Id : "+post.id
    }

}