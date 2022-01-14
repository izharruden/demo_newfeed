package com.demo.newfeed.viewmodel

import android.app.Activity
import android.content.Context
import com.demo.newfeed.BaseActivity
import com.demo.newfeed.BaseConfig
import com.demo.newfeed.repository.ApiRepository
import com.demo.newfeed.retrofit.ApiInterface
import com.demo.newfeed.retrofit.RetrofitClient
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response

class NewFeedViewModel {
    companion object {

        /***********************************
         *
         * CREATE SERVICE
         *
         ***********************************/

        private fun createService(): ApiInterface {
            return RetrofitClient.getClient().create(ApiInterface::class.java)
        }

        /***********************************
         *
         * API LIST
         *
         ***********************************/

        fun getPostCard(
            context: Context,
            callBack: ApiRepository.CallBack,
            progress: Boolean
        ) {

            val call =
                createService().get_post_card()
            ApiRepository(context, callBack, call, "getPostcard", progress)
        }

        /***********************************
         *
         * HANDLING ERROR MESSAGE
         *
         ***********************************/

        fun onErrorResponseAPI(
            context: Context,
            activity: Activity,
            response: Response<ResponseBody>?
        ) {

            if (response?.errorBody() != null) {
                try {
                    val errorJson = response.errorBody()!!.string()
                    val error = JSONObject(errorJson)

                    if (error.has("message")) {
                        val gerError = error.getString("message")
                        BaseActivity.showToast(context, gerError)
                        return
                    }

                    if (error.has("errors")) {
                        val gerError = error.getString("errors")
                        val error = JSONArray(gerError)
                        var errorMessage = ""

                        for (i in 0 until error.length()) {
                            errorMessage = "${error[i]}"
                        }

                        if (errorMessage != "") {
                            BaseActivity.showToast(context, errorMessage)
                            return
                        }
                    }

                    if (error.has("error")) {
                        val gerError = error.getString("error")

                        try {
                            if (JSONObject(gerError).has("message")) {
                                val code = error.getString("message")
                                BaseActivity.showToast(context, code)

                                return
                            }
                        } catch (e: Exception) {
                        }
                        BaseActivity.showToast(context, gerError)
                        return
                    }
                } catch (e: Exception) {
                }
            }

            BaseActivity.showToast(context, "Failed to get response")
        }
    }
}