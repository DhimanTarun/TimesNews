package   com.timesnews.app.interfaces

import com.timesnews.app.model.MostViewedNews

interface IRecyclerViewOnClickListener {
    fun onclick(value: MostViewedNews.Result?)
}