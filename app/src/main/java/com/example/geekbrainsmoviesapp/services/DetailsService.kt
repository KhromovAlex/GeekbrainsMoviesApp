package com.example.geekbrainsmoviesapp.services

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.geekbrainsmoviesapp.api.RetrofitServices
import com.example.geekbrainsmoviesapp.common.Common
import com.example.geekbrainsmoviesapp.common.Common.DETAILS_INTENT_FILTER
import com.example.geekbrainsmoviesapp.common.Common.DETAILS_LOAD_RESULT_EXTRA
import com.example.geekbrainsmoviesapp.common.Common.DETAILS_REQUEST_DATA
import com.example.geekbrainsmoviesapp.common.Common.DETAILS_REQUEST_ID_EXTRA
import com.example.geekbrainsmoviesapp.common.Common.DETAILS_RESULT_ERROR
import com.example.geekbrainsmoviesapp.common.Common.DETAILS_RESULT_NULL_INTENT
import com.example.geekbrainsmoviesapp.common.Common.DETAILS_RESULT_SUCCESS
import com.example.geekbrainsmoviesapp.model.MovieDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsService(name: String = "DetailService") : IntentService(name) {
    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)
    private val mService: RetrofitServices = Common.retrofitService

    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            putLoadResult(DETAILS_RESULT_NULL_INTENT)
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
        } else {
            val id = intent.getIntExtra(DETAILS_REQUEST_ID_EXTRA, 0)
            mService.getMovie(id).enqueue(object : Callback<MovieDetails> {
                override fun onResponse(
                    call: Call<MovieDetails>,
                    response: Response<MovieDetails>
                ) {
                    putLoadResult(DETAILS_RESULT_SUCCESS)
                    broadcastIntent.putExtra(DETAILS_REQUEST_DATA, response.body())
                    LocalBroadcastManager.getInstance(this@DetailsService)
                        .sendBroadcast(broadcastIntent)
                }

                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                    putLoadResult(DETAILS_RESULT_ERROR)
                    LocalBroadcastManager.getInstance(this@DetailsService)
                        .sendBroadcast(broadcastIntent)
                }
            })
        }
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }
}
