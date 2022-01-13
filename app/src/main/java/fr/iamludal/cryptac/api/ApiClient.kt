package fr.iamludal.cryptac.api

import com.google.gson.GsonBuilder
import fr.iamludal.cryptac.api.interceptors.ApiKeyInterceptor
import fr.iamludal.cryptac.shared.CustomStrategy
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Client to make API requests
 */
object ApiClient {
    private const val BASE_URL = "https://pro-api.coinmarketcap.com/v1/"

    /**
     * Create a new client implementation for the ApiInterface service interface
     * @return the client implementation
     */
    fun create(): ApiInterface {
        val httpClient = OkHttpClient.Builder().run {
            addInterceptor(ApiKeyInterceptor())
        }

        val gson = GsonBuilder()
            .setFieldNamingStrategy(CustomStrategy())
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(httpClient.build())
            .build()
            .create(ApiInterface::class.java)
    }
}
