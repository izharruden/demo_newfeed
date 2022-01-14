package com.demo.newfeed.repository

import android.app.Activity
import android.content.Context
import com.demo.newfeed.utils.PageProgressDialog
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiRepository(
    getContext: Context,
    callBack: CallBack,
    call: Call<ResponseBody>,
    api: String,
    progress: Boolean
) {

    init {

        val activity = getContext as Activity?
        var progressDialog: PageProgressDialog? = null
        if (progress) progressDialog = PageProgressDialog.show(getContext)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (progress && progressDialog != null && activity != null && !activity.isFinishing && progressDialog.isShowing) progressDialog.dismiss()
                if (activity != null && !activity.isFinishing) callBack.onResponseAPI(
                    response,
                    api,
                    response.isSuccessful
                )
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                try {
                    if (progress && progressDialog != null && activity != null && !activity.isFinishing && progressDialog.isShowing) progressDialog.dismiss()
                    if (activity != null && !activity.isFinishing) callBack.onResponseAPI(
                        null,
                        api,
                        false
                    )
                    call.cancel()
                } catch (e: Exception) {
                }
            }
        })
    }

    interface CallBack {
        fun onResponseAPI(response: Response<ResponseBody>?, api: String, success: Boolean)
    }
}