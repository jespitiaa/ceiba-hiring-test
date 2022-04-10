package com.ceibasoftware.hiringtest.jespitiaa.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ceibasoftware.hiringtest.jespitiaa.BR
import com.ceibasoftware.hiringtest.jespitiaa.databinding.FragmentUserPostsBinding
import com.ceibasoftware.hiringtest.jespitiaa.ui.adapters.UserPostsAdapter
import com.ceibasoftware.hiringtest.jespitiaa.viewmodel.UserPostsViewModel
import androidx.recyclerview.widget.DividerItemDecoration




/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class UserPostsFragment : Fragment() {

    private var _binding: FragmentUserPostsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: UserPostsViewModel
    private var viewModelAdapter: UserPostsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserPostsBinding.inflate(inflater, container, false)
        viewModelAdapter = UserPostsAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.postsRv
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = viewModelAdapter
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            linearLayoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val args: UserPostsFragmentArgs by navArgs()
        binding.setVariable(BR.user, args.user)
        binding.setVariable(BR.showPublicationsBtn, false)
        binding.includedLayout.showPublicationsBtn = false
        binding.includedLayout.setVariable(BR.showPublicationsBtn, false)
        binding.includedLayout.userPostsBtnTV.visibility = View.GONE
        viewModel = ViewModelProvider(this, UserPostsViewModel.Factory(activity.application, args.user.id)).get(
            UserPostsViewModel::class.java)
        viewModel.posts.observe(viewLifecycleOwner, {
            it.apply {
                viewModelAdapter!!.posts = this
            }
        })
    }
}