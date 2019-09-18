package kz.kairliyev.newsapi.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationRecycler : RecyclerView.OnScrollListener {
    private var visibleThreshold = 2
    private var currentPage = 1
    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 1

    internal var mLayoutManager: RecyclerView.LayoutManager

    constructor(layoutManager: LinearLayoutManager) {
        this.mLayoutManager = layoutManager as RecyclerView.LayoutManager
    }

    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 10
        val totalItemCount = mLayoutManager.itemCount

        lastVisibleItemPosition = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            nextPage(currentPage, totalItemCount, view)
            loading = true
        }
    }

    fun resetState() {
        this.currentPage = this.startingPageIndex
        this.previousTotalItemCount = 0
        this.loading = true
    }

    abstract fun nextPage(page: Int, totalItemsCount: Int, view: RecyclerView)
}