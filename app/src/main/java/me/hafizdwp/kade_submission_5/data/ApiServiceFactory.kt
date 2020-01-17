package me.hafizdwp.kade_submission_5.data

import me.hafizdwp.kade_submission_5.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author hafizdwp
 * 20/11/2019
 **/
object ApiServiceFactory {

    val mClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
//                    addInterceptor(ChuckInterceptor(MyApp.getContext()))
                }
            }
            .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
            )
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)

    inline fun <reified T> build(baseUrl: String = BuildConfig.BASE_URL): T =
            Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(mClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(T::class.java)
}