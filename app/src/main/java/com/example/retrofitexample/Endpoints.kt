package com.example.retrofitexample

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Endpoints {
    @GET("/posts")
    fun getPosts(): Call<List<Post>>

    @POST("/posts")
    fun addPost(@Body postData: Post): Call<Post>

    @PUT("/posts/{id}")
    fun updatePost(@Body updatedData: Post,@Path(value = "id", encoded = true) postId:Int): Call<Post>

    @DELETE("/posts/{id}")
    fun deletePost(@Path(value = "id", encoded = true) postId:Int):Call<ResponseBody>

}