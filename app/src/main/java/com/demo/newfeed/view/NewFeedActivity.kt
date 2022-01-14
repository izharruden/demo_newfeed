package com.demo.newfeed.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.newfeed.BaseActivity
import com.demo.newfeed.R
import com.demo.newfeed.adapter.NewFeedAdapter
import com.demo.newfeed.models.ListPostCardData
import com.demo.newfeed.models.PostCardData
import com.demo.newfeed.repository.ApiRepository
import com.demo.newfeed.viewmodel.NewFeedViewModel
import com.google.gson.GsonBuilder
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_new_feed.*
import okhttp3.ResponseBody
import retrofit2.Response

class NewFeedActivity : BaseActivity(), ApiRepository.CallBack {

    private var listAdapter: NewFeedAdapter? = null
    private var listObject = ArrayList<PostCardData>()
    private var listPage = 0
    private var loadingPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_feed)
        StatusBarUtil.setTranslucentForImageView(this, 20, null)

        centerTitle.text = "My PostCards"
        leftIcon.setOnClickListener { onBackPressed() }
        initList()
    }

    private fun initList() {
        listObject.clear()

        listAdapter = NewFeedAdapter(listObject)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = listAdapter
        recyclerView.isNestedScrollingEnabled = false

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (loadingPage) {
                    loadingPage = false
                    listPage += 1
                    callListAPI()
                }
            }
        })

        callListAPI()
    }

    private fun callListAPI() {
        NewFeedViewModel.getPostCard(this, this, true)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResponseAPI(response: Response<ResponseBody>?, api: String, success: Boolean) {
        when {
            success -> {
                val gson = GsonBuilder().create()
                val getResponseString = response!!.body()!!.string()

                if (api == "getPostcard") {
                    loadingPage = false

                    val getResponse = gson.fromJson(getResponseString, ListPostCardData::class.java)
                    if (getResponse.data != null) {
                        (getResponse.data.indices).forEach { i ->
                            val data = getResponse.data[i]
                            listObject.add(data)
                        }

                        listAdapter?.updateModel(listObject)
                        listAdapter?.notifyDataSetChanged()

                        loadingLabel.text = "${listObject.size}"
                    }
                }
            }
            else -> NewFeedViewModel.onErrorResponseAPI(this, this, response)
        }
    }
}