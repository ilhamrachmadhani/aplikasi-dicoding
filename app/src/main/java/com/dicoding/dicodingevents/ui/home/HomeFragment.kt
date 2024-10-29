package com.dicoding.dicodingevents.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevents.EventAdapter
import com.dicoding.dicodingevents.data.response.ListEventsItem
import com.dicoding.dicodingevents.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    private val homeViewModel: HomeViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // upcoming event on home page
        homeViewModel.listUpcomingEvent.observe(viewLifecycleOwner){eventData ->
            setUpcomingEventData(eventData)
        }

        homeViewModel.isLoadingUpcoming.observe(viewLifecycleOwner){
            showLoadingUpcoming(it)
        }

        val upcomingLayoutManager = LinearLayoutManager(activity)
        upcomingLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        _binding?.recyclerViewHome?.layoutManager = upcomingLayoutManager

        // finished event on home page
        homeViewModel.listFinishedEvent.observe(viewLifecycleOwner){ eventData ->
            setFinishedEventData(eventData)
        }

        homeViewModel.isLoadingFinished.observe(viewLifecycleOwner){
            showLoadingFinished(it)
        }

        val finishedLayoutManager = LinearLayoutManager(activity)
        _binding?.recyclerViewHome?.layoutManager = finishedLayoutManager


        homeViewModel.toastText.observe(viewLifecycleOwner){ toastText ->
            Toast.makeText(activity, toastText, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpcomingEventData(eventData: List<ListEventsItem>){
        val adapter = EventAdapter()
        adapter.submitList(eventData)
        _binding?.recyclerViewHome?.adapter = adapter
    }

    private fun setFinishedEventData(eventData: List<ListEventsItem>){
        val adapter = EventAdapter()
        adapter.submitList(eventData)
        _binding?.recyclerViewHome?.adapter = adapter
    }

    private fun showLoadingUpcoming(isLoading: Boolean){
        _binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showLoadingFinished(isLoading: Boolean){
        _binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}