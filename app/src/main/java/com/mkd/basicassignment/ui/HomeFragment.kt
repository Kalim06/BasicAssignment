package com.mkd.basicassignment.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mkd.basicassignment.adapters.VideoItemAdapter
import com.mkd.basicassignment.databinding.FragmentHomeBinding
import com.mkd.basicassignment.model.VideoData
import com.mkd.basicassignment.repository.ApiResultHandler
import com.mkd.basicassignment.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    //Binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //Variables
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var videoAdapter: VideoItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ViewModel
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        //Adapter
        videoAdapter = VideoItemAdapter()

        //RecyclerView
        binding.videoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.videoRecyclerView.adapter = videoAdapter

        //Get Videos
        homeViewModel.getSupabaseVideos().observe(viewLifecycleOwner) { result ->
            val apiResultHandler = ApiResultHandler<List<VideoData?>?>(onSuccess = {
                if (it != null) {
                    // Store the original list of videos
                    val originalList = it.filterNotNull()
                    videoAdapter.setData(originalList)
                    setupSearchFilter(originalList) // Setup search filter
                } else {
                    Snackbar.make(binding.root, "No Data Found", Snackbar.LENGTH_SHORT).show()
                }
            }, onFailure = {
                Snackbar.make(
                    binding.root,
                    "Something Went Wrong. Please try again later.",
                    Snackbar.LENGTH_SHORT
                ).show()
            })

            if (result != null) {
                apiResultHandler.handleApiResult(result)
                apiResultHandler.loading.observe(viewLifecycleOwner) {
                    binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun setupSearchFilter(originalList: List<VideoData>) {
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                // Filter the original list based on the search query
                val query = s.toString().trim()
                val filteredList = originalList.filter { video ->
                    video.title.contains(query, ignoreCase = true)
                }
                // Update the RecyclerView adapter with the filtered list
                if (filteredList.isEmpty()) {
                    // Show "No Results Found" message
                    Snackbar.make(binding.root, "No Results Found", Snackbar.LENGTH_SHORT).show()
                } else {
                    videoAdapter.setData(filteredList)
                }
            }
        })
    }
}