package com.example.myapplication.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.BlogPost
import com.example.myapplication.ui.DataStateListener
import com.example.myapplication.ui.main.state.MainStateEvent.*
import com.example.myapplication.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), BlogListAdapter.Interaction {

    lateinit var viewModel: MainViewModel

    lateinit var dataStateHandler: DataStateListener

    lateinit var blogListAdapter: BlogListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?: throw Exception("Invalid Activity")

        subscribeObservers()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainFragment.context)
            blogListAdapter = BlogListAdapter(this@MainFragment)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            adapter = blogListAdapter
        }
    }

    fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer{ dataState->
            println("DEBUG: DataState: $dataState")
            // Handle data<T>
            dataStateHandler.onDataStateChange(dataState)

            dataState.data?.let{ event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    //set BlogPost data
                    mainViewState.blogPosts?.let {
                        viewModel.setBlogListData(it)
                        blogListAdapter.submitList(it)
                    }

                    mainViewState.user?.let {
                        viewModel.setUser(it)
                    }

                }

            }

        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer{ viewState->
                viewState.blogPosts?.let{
                    println("DEBUG: Setting blog post to RecyclerView: $it")
                }

                viewState.user?.let{
                    println("DEBUG: Setting user data: $it")
                }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_get_user -> triggerGetUserEvent()

            R.id.action_get_blogs -> triggerGetBlogsEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetBlogsEvent() {
        viewModel.setStateEvent(GetBlogPostsEvent())
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(GetUserEvent("1"))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            dataStateHandler = context as DataStateListener
        }catch(e: ClassCastException){
            println("$context must implement DataStateListener")
        }

    }

    override fun onItemSelected(position: Int, item: BlogPost) {
        TODO("Not yet implemented")
    }
}