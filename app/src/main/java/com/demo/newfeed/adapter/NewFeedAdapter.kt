package com.demo.newfeed.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.newfeed.BaseActivity
import com.demo.newfeed.R
import com.demo.newfeed.models.PostCardData
import kotlinx.android.synthetic.main.adapter_new_feed.view.*
import java.util.*


class NewFeedAdapter(
    private var data: ArrayList<PostCardData>
) :
    RecyclerView.Adapter<NewFeedAdapter.ViewHolder>(), Filterable {
    private var originalData: ArrayList<PostCardData> = data
    private var dataFilter: Filter? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateModel(data: ArrayList<PostCardData>) {
        this.data = data
        this.originalData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_new_feed, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(dataList: PostCardData) {

            val colorTop = dataList.color?.bar?.top
            val colorBottom = dataList.color?.bar?.bottom
            if (colorTop != null)
                itemView.headerView.setBackgroundColor(Color.parseColor(colorTop))
            if (colorBottom != null)
                itemView.cardView.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        Color.parseColor(
                            colorBottom
                        )
                    )
                )

            /** Header **/

            if (dataList.name != null) itemView.headerTitle.text = dataList.name
            if (dataList.icon != null && dataList.icon.isNotEmpty())
                Glide.with(itemView.context).load(dataList.icon).into(itemView.headerImage)

            /** Body **/

            if (dataList.name != null) itemView.nameLabel.text = dataList.name
            else itemView.nameLabel.text = "Unknown"

            itemView.titleLabel.text = dataList.description
            if (dataList.timestamp != null)
                itemView.dateLabel.text =
                    BaseActivity.changeTimeStamp(dataList.timestamp, "dd MMMM yyyy")

            if (dataList.body != null) itemView.descLabel.text = Html.fromHtml(dataList.body)
            else itemView.descLabel.text = ""
            itemView.descLabel.movementMethod = LinkMovementMethod.getInstance()

            itemView.listImage.visibility = View.GONE
            itemView.listVideoView.visibility = View.GONE
            if (dataList.background_type == 1) {
                itemView.listImage.visibility = View.VISIBLE
                if (dataList.background_url != null && dataList.background_url.isNotEmpty())
                    Glide.with(itemView.context).load(dataList.background_url)
                        .into(itemView.listImage)

            }

            if (dataList.background_type == 2) {
                itemView.listVideoView.visibility = View.VISIBLE
                val video = Uri.parse(dataList.background_url)
                itemView.listVideo.setVideoURI(video)
                itemView.listVideo.setOnPreparedListener { mp ->

                    itemView.listVideo.scaleX = 1.5f
                    itemView.listVideo.scaleY = 1.5f
                    mp.isLooping = true
                    itemView.listVideo.start()
                }
            }

            if (dataList.icon != null && dataList.icon.isNotEmpty())
                Glide.with(itemView.context).load(dataList.icon).into(itemView.profileImage)

            /** Footer **/

            itemView.starLabel.text = "${dataList.like_count}"
            itemView.shareLabel.text = "${dataList.share_count}"
            itemView.eyeLabel.text = "${dataList.view_count}"

            itemView.starIcon.setImageResource(R.drawable.ic_star_unseen)
            itemView.shareIcon.setImageResource(R.drawable.ic_share_unseen)
            itemView.eyeIcon.setImageResource(R.drawable.ic_eye_unseen)

            if (dataList.liked != null && dataList.liked) itemView.starIcon.setImageResource(R.drawable.ic_star)
            if (dataList.shared != null && dataList.shared) itemView.shareIcon.setImageResource(R.drawable.ic_share)
            if (dataList.viewed != null && dataList.viewed) itemView.eyeIcon.setImageResource(R.drawable.ic_eye)

            itemView.viewList.setOnClickListener {
                viewArticle(itemView.context, dataList)
            }
        }

        @SuppressLint("SetTextI18n")
        private fun viewArticle(context: Context, data: PostCardData) {
            val dialogView =
                LayoutInflater.from(context).inflate(R.layout.dialog_view_postcard, null)
            val dialog = Dialog(context, R.style.DialogTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(dialogView)
            dialog.setCancelable(true)
            dialog.window?.setGravity(Gravity.BOTTOM)
            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.show()

            val headerView = dialogView.findViewById(R.id.headerView) as LinearLayout
            val dialogCardView = dialogView.findViewById(R.id.dialogCardView) as CardView

            val headerImage = dialogView.findViewById(R.id.headerImage) as ImageView
            val listImage = dialogView.findViewById(R.id.listImage) as ImageView
            val listVideo = dialogView.findViewById(R.id.listVideo) as VideoView
            val profileImage = dialogView.findViewById(R.id.profileImage) as ImageView

            val headerTitle = dialogView.findViewById(R.id.headerTitle) as TextView
            val nameLabel = dialogView.findViewById(R.id.nameLabel) as TextView
            val titleLabel = dialogView.findViewById(R.id.titleLabel) as TextView
            val dateLabel = dialogView.findViewById(R.id.dateLabel) as TextView
            val descLabel = dialogView.findViewById(R.id.descLabel) as TextView

            val starLabel = dialogView.findViewById(R.id.starLabel) as TextView
            val shareLabel = dialogView.findViewById(R.id.shareLabel) as TextView
            val eyeLabel = dialogView.findViewById(R.id.eyeLabel) as TextView

            val starIcon = dialogView.findViewById(R.id.starIcon) as ImageView
            val shareIcon = dialogView.findViewById(R.id.shareIcon) as ImageView
            val eyeIcon = dialogView.findViewById(R.id.eyeIcon) as ImageView

            val colorTop = data.color?.bar?.top
            val colorBottom = data.color?.bar?.bottom
            if (colorTop != null)
                headerView.setBackgroundColor(Color.parseColor(colorTop))
            if (colorBottom != null)
                dialogCardView.setCardBackgroundColor(
                    ColorStateList.valueOf(
                        Color.parseColor(
                            colorBottom
                        )
                    )
                )

            /** Header **/

            if (data.name != null) headerTitle.text = data.name
            if (data.icon != null && data.icon.isNotEmpty())
                Glide.with(context).load(data.icon).into(headerImage)

            /** Body **/

            if (data.name != null) nameLabel.text = data.name
            else nameLabel.text = "Unknown"

            titleLabel.text = data.description
            if (data.timestamp != null)
                dateLabel.text =
                    BaseActivity.changeTimeStamp(data.timestamp, "dd MMMM yyyy")

            if (data.body != null) descLabel.text = Html.fromHtml(data.body)
            else descLabel.text = ""
            descLabel.movementMethod = LinkMovementMethod.getInstance()

            listImage.visibility = View.GONE
            listVideo.visibility = View.GONE
            if (data.background_type == 1) {
                listImage.visibility = View.VISIBLE
                if (data.background_url != null && data.background_url.isNotEmpty())
                    Glide.with(context).load(data.background_url)
                        .into(listImage)

            }

            if (data.background_type == 2) {
                listVideo.visibility = View.VISIBLE
                val video = Uri.parse(data.background_url)
                listVideo.setVideoURI(video)
                listVideo.setOnPreparedListener { mp ->
                    mp.isLooping = true
                    listVideo.start()
                }

            }

            if (data.icon != null && data.icon.isNotEmpty())
                Glide.with(context).load(data.icon).into(profileImage)

            /** Footer **/

            starLabel.text = "${data.like_count}"
            shareLabel.text = "${data.share_count}"
            eyeLabel.text = "${data.view_count}"

            starIcon.setImageResource(R.drawable.ic_star_unseen)
            shareIcon.setImageResource(R.drawable.ic_share_unseen)
            eyeIcon.setImageResource(R.drawable.ic_eye_unseen)

            if (data.liked != null && data.liked) starIcon.setImageResource(R.drawable.ic_star)
            if (data.shared != null && data.shared) shareIcon.setImageResource(R.drawable.ic_share)
            if (data.viewed != null && data.viewed) eyeIcon.setImageResource(R.drawable.ic_eye)
        }
    }

    private inner class DataFilter : Filter() {

        override fun performFiltering(constraint: CharSequence): FilterResults {
            val results = FilterResults()
            return results
        }

        override fun publishResults(
            constraint: CharSequence,
            results: FilterResults
        ) {

        }
    }

    override fun getFilter(): Filter {
        if (dataFilter == null)
            dataFilter = DataFilter()
        return dataFilter as Filter
    }

    fun resetData() {
        data = originalData
    }
}