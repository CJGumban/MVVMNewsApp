package com.example.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.mvvmnewsapp.R
import com.example.mvvmnewsapp.adapters.NewsAdapter
import com.example.mvvmnewsapp.databinding.FragmentNewsListBinding
import com.example.mvvmnewsapp.ui.NewsActivity
import com.example.mvvmnewsapp.ui.NewsViewModel
import com.example.mvvmnewsapp.util.Constants.Companion.QUERY_PAGE_SIZE
import com.example.mvvmnewsapp.util.Resource


class NewsListFragment : Fragment() {

    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var newsAdapter: NewsAdapter
    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsListBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_newsListFragment_to_articleFragment,
                bundle
            )
        }
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {response->
            when (response) {
                is Resource.Success -> {
                    Log.i(TAG, "Success")

                    hideProgressBar()
                    response.data?.let {newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                        Log.i(TAG,"total pages and lastpage $totalPages ${viewModel.breakingNewsPage}" +
                                "newsResponse total Results ${newsResponse.totalResults}")
                        isLastPage = viewModel.breakingNewsPage == totalPages
                        if (isLastPage){
                            binding.rvNewsList.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {message->
                        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "An error occured : $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }

            }
            Log.i(TAG, "${response.data?.articles?.get(0)}}")
        })
    }

    private fun hideProgressBar() {

        binding.newsListPaginationProgressBar.visibility = View.GONE
        isLoading = false
    }
    private fun showProgressBar() {
        binding.newsListPaginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            var shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling

            Log.i(TAG, "isNotLoading and NotLatPage: ${ !isLoading} ${!isLastPage} " +
                    "isNotLoadingAndNotLastPage: $isNotLoadingAndNotLastPage " +
                    "isAtLastItem: $isAtLastItem " +
                    "isNotAtBeginning: $isNotAtBeginning " +
                    "isTotalMoreThanVisible: $isTotalMoreThanVisible " )

            if (shouldPaginate){
                viewModel.getBreakingNews("us")
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }

    private fun setupRecyclerView(){
        val recyclerView = binding.rvNewsList
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        newsAdapter = NewsAdapter()
        recyclerView.adapter = newsAdapter
        recyclerView.addOnScrollListener(this.scrollListener)


    }


    companion object{
        const val TAG = "NewsListFragment"
    }
}
