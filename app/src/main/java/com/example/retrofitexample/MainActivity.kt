package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.retrofitexample.databinding.ActivityMainBinding
import okhttp3.ResponseBody

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding
    private val postApi = RetrofitHelper.buildService(Endpoints::class.java)
    private lateinit var receivedPost:Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        updatePost(Post(
            id = 5,
            userId = 5,
            title = "Kotlin",
            body = "Kotlin approved by google"
        ),5)

        deletePost(5)

        addPost(Post(
            id = null,
            userId = 5,
            title = "Kotlin Sindbad",
            body = "Kotlin approved by google!!YAY"
        ))
    }

    private fun addPost(samplePost: Post){
        val postCall = postApi.addPost(samplePost)
        postCall.enqueue(object: Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if(response.isSuccessful){
                    receivedPost = response.body()!!
                    Toast.makeText(this@MainActivity,receivedPost.toString(),Toast.LENGTH_LONG).show()
                    getPosts()

                } else Toast.makeText(this@MainActivity,"Nothing received",Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(this@MainActivity,t.message,Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getPosts(){
        val getCall = postApi.getPosts()
        getCall.enqueue(object: Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if(response.isSuccessful){
                    bind.progressBar.visibility = View.GONE
                    try {
                        var tmpPostList:MutableList<Post>? = response.body() as MutableList<Post>?
                        tmpPostList?.add(0,receivedPost)
                    } catch (ex:Exception){
                        Toast.makeText(this@MainActivity,ex.message,Toast.LENGTH_LONG).show()
                    }
                    bind.recyclerView.apply{
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = PostsAdapter(response.body()!!)
                    }
                }
                else{
                    bind.progressBar.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "Unreachable", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updatePost(updatedPost:Post, id: Int){
        val putCall = postApi.updatePost(updatedPost,id)
        putCall.enqueue(object: Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if(response.isSuccessful)
                    Toast.makeText(this@MainActivity,response.body()!!.toString(),Toast.LENGTH_LONG).show()
                else Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(this@MainActivity,t.message,Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun deletePost(id:Int){
        val deleteCall = postApi.deletePost(id)
        deleteCall.enqueue(object: Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful)
                    Toast.makeText(this@MainActivity,response.code().toString(),Toast.LENGTH_LONG).show()
                else Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@MainActivity,t.message,Toast.LENGTH_LONG).show()
            }
        })
    }
}