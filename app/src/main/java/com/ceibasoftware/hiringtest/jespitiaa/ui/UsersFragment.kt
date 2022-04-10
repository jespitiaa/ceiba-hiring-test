package com.ceibasoftware.hiringtest.jespitiaa.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ceibasoftware.hiringtest.jespitiaa.databinding.FragmentUsersBinding
import com.ceibasoftware.hiringtest.jespitiaa.ui.adapters.UsersAdapter
import com.ceibasoftware.hiringtest.jespitiaa.viewmodel.UsersViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: UsersViewModel
    private var viewModelAdapter: UsersAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        viewModelAdapter = UsersAdapter()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.usersRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
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
        viewModel = ViewModelProvider(this, UsersViewModel.Factory(activity.application)).get(UsersViewModel::class.java)
        viewModel.users.observe(viewLifecycleOwner, {
            it.apply {
                viewModelAdapter!!.users = this
                binding.emptyListTV.visibility = if(this.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
        })

        binding.editTextTextPersonName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.userQuery = s.toString()
                viewModel.refreshData()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }


}