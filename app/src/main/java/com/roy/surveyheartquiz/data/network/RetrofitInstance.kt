package com.roy.surveyheartquiz.data.network
/** By ROY */
import com.roy.surveyheartquiz.data.network.api.ApiService
import com.roy.surveyheartquiz.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    /** Used for logging the request and response lines of the Retrofit Calls */
    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    /** OkHttpClient */
    private val httpClient: OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(logging)
        .build()


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

}