package com.timesnews.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.timesnews.app.databinding.AdapterMostViewedNewsBinding
import com.timesnews.app.interfaces.IRecyclerViewOnClickListener
import com.timesnews.app.model.MostViewedNews


class MostViewedNewsAdapter(
    private val mMostViewedNewsDataList: List<MostViewedNews.Result>,
    private val mIRecyclerViewOnClickListener: IRecyclerViewOnClickListener
) :
    RecyclerView.Adapter<MostViewedNewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mBinding = AdapterMostViewedNewsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(mBinding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBinding.mMostViewedData = mMostViewedNewsDataList[position]
        holder.mBinding.newsIV.load(mMostViewedNewsDataList[position].media.first().mediaMetadata.first().url) {
            crossfade(true)
            placeholder(R.mipmap.ic_launcher)
            transformations(CircleCropTransformation())
        }
        holder.mBinding.root.setOnClickListener {
            mIRecyclerViewOnClickListener.onclick(
                mMostViewedNewsDataList[holder.adapterPosition])

        }
    }


    override fun getItemCount(): Int {
        return mMostViewedNewsDataList.size
    }

    class ViewHolder(binding: AdapterMostViewedNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val mBinding = binding
    }
}