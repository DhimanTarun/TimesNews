package com.timesnews.app.network

import com.timesnews.app.model.MostViewedNews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiManager {


   @GET("mostpopular/v2/viewed/{period}.json")
   suspend fun getMostViewedNews(
      @Path("period") period:String,
      @Query("api-key") apiKey:String="Lu18Aia6vmzQfIxxxUI4pQoLvsNgYh1S"): Response<MostViewedNews>
  }